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
import club.axus.axuspixeldungeon.items.Heap;
import club.axus.axuspixeldungeon.levels.Level;
import club.axus.axuspixeldungeon.levels.Terrain;
import club.axus.axuspixeldungeon.levels.painters.Painter;
import club.axus.axuspixeldungeon.levels.traps.SummoningTrap;
import com.watabou.utils.Point;

public class SecretSummoningRoom extends SecretRoom {
	
	//minimum of 3x3 traps, max of 6x6 traps
	
	@Override
	public int maxWidth() {
		return 8;
	}
	
	@Override
	public int maxHeight() {
		return 8;
	}
	
	@Override
	public void paint(Level level) {
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.SECRET_TRAP);
		
		Point center = center();
		level.drop(Generator.random(), level.pointToCell(center)).setHauntedIfCursed().type = Heap.Type.SKELETON;
		
		for (Point p : getPoints()){
			int cell = level.pointToCell(p);
			if (level.map[cell] == Terrain.SECRET_TRAP){
				level.setTrap(new SummoningTrap().hide(), cell);
			}
		}
		
		entrance().set(Door.Type.HIDDEN);
	}
	
}
