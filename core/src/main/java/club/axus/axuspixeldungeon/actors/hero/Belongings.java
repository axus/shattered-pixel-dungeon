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

package club.axus.axuspixeldungeon.actors.hero;

import club.axus.axuspixeldungeon.Badges;
import club.axus.axuspixeldungeon.GamesInProgress;
import club.axus.axuspixeldungeon.actors.buffs.LostInventory;
import club.axus.axuspixeldungeon.items.EquipableItem;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.KindOfWeapon;
import club.axus.axuspixeldungeon.items.KindofMisc;
import club.axus.axuspixeldungeon.items.armor.Armor;
import club.axus.axuspixeldungeon.items.armor.ClassArmor;
import club.axus.axuspixeldungeon.items.artifacts.Artifact;
import club.axus.axuspixeldungeon.items.bags.Bag;
import club.axus.axuspixeldungeon.items.rings.Ring;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import club.axus.axuspixeldungeon.items.wands.Wand;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class Belongings implements Iterable<Item> {

	private Hero owner;

	public static class Backpack extends Bag {
		public int capacity(){
			int cap = super.capacity();
			for (Item item : items){
				if (item instanceof Bag){
					cap++;
				}
			}
			return cap;
		}
	}

	public Backpack backpack;
	
	public Belongings( Hero owner ) {
		this.owner = owner;
		
		backpack = new Backpack();
		backpack.owner = owner;
	}

	public KindOfWeapon weapon = null;
	public Armor armor = null;
	public Artifact artifact = null;
	public KindofMisc misc = null;
	public Ring ring = null;

	//used when thrown weapons temporary become the current weapon
	public KindOfWeapon thrownWeapon = null;

	//*** these accessor methods are so that worn items can be affected by various effects/debuffs
	// we still want to access the raw equipped items in cases where effects should be ignored though,
	// such as when equipping something, showing an interface, or dealing with items from a dead hero

	public KindOfWeapon weapon(){
		//no point in lost invent check, if it's assigned it must be usable
		if (thrownWeapon != null) return thrownWeapon;

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		if (!lostInvent || (weapon != null && weapon.keptThoughLostInvent)){
			return weapon;
		} else {
			return null;
		}
	}

	public Armor armor(){
		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		if (!lostInvent || (armor != null && armor.keptThoughLostInvent)){
			return armor;
		} else {
			return null;
		}
	}

	public Artifact artifact(){
		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		if (!lostInvent || (artifact != null && artifact.keptThoughLostInvent)){
			return artifact;
		} else {
			return null;
		}
	}

	public KindofMisc misc(){
		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		if (!lostInvent || (misc != null && misc.keptThoughLostInvent)){
			return misc;
		} else {
			return null;
		}
	}

	public Ring ring(){
		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		if (!lostInvent || (ring != null && ring.keptThoughLostInvent)){
			return ring;
		} else {
			return null;
		}
	}

	// ***
	
	private static final String WEAPON		= "weapon";
	private static final String ARMOR		= "armor";
	private static final String ARTIFACT   = "artifact";
	private static final String MISC       = "misc";
	private static final String RING       = "ring";

	public void storeInBundle( Bundle bundle ) {
		
		backpack.storeInBundle( bundle );
		
		bundle.put( WEAPON, weapon );
		bundle.put( ARMOR, armor );
		bundle.put( ARTIFACT, artifact );
		bundle.put( MISC, misc );
		bundle.put( RING, ring );
	}
	
	public void restoreFromBundle( Bundle bundle ) {
		
		backpack.clear();
		backpack.restoreFromBundle( bundle );
		
		weapon = (KindOfWeapon) bundle.get(WEAPON);
		if (weapon() != null) {
			weapon().activate(owner);
		}
		
		armor = (Armor)bundle.get( ARMOR );
		if (armor() != null){
			armor().activate( owner );
		}

		//pre-0.8.2
		if (bundle.contains("misc1") || bundle.contains("misc2")){
			artifact = null;
			misc = null;
			ring = null;

			KindofMisc m = (KindofMisc)bundle.get("misc1");
			if (m instanceof Artifact){
				artifact = (Artifact) m;
			} else if (m instanceof Ring) {
				ring = (Ring) m;
			}

			m = (KindofMisc)bundle.get("misc2");
			if (m instanceof Artifact){
				if (artifact == null)   artifact = (Artifact) m;
				else                    misc = (Artifact) m;
			} else if (m instanceof Ring) {
				if (ring == null)       ring = (Ring) m;
				else                    misc = (Ring) m;
			}

		} else {
			artifact = (Artifact) bundle.get(ARTIFACT);
			misc = (KindofMisc) bundle.get(MISC);
			ring = (Ring) bundle.get(RING);
		}

		if (artifact() != null)   artifact().activate(owner);
		if (misc() != null)       misc().activate( owner );
		if (ring() != null)       ring().activate( owner );
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		if (bundle.contains( ARMOR )){
			Armor armor = ((Armor)bundle.get( ARMOR ));
			if (armor instanceof ClassArmor){
				info.armorTier = 6;
			} else {
				info.armorTier = armor.tier;
			}
		} else {
			info.armorTier = 0;
		}
	}

	//ignores lost inventory debuff
	public ArrayList<Bag> getBags(){
		ArrayList<Bag> result = new ArrayList<>();

		result.add(backpack);

		for (Item i : this){
			if (i instanceof Bag){
				result.add((Bag)i);
			}
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	public<T extends Item> T getItem( Class<T> itemClass ) {

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;

		for (Item item : this) {
			if (itemClass.isInstance( item )) {
				if (!lostInvent || item.keptThoughLostInvent) {
					return (T) item;
				}
			}
		}
		
		return null;
	}

	public<T extends Item> ArrayList<T> getAllItems( Class<T> itemClass ) {
		ArrayList<T> result = new ArrayList<>();

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;

		for (Item item : this) {
			if (itemClass.isInstance( item )) {
				if (!lostInvent || item.keptThoughLostInvent) {
					result.add((T) item);
				}
			}
		}

		return result;
	}
	
	public boolean contains( Item contains ){

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		
		for (Item item : this) {
			if (contains == item) {
				if (!lostInvent || item.keptThoughLostInvent) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Item getSimilar( Item similar ){

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		
		for (Item item : this) {
			if (similar != item && similar.isSimilar(item)) {
				if (!lostInvent || item.keptThoughLostInvent) {
					return item;
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Item> getAllSimilar( Item similar ){
		ArrayList<Item> result = new ArrayList<>();

		boolean lostInvent = owner != null && owner.buff(LostInventory.class) != null;
		
		for (Item item : this) {
			if (item != similar && similar.isSimilar(item)) {
				if (!lostInvent || item.keptThoughLostInvent) {
					result.add(item);
				}
			}
		}
		
		return result;
	}

	//triggers when a run ends, so ignores lost inventory effects
	public void identify() {
		for (Item item : this) {
			item.identify();
		}
	}
	
	public void observe() {
		if (weapon() != null) {
			weapon().identify();
			Badges.validateItemLevelAquired(weapon());
		}
		if (armor() != null) {
			armor().identify();
			Badges.validateItemLevelAquired(armor());
		}
		if (artifact() != null) {
			artifact().identify();
			Badges.validateItemLevelAquired(artifact());
		}
		if (misc() != null) {
			misc().identify();
			Badges.validateItemLevelAquired(misc());
		}
		if (ring() != null) {
			ring().identify();
			Badges.validateItemLevelAquired(ring());
		}
		for (Item item : backpack) {
			if (item instanceof EquipableItem || item instanceof Wand) {
				item.cursedKnown = true;
			}
		}
	}
	
	public void uncurseEquipped() {
		ScrollOfRemoveCurse.uncurse( owner, armor(), weapon(), artifact(), misc(), ring());
	}
	
	public Item randomUnequipped() {
		if (owner.buff(LostInventory.class) != null) return null;

		return Random.element( backpack.items );
	}
	
	public int charge( float charge ) {
		
		int count = 0;
		
		for (Wand.Charger charger : owner.buffs(Wand.Charger.class)){
			charger.gainCharge(charge);
			count++;
		}
		
		return count;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}
	
	private class ItemIterator implements Iterator<Item> {

		private int index = 0;
		
		private Iterator<Item> backpackIterator = backpack.iterator();
		
		private Item[] equipped = {weapon, armor, artifact, misc, ring};
		private int backpackIndex = equipped.length;
		
		@Override
		public boolean hasNext() {
			
			for (int i=index; i < backpackIndex; i++) {
				if (equipped[i] != null) {
					return true;
				}
			}
			
			return backpackIterator.hasNext();
		}

		@Override
		public Item next() {
			
			while (index < backpackIndex) {
				Item item = equipped[index++];
				if (item != null) {
					return item;
				}
			}
			
			return backpackIterator.next();
		}

		@Override
		public void remove() {
			switch (index) {
			case 0:
				equipped[0] = weapon = null;
				break;
			case 1:
				equipped[1] = armor = null;
				break;
			case 2:
				equipped[2] = artifact = null;
				break;
			case 3:
				equipped[3] = misc = null;
				break;
			case 4:
				equipped[4] = ring = null;
				break;
			default:
				backpackIterator.remove();
			}
		}
	}
}
