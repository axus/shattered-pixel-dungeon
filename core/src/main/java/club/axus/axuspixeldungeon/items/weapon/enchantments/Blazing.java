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

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Burning;
import club.axus.axuspixeldungeon.effects.particles.FlameParticle;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import club.axus.axuspixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Blazing extends Weapon.Enchantment {

	private static ItemSprite.Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		int level = Math.max( 0, weapon.buffedLvl() );

		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		float procChance = (level+1f)/(level+3f) * procChanceMultiplier(attacker);
		if (Random.Float() < procChance) {
			
			if (defender.buff(Burning.class) != null){
				Buff.affect(defender, Burning.class).reignite(defender, 8f);
				int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
				defender.damage( Math.round(burnDamage * 0.67f), this );
			} else {
				Buff.affect(defender, Burning.class).reignite(defender, 8f);
			}
			
			defender.sprite.emitter().burst( FlameParticle.FACTORY, level + 1 );
			
		}

		return damage;

	}
	
	@Override
	public Glowing glowing() {
		return ORANGE;
	}
}
