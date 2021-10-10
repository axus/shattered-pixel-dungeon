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

package club.axus.axuspixeldungeon.levels.traps;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.mobs.Mob;
import club.axus.axuspixeldungeon.effects.CellEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.Heap;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTeleportation;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.utils.BArray;
import club.axus.axuspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WarpingTrap extends TeleportationTrap {

	{
		color = TEAL;
		shape = STARS;
	}

	@Override
	public void activate() {
		if (Dungeon.level.distance(Dungeon.hero.pos, pos) <= 1){
			BArray.setFalse(Dungeon.level.visited);
			BArray.setFalse(Dungeon.level.mapped);
		}

		super.activate();

		GameScene.updateFog(); //just in case hero wasn't moved
		Dungeon.observe();

	}
}
