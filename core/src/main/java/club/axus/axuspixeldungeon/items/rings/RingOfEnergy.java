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

package club.axus.axuspixeldungeon.items.rings;

import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfEnergy extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_ENERGY;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",
					new DecimalFormat("#.##").format(100f * (Math.pow(1.20f, soloBuffedBonus()) - 1f)),
					new DecimalFormat("#.##").format(100f * (Math.pow(1.15f, soloBuffedBonus()) - 1f)));
		} else {
			return Messages.get(this, "typical_stats",
					new DecimalFormat("#.##").format(20f),
					new DecimalFormat("#.##").format(15f));
		}
	}
	
	@Override
	protected RingBuff buff( ) {
		return new Energy();
	}
	
	public static float wandChargeMultiplier( Char target ){
		return (float)Math.pow(1.20, getBuffedBonus(target, Energy.class));
	}

	public static float artifactChargeMultiplier( Char target ){
		return (float)Math.pow(1.15, getBuffedBonus(target, Energy.class));
	}
	
	public class Energy extends RingBuff {
	}
}
