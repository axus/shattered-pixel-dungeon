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

package club.axus.axuspixeldungeon.actors.mobs.npcs;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.sprites.CharSprite;
import club.axus.axuspixeldungeon.sprites.SheepSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Sheep extends NPC {

	private static final String[] LINE_KEYS = {"Baa!", "Baa?", "Baa.", "Baa..."};

	{
		spriteClass = SheepSprite.class;
	}

	public float lifespan;

	private boolean initialized = false;

	@Override
	protected boolean act() {
		if (initialized) {
			HP = 0;

			destroy();
			sprite.die();

		} else {
			initialized = true;
			spend( lifespan + Random.Float(2) );
		}
		return true;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return INFINITE_EVASION;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}

	@Override
	public void add( Buff buff ) {
	}

	@Override
	public boolean interact(Char c) {
		sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, Random.element( LINE_KEYS )) );
		if (c == Dungeon.hero) {
			Dungeon.hero.spendAndNext(1f);
			Sample.INSTANCE.play(Assets.Sounds.SHEEP, 1, Random.Float(0.91f, 1.1f));
		}
		return true;
	}
}