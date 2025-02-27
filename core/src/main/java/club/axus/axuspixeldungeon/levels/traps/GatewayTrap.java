package club.axus.axuspixeldungeon.levels.traps;


import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.mobs.Mob;
import club.axus.axuspixeldungeon.items.Heap;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GatewayTrap extends Trap {

	{
		color = TEAL;
		shape = CROSSHAIR;

		disarmedByActivation = false;
		avoidsHallways = true;
	}

	private int telePos = -1;

	@Override
	public void activate() {

		if (telePos == -1){
			for (int i : PathFinder.NEIGHBOURS9){
				Char ch = Actor.findChar(pos + i);
				if (ch != null){
					if (ScrollOfTeleportation.teleportChar(ch)) {
						if (ch instanceof Mob && ((Mob) ch).state == ((Mob) ch).HUNTING) {
							((Mob) ch).state = ((Mob) ch).WANDERING;
						}
						telePos = ch.pos;
						break;
					}
				}
				Heap heap = Dungeon.level.heaps.get(pos + i);
				if (heap != null){
					int cell = Dungeon.level.randomRespawnCell( null );

					Item item = heap.pickUp();

					if (cell != -1) {
						Heap dropped = Dungeon.level.drop( item, cell );
						dropped.type = heap.type;
						dropped.sprite.view( dropped );
						telePos = cell;
						break;

					}
				}
			}
		}

		if (telePos != -1){

			ArrayList<Integer> telePositions = new ArrayList<>();
			for (int i : PathFinder.NEIGHBOURS8){
				if (Dungeon.level.passable[telePos+i]
						&& Actor.findChar( telePos+i ) == null){
					telePositions.add(telePos+i);
				}
			}
			Random.shuffle(telePositions);

			if (Dungeon.level.passable[telePos]
					&& Actor.findChar( telePos ) == null){
				telePositions.add(0, telePos);
			}

			ArrayList<Integer> largeCharPositions = new ArrayList<>();
			for (int pos : telePositions){
				if (Dungeon.level.openSpace[pos]){
					largeCharPositions.add(pos);
				}
			}

			for (int i : PathFinder.NEIGHBOURS9){

				Char ch = Actor.findChar(pos + i);
				if (ch != null){
					int newPos = -1;
					if (Char.hasProp(ch, Char.Property.LARGE)){
						if (!largeCharPositions.isEmpty()){
							newPos = largeCharPositions.get(0);
						}
					} else {
						if (!telePositions.isEmpty()) {
							newPos = telePositions.get(0);
						}
					}

					if (newPos != -1){
						telePositions.remove((Integer)newPos);
						largeCharPositions.remove((Integer)newPos);

						if (ScrollOfTeleportation.teleportToLocation(ch, newPos)){
							if (ch instanceof Mob && ((Mob) ch).state == ((Mob) ch).HUNTING) {
								((Mob) ch).state = ((Mob) ch).WANDERING;
							}
						}
					}
				}

				Heap heap = Dungeon.level.heaps.get(pos + i);
				if (heap != null){
					Item item = heap.pickUp();
					Heap dropped = Dungeon.level.drop( item, telePos );
					dropped.type = heap.type;
					dropped.sprite.view( dropped );
				}
			}
		}

	}

	private static final String TELE_POS = "tele_pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TELE_POS, telePos);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		telePos = bundle.getInt(TELE_POS);
	}
}
