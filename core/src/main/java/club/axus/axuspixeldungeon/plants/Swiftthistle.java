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

package club.axus.axuspixeldungeon.plants;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Haste;
import club.axus.axuspixeldungeon.actors.hero.HeroSubClass;
import club.axus.axuspixeldungeon.actors.mobs.Mob;
import club.axus.axuspixeldungeon.levels.traps.Trap;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.sprites.CharSprite;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Swiftthistle extends Plant {
	
	{
		image = 2;
		seedClass = Seed.class;
	}
	
	@Override
	public void activate( Char ch ) {
		if (ch == Dungeon.hero) {
			Buff.affect(ch, TimeBubble.class).reset();
			if (Dungeon.hero.subClass == HeroSubClass.WARDEN){
				Buff.affect(ch, Haste.class, 1f);
			}
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SWIFTTHISTLE;
			
			plantClass = Swiftthistle.class;
		}
	}
	
	//FIXME lots of copypasta from time freeze here
	
	public static class TimeBubble extends Buff {
		
		{
			type = buffType.POSITIVE;
			announced = true;
		}
		
		private float left;
		ArrayList<Integer> presses = new ArrayList<>();
		
		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1f, 1f, 0);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (6f - left) / 6f);
		}
		
		public void reset(){
			left = 7f;
		}
		
		@Override
		public String toString() {
			return Messages.get(this, "name");
		}
		
		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns(left));
		}
		
		public void processTime(float time){
			left -= time;

			//use 1/1,000 to account for rounding errors
			if (left < -0.001f){
				detach();
			}
			
		}
		
		public void setDelayedPress(int cell){
			if (!presses.contains(cell)) {
				presses.add(cell);
			}
		}

		public void triggerPresses() {
			for (int cell : presses) {
				Dungeon.level.pressCell(cell);
			}
			
			presses = new ArrayList<>();
		}

		public void disarmPressedTraps(){
			for (int cell : presses){
				Trap t = Dungeon.level.traps.get(cell);
				if (t != null && t.disarmedByActivation) t.disarm();
			}

			presses = new ArrayList<>();
		}
		
		@Override
		public void detach(){
			super.detach();
			triggerPresses();
			target.next();
		}

		@Override
		public void fx(boolean on) {
			Emitter.freezeEmitters = on;
			if (on){
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					if (mob.sprite != null) mob.sprite.add(CharSprite.State.PARALYSED);
				}
			} else {
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					if (mob.paralysed <= 0) mob.sprite.remove(CharSprite.State.PARALYSED);
				}
			}
		}
		
		private static final String PRESSES = "presses";
		private static final String LEFT = "left";
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			
			int[] values = new int[presses.size()];
			for (int i = 0; i < values.length; i ++)
				values[i] = presses.get(i);
			bundle.put( PRESSES , values );
			
			bundle.put( LEFT, left);
		}
		
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			
			int[] values = bundle.getIntArray( PRESSES );
			for (int value : values)
				presses.add(value);
			
			left = bundle.getFloat(LEFT);
		}
		
	}
}
