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

package club.axus.axuspixeldungeon.items.weapon.missiles.darts;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Corrosion;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;


public class RotDart extends TippedDart {
	
	{
		image = ItemSpriteSheet.ROT_DART;
	}
	
	@Override
	public int proc(Char attacker, Char defender, int damage) {
		
		if (defender.properties().contains(Char.Property.BOSS)
				|| defender.properties().contains(Char.Property.MINIBOSS)){
			Buff.affect(defender, Corrosion.class).set(5f, Dungeon.depth/3);
		} else{
			Buff.affect(defender, Corrosion.class).set(10f, Dungeon.depth);
		}
		
		return super.proc(attacker, defender, damage);
	}
	
	@Override
	public float durabilityPerUse() {
		return 100f;
	}
}
