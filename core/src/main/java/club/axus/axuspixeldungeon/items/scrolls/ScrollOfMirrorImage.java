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

package club.axus.axuspixeldungeon.items.scrolls;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.mobs.npcs.MirrorImage;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfMirrorImage extends Scroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_MIRRORIMG;
	}

	private static final int NIMAGES	= 2;
	
	@Override
	public void doRead() {
		int spawnedImages = spawnImages(curUser, NIMAGES);
		
		if (spawnedImages > 0) {
			identify();
		}
		
		Sample.INSTANCE.play( Assets.Sounds.READ );
		
		readAnimation();
	}
	
	//returns the number of images spawned
	public static int spawnImages( Hero hero, int nImages ){
		
		ArrayList<Integer> respawnPoints = new ArrayList<>();
		
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = hero.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
				respawnPoints.add( p );
			}
		}
		
		int spawned = 0;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index( respawnPoints );
			
			MirrorImage mob = new MirrorImage();
			mob.duplicate( hero );
			GameScene.add( mob );
			ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );
			
			respawnPoints.remove( index );
			nImages--;
			spawned++;
		}
		
		return spawned;
	}
	
	public static class DelayedImageSpawner extends Buff{
		
		public DelayedImageSpawner(){
			this(NIMAGES, NIMAGES, 1);
		}
		
		public DelayedImageSpawner( int total, int perRound, float delay){
			super();
			totImages = total;
			imPerRound = perRound;
			this.delay = delay;
		}
		
		private int totImages;
		private int imPerRound;
		private float delay;
		
		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)){
				spend(delay);
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public boolean act() {
			
			int spawned = spawnImages((Hero)target,  Math.min(totImages, imPerRound));
			
			totImages -= spawned;
			
			if (totImages <0){
				detach();
			} else {
				spend( delay );
			}
			
			return true;
		}
		
		private static final String TOTAL = "images";
		private static final String PER_ROUND = "per_round";
		private static final String DELAY = "delay";
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( TOTAL, totImages );
			bundle.put( PER_ROUND, imPerRound );
			bundle.put( DELAY, delay );
		}
		
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			totImages = bundle.getInt( TOTAL );
			imPerRound = bundle.getInt( PER_ROUND );
			delay = bundle.getFloat( DELAY );
		}
	}

	@Override
	public int value() {
		return isKnown() ? 30 * quantity : super.value();
	}
}
