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

package club.axus.axuspixeldungeon.actors.buffs;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Charm extends FlavourBuff {

	public int object = 0;
	public boolean ignoreHeroAllies = false;

	public static final float DURATION = 10f;

	{
		type = buffType.NEGATIVE;
		announced = true;
	}

	private static final String OBJECT          = "object";
	private static final String IGNORE_ALLIES    = "ignore_allies";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( OBJECT, object );
		bundle.put( IGNORE_ALLIES, ignoreHeroAllies );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		object = bundle.getInt( OBJECT );
		ignoreHeroAllies = bundle.getBoolean( IGNORE_ALLIES );
	}

	@Override
	public int icon() {
		return BuffIndicator.HEART;
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

	public boolean ignoreNextHit = false;

	public void recover(Object src) {
		if (ignoreHeroAllies && src instanceof Char){
			if (src != Dungeon.hero && ((Char) src).alignment == Char.Alignment.ALLY){
				return;
			}
		}

		if (ignoreNextHit){
			ignoreNextHit = false;
			return;
		}
		spend(-5f);
		if (cooldown() <= 0){
			detach();
		}
	}
}
