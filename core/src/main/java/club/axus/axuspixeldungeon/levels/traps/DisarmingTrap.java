/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package club.axus.axuspixeldungeon.levels.traps;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.mobs.Statue;
import club.axus.axuspixeldungeon.effects.CellEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.Heap;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.KindOfWeapon;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class DisarmingTrap extends Trap{

	{
		color = RED;
		shape = LARGE_DOT;
	}

	@Override
	public void activate() {
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			int cell = Dungeon.level.randomRespawnCell( null );

			if (cell != -1) {
				Item item = heap.pickUp();
				Heap dropped = Dungeon.level.drop( item, cell );
				dropped.type = heap.type;
				dropped.sprite.view( dropped );
				dropped.seen = true;
				for (int i : PathFinder.NEIGHBOURS9) Dungeon.level.visited[cell+i] = true;
				GameScene.updateFog();

				Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
				CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
			}
		}

		if (Actor.findChar(pos) instanceof Statue){
			Actor.findChar(pos).die(this);
			Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
			CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
		}

		if (Dungeon.hero.pos == pos && !Dungeon.hero.flying){
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;

			if (weapon != null && !weapon.cursed) {

				int cell;
				int tries = 20;
				do {
					cell = Dungeon.level.randomRespawnCell( null );
					if (tries-- < 0 && cell != -1) break;

					PathFinder.buildDistanceMap(pos, Dungeon.level.passable);
				} while (cell == -1 || PathFinder.distance[cell] < 10 || PathFinder.distance[cell] > 20);

				hero.belongings.weapon = null;
				Dungeon.quickslot.clearItem(weapon);
				weapon.updateQuickslot();

				Dungeon.level.drop(weapon, cell).seen = true;
				for (int i : PathFinder.NEIGHBOURS9)
					Dungeon.level.mapped[cell+i] = true;
				GameScene.updateFog(cell, 1);

				GLog.w( Messages.get(this, "disarm") );

				Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
				CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);

			}
		}
	}
}
