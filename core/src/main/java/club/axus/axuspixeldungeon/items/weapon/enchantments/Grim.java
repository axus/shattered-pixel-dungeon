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

import club.axus.axuspixeldungeon.Badges;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.effects.particles.ShadowParticle;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import club.axus.axuspixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Grim extends Weapon.Enchantment {
	
	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {

		int level = Math.max( 0, weapon.buffedLvl() );

		int enemyHealth = defender.HP - damage;
		if (enemyHealth <= 0) return damage; //no point in proccing if they're already dead.

		//scales from 0 - 50% based on how low hp the enemy is, plus 5% per level
		float maxChance = 0.5f + .05f*level;
		float chanceMulti = (float)Math.pow( ((defender.HT - enemyHealth) / (float)defender.HT), 2);
		float chance = maxChance * chanceMulti;

		chance *= procChanceMultiplier(attacker);

		if (Random.Float() < chance) {
			
			defender.damage( defender.HP, this );
			defender.sprite.emitter().burst( ShadowParticle.UP, 5 );
			
			if (!defender.isAlive() && attacker instanceof Hero
				//this prevents unstable from triggering grim achievement
				&& weapon.hasEnchant(Grim.class, attacker)) {
				Badges.validateGrimWeapon();
			}
			
		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return BLACK;
	}

}
