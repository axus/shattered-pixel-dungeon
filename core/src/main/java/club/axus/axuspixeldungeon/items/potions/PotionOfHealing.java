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

package club.axus.axuspixeldungeon.items.potions;

import club.axus.axuspixeldungeon.Challenges;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Bleeding;
import club.axus.axuspixeldungeon.actors.buffs.Blindness;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Cripple;
import club.axus.axuspixeldungeon.actors.buffs.Drowsy;
import club.axus.axuspixeldungeon.actors.buffs.Healing;
import club.axus.axuspixeldungeon.actors.buffs.Poison;
import club.axus.axuspixeldungeon.actors.buffs.Slow;
import club.axus.axuspixeldungeon.actors.buffs.Vertigo;
import club.axus.axuspixeldungeon.actors.buffs.Vulnerable;
import club.axus.axuspixeldungeon.actors.buffs.Weakness;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.utils.GLog;

public class PotionOfHealing extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_HEALING;

		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		cure( hero );
		heal( hero );
		Talent.onHealingPotionUsed( hero );
	}

	public static void heal( Char ch ){
		if (ch == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
			pharmacophobiaProc(Dungeon.hero);
		} else {
			//starts out healing 30 hp, equalizes with hero health total at level 11
			Buff.affect(ch, Healing.class).setHeal((int) (0.8f * ch.HT + 14), 0.25f, 0);
			if (ch == Dungeon.hero){
				GLog.p( Messages.get(PotionOfHealing.class, "heal") );
			}
		}
	}

	public static void pharmacophobiaProc( Hero hero ){
		// harms the hero for ~40% of their max HP in poison
		Buff.affect( hero, Poison.class).set(4 + hero.lvl/2);
	}
	
	public static void cure( Char ch ) {
		Buff.detach( ch, Poison.class );
		Buff.detach( ch, Cripple.class );
		Buff.detach( ch, Weakness.class );
		Buff.detach( ch, Vulnerable.class );
		Buff.detach( ch, Bleeding.class );
		Buff.detach( ch, Blindness.class );
		Buff.detach( ch, Drowsy.class );
		Buff.detach( ch, Slow.class );
		Buff.detach( ch, Vertigo.class);
	}

	@Override
	public int value() {
		return isKnown() ? 30 * quantity : super.value();
	}
}
