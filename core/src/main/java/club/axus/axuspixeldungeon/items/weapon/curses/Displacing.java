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

package club.axus.axuspixeldungeon.items.weapon.curses;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.mobs.Mob;
import club.axus.axuspixeldungeon.effects.CellEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Displacing extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {

		float procChance = 1/12f * procChanceMultiplier(attacker);
		if (Random.Float() < procChance && !defender.properties().contains(Char.Property.IMMOVABLE)){
			int count = 20;
			int newPos;
			do {
				newPos = Dungeon.level.randomRespawnCell( defender );
				if (count-- <= 0) {
					break;
				}
			} while (newPos == -1 || Dungeon.level.secret[newPos]);

			if (newPos != -1 && !Dungeon.bossLevel()) {

				if (Dungeon.level.heroFOV[defender.pos]) {
					CellEmitter.get( defender.pos ).start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
				}

				defender.pos = newPos;
				if (defender instanceof Mob && ((Mob) defender).state == ((Mob) defender).HUNTING){
					((Mob) defender).state = ((Mob) defender).WANDERING;
				}
				defender.sprite.place( defender.pos );
				defender.sprite.visible = Dungeon.level.heroFOV[defender.pos];

				return 0;

			}
		}

		return damage;
	}

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}

}
