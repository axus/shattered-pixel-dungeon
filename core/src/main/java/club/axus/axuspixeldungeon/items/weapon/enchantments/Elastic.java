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

package club.axus.axuspixeldungeon.items.weapon.enchantments;

import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.items.wands.WandOfBlastWave;
import club.axus.axuspixeldungeon.items.weapon.SpiritBow;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.items.weapon.missiles.MissileWeapon;
import club.axus.axuspixeldungeon.mechanics.Ballistica;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Elastic extends Weapon.Enchantment {
	
	private static ItemSprite.Glowing PINK = new ItemSprite.Glowing( 0xFF00FF );
	
	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
		int level = Math.max( 0, weapon.buffedLvl() );

		// lvl 0 - 20%
		// lvl 1 - 33%
		// lvl 2 - 43%
		float procChance = (level+1f)/(level+5f) * procChanceMultiplier(attacker);
		if (Random.Float() < procChance) {
			//trace a ballistica to our target (which will also extend past them
			Ballistica trajectory = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET);
			//trim it to just be the part that goes past them
			trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
			//knock them back along that ballistica
			WandOfBlastWave.throwChar(defender, trajectory, 2, !(weapon instanceof MissileWeapon || weapon instanceof SpiritBow));
		}
		
		return damage;
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return PINK;
	}

}
