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

package club.axus.axuspixeldungeon.items.potions.brews;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.blobs.Blob;
import club.axus.axuspixeldungeon.actors.blobs.Electricity;
import club.axus.axuspixeldungeon.items.potions.AlchemicalCatalyst;
import club.axus.axuspixeldungeon.items.potions.PotionOfParalyticGas;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class ShockingBrew extends Brew {
	
	{
		image = ItemSpriteSheet.BREW_SHOCKING;
	}
	
	@Override
	public void shatter(int cell) {
		if (Dungeon.level.heroFOV[cell]) {
			splash( cell );
			Sample.INSTANCE.play( Assets.Sounds.SHATTER );
			Sample.INSTANCE.play(Assets.Sounds.LIGHTNING);
		}
		PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				GameScene.add(Blob.seed(i, 20, Electricity.class));
			}
		}
	}
	
	@Override
	public int value() {
		//prices of ingredients
		return quantity * (40 + 40);
	}
	
	public static class Recipe extends club.axus.axuspixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{PotionOfParalyticGas.class, AlchemicalCatalyst.class};
			inQuantity = new int[]{1, 1};
			
			cost = 8;
			
			output = ShockingBrew.class;
			outQuantity = 1;
		}
		
	}
}
