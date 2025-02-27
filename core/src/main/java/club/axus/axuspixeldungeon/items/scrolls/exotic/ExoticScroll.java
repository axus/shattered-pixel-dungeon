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

package club.axus.axuspixeldungeon.items.scrolls.exotic;

import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.Recipe;
import club.axus.axuspixeldungeon.items.scrolls.Scroll;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfIdentify;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfLullaby;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfMagicMapping;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfMirrorImage;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRage;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRecharging;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRetribution;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTeleportation;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTerror;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTransmutation;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfUpgrade;
import club.axus.axuspixeldungeon.items.stones.Runestone;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExoticScroll extends Scroll {
	
	
	public static final HashMap<Class<?extends Scroll>, Class<?extends ExoticScroll>> regToExo = new HashMap<>();
	public static final HashMap<Class<?extends ExoticScroll>, Class<?extends Scroll>> exoToReg = new HashMap<>();
	static{
		regToExo.put(ScrollOfIdentify.class, ScrollOfDivination.class);
		exoToReg.put(ScrollOfDivination.class, ScrollOfIdentify.class);
		
		regToExo.put(ScrollOfUpgrade.class, ScrollOfEnchantment.class);
		exoToReg.put(ScrollOfEnchantment.class, ScrollOfUpgrade.class);
		
		regToExo.put(ScrollOfTerror.class, ScrollOfPetrification.class);
		exoToReg.put(ScrollOfPetrification.class, ScrollOfTerror.class);
		
		regToExo.put(ScrollOfRemoveCurse.class, ScrollOfAntiMagic.class);
		exoToReg.put(ScrollOfAntiMagic.class, ScrollOfRemoveCurse.class);
		
		regToExo.put(ScrollOfLullaby.class, ScrollOfAffection.class);
		exoToReg.put(ScrollOfAffection.class, ScrollOfLullaby.class);
		
		regToExo.put(ScrollOfRage.class, ScrollOfConfusion.class);
		exoToReg.put(ScrollOfConfusion.class, ScrollOfRage.class);
		
		regToExo.put(ScrollOfTerror.class, ScrollOfPetrification.class);
		exoToReg.put(ScrollOfPetrification.class, ScrollOfTerror.class);
		
		regToExo.put(ScrollOfRecharging.class, ScrollOfMysticalEnergy.class);
		exoToReg.put(ScrollOfMysticalEnergy.class, ScrollOfRecharging.class);
		
		regToExo.put(ScrollOfMagicMapping.class, ScrollOfForesight.class);
		exoToReg.put(ScrollOfForesight.class, ScrollOfMagicMapping.class);
		
		regToExo.put(ScrollOfTeleportation.class, ScrollOfPassage.class);
		exoToReg.put(ScrollOfPassage.class, ScrollOfTeleportation.class);
		
		regToExo.put(ScrollOfRetribution.class, ScrollOfPsionicBlast.class);
		exoToReg.put(ScrollOfPsionicBlast.class, ScrollOfRetribution.class);
		
		regToExo.put(ScrollOfMirrorImage.class, ScrollOfPrismaticImage.class);
		exoToReg.put(ScrollOfPrismaticImage.class, ScrollOfMirrorImage.class);
		
		regToExo.put(ScrollOfTransmutation.class, ScrollOfPolymorph.class);
		exoToReg.put(ScrollOfPolymorph.class, ScrollOfTransmutation.class);
	}
	
	@Override
	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( exoToReg.get(this.getClass()) ));
	}
	
	@Override
	public void setKnown() {
		if (!isKnown()) {
			handler.know(exoToReg.get(this.getClass()));
			updateQuickslot();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		if (handler != null && handler.contains(exoToReg.get(this.getClass()))) {
			image = handler.image(exoToReg.get(this.getClass())) + 16;
			rune = handler.label(exoToReg.get(this.getClass()));
		}
	}
	
	@Override
	//20 gold more than its none-exotic equivalent
	public int value() {
		return (Reflection.newInstance(exoToReg.get(getClass())).value() + 20) * quantity;
	}
	
	public static class ScrollToExotic extends Recipe {
		
		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			int r = 0;
			Scroll s = null;
			
			for (Item i : ingredients){
				if (i instanceof Runestone){
					r++;
				} else if (regToExo.containsKey(i.getClass())) {
					s = (Scroll)i;
				}
			}
			
			return s != null && r == 2;
		}
		
		@Override
		public int cost(ArrayList<Item> ingredients) {
			return 0;
		}
		
		@Override
		public Item brew(ArrayList<Item> ingredients) {
			Item result = null;
			
			for (Item i : ingredients){
				i.quantity(i.quantity()-1);
				if (regToExo.containsKey(i.getClass())) {
					result = Reflection.newInstance(regToExo.get(i.getClass()));
				}
			}
			return result;
		}
		
		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			for (Item i : ingredients){
				if (regToExo.containsKey(i.getClass())) {
					return Reflection.newInstance(regToExo.get(i.getClass()));
				}
			}
			return null;
			
		}
	}
}
