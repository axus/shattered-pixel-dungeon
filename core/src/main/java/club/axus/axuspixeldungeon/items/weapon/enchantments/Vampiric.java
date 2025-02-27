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
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.sprites.CharSprite;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import club.axus.axuspixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Vampiric extends Weapon.Enchantment {

	private static ItemSprite.Glowing RED = new ItemSprite.Glowing( 0x660022 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		
		//chance to heal scales from 5%-30% based on missing HP
		float missingPercent = (attacker.HT - attacker.HP) / (float)attacker.HT;
		float healChance = 0.05f + .25f*missingPercent;

		healChance *= procChanceMultiplier(attacker);
		
		if (Random.Float() < healChance){
			
			//heals for 50% of damage dealt
			int healAmt = Math.round(damage * 0.5f);
			healAmt = Math.min( healAmt, attacker.HT - attacker.HP );
			
			if (healAmt > 0 && attacker.isAlive()) {
				
				attacker.HP += healAmt;
				attacker.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
				attacker.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
				
			}
		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return RED;
	}
}
