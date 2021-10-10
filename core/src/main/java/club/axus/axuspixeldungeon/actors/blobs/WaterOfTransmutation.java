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

package club.axus.axuspixeldungeon.actors.blobs;

import club.axus.axuspixeldungeon.Challenges;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.effects.BlobEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.Generator;
import club.axus.axuspixeldungeon.items.Generator.Category;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.artifacts.Artifact;
import club.axus.axuspixeldungeon.items.potions.Potion;
import club.axus.axuspixeldungeon.items.potions.PotionOfStrength;
import club.axus.axuspixeldungeon.items.rings.Ring;
import club.axus.axuspixeldungeon.items.scrolls.Scroll;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTransmutation;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfUpgrade;
import club.axus.axuspixeldungeon.items.wands.Wand;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.items.weapon.melee.MagesStaff;
import club.axus.axuspixeldungeon.items.weapon.melee.MeleeWeapon;
import club.axus.axuspixeldungeon.journal.Catalog;
import club.axus.axuspixeldungeon.journal.Notes.Landmark;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.plants.Plant;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class WaterOfTransmutation extends WellWater {
	
	@Override
	protected Item affectItem( Item item, int pos ) {
		
		item = ScrollOfTransmutation.changeItem(item);
		
		//incase a never-seen item pops out
		if (item != null&& item.isIdentified()){
			Catalog.setSeen(item.getClass());
		}

		return item;

	}
	
	@Override
	protected boolean affectHero(Hero hero) {
		return false;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( Speck.factory( Speck.CHANGE ), 0.2f, 0 );
	}
	
	@Override
	protected Landmark record() {
		return Landmark.WELL_OF_TRANSMUTATION;
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
