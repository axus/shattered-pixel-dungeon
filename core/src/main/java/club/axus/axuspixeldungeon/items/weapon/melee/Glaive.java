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

package club.axus.axuspixeldungeon.items.weapon.melee;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;

public class Glaive extends MeleeWeapon {

	{
		image = ItemSpriteSheet.GLAIVE;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.8f;

		tier = 5;
		DLY = 1.5f; //0.67x speed
		RCH = 2;    //extra reach
	}

	@Override
	public int max(int lvl) {
		return  Math.round(6.67f*(tier+1)) +    //40 base, up from 30
				lvl*Math.round(1.33f*(tier+1)); //+8 per level, up from +6
	}

}
