/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package club.axus.axuspixeldungeon.items.food;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.CounterBuff;
import club.axus.axuspixeldungeon.actors.buffs.Hunger;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.items.Generator;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;

public class Berry extends Food {

	{
		image = ItemSpriteSheet.BERRY;
		energy = Hunger.HUNGRY/3f; //100 food value

		bones = false;
	}

	@Override
	protected float eatingTime(){
		if (Dungeon.hero.hasTalent(Talent.INVIGORATING_MEAL)){
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		SeedCounter counter = Buff.count(hero, SeedCounter.class, 1);
		if (counter.count() >= 2){
			Dungeon.level.drop(Generator.random(Generator.Category.SEED), hero.pos).sprite.drop();
			counter.detach();
		}
	}

	@Override
	public int value() {
		return 5 * quantity;
	}

	public static class SeedCounter extends CounterBuff{{revivePersists = true;}};
}
