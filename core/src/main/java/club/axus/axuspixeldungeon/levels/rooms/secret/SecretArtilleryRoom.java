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

package club.axus.axuspixeldungeon.levels.rooms.secret;

import club.axus.axuspixeldungeon.items.Generator;
import club.axus.axuspixeldungeon.items.bombs.Bomb;
import club.axus.axuspixeldungeon.levels.Level;
import club.axus.axuspixeldungeon.levels.Terrain;
import club.axus.axuspixeldungeon.levels.painters.Painter;

public class SecretArtilleryRoom extends SecretRoom {
	
	@Override
	public void paint(Level level) {
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY_SP);
		
		Painter.set(level, center(), Terrain.STATUE_SP);
		
		for (int i = 0; i < 3; i++){
			int itemPos;
			do{
				itemPos = level.pointToCell(random());
			} while ( level.map[itemPos] != Terrain.EMPTY_SP
					|| level.heaps.get(itemPos) != null);
			
			if( i == 0 ){
				level.drop(new Bomb.DoubleBomb(), itemPos);
			} else {
				level.drop(Generator.randomMissile(), itemPos);
			}
		}
		
		entrance().set(Door.Type.HIDDEN);
	}
}
