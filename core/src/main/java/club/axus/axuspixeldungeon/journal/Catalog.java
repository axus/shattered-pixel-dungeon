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

package club.axus.axuspixeldungeon.journal;

import club.axus.axuspixeldungeon.Badges;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.armor.ClothArmor;
import club.axus.axuspixeldungeon.items.armor.HuntressArmor;
import club.axus.axuspixeldungeon.items.armor.LeatherArmor;
import club.axus.axuspixeldungeon.items.armor.MageArmor;
import club.axus.axuspixeldungeon.items.armor.MailArmor;
import club.axus.axuspixeldungeon.items.armor.PlateArmor;
import club.axus.axuspixeldungeon.items.armor.RogueArmor;
import club.axus.axuspixeldungeon.items.armor.ScaleArmor;
import club.axus.axuspixeldungeon.items.armor.WarriorArmor;
import club.axus.axuspixeldungeon.items.artifacts.AlchemistsToolkit;
import club.axus.axuspixeldungeon.items.artifacts.ChaliceOfBlood;
import club.axus.axuspixeldungeon.items.artifacts.CloakOfShadows;
import club.axus.axuspixeldungeon.items.artifacts.DriedRose;
import club.axus.axuspixeldungeon.items.artifacts.EtherealChains;
import club.axus.axuspixeldungeon.items.artifacts.HornOfPlenty;
import club.axus.axuspixeldungeon.items.artifacts.MasterThievesArmband;
import club.axus.axuspixeldungeon.items.artifacts.SandalsOfNature;
import club.axus.axuspixeldungeon.items.artifacts.TalismanOfForesight;
import club.axus.axuspixeldungeon.items.artifacts.TimekeepersHourglass;
import club.axus.axuspixeldungeon.items.artifacts.UnstableSpellbook;
import club.axus.axuspixeldungeon.items.potions.PotionOfExperience;
import club.axus.axuspixeldungeon.items.potions.PotionOfFrost;
import club.axus.axuspixeldungeon.items.potions.PotionOfHaste;
import club.axus.axuspixeldungeon.items.potions.PotionOfHealing;
import club.axus.axuspixeldungeon.items.potions.PotionOfInvisibility;
import club.axus.axuspixeldungeon.items.potions.PotionOfLevitation;
import club.axus.axuspixeldungeon.items.potions.PotionOfLiquidFlame;
import club.axus.axuspixeldungeon.items.potions.PotionOfMindVision;
import club.axus.axuspixeldungeon.items.potions.PotionOfParalyticGas;
import club.axus.axuspixeldungeon.items.potions.PotionOfPurity;
import club.axus.axuspixeldungeon.items.potions.PotionOfStrength;
import club.axus.axuspixeldungeon.items.potions.PotionOfToxicGas;
import club.axus.axuspixeldungeon.items.rings.RingOfAccuracy;
import club.axus.axuspixeldungeon.items.rings.RingOfElements;
import club.axus.axuspixeldungeon.items.rings.RingOfEnergy;
import club.axus.axuspixeldungeon.items.rings.RingOfEvasion;
import club.axus.axuspixeldungeon.items.rings.RingOfForce;
import club.axus.axuspixeldungeon.items.rings.RingOfFuror;
import club.axus.axuspixeldungeon.items.rings.RingOfHaste;
import club.axus.axuspixeldungeon.items.rings.RingOfMight;
import club.axus.axuspixeldungeon.items.rings.RingOfSharpshooting;
import club.axus.axuspixeldungeon.items.rings.RingOfTenacity;
import club.axus.axuspixeldungeon.items.rings.RingOfWealth;
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
import club.axus.axuspixeldungeon.items.wands.WandOfBlastWave;
import club.axus.axuspixeldungeon.items.wands.WandOfCorrosion;
import club.axus.axuspixeldungeon.items.wands.WandOfCorruption;
import club.axus.axuspixeldungeon.items.wands.WandOfDisintegration;
import club.axus.axuspixeldungeon.items.wands.WandOfFireblast;
import club.axus.axuspixeldungeon.items.wands.WandOfFrost;
import club.axus.axuspixeldungeon.items.wands.WandOfLightning;
import club.axus.axuspixeldungeon.items.wands.WandOfLivingEarth;
import club.axus.axuspixeldungeon.items.wands.WandOfMagicMissile;
import club.axus.axuspixeldungeon.items.wands.WandOfPrismaticLight;
import club.axus.axuspixeldungeon.items.wands.WandOfRegrowth;
import club.axus.axuspixeldungeon.items.wands.WandOfTransfusion;
import club.axus.axuspixeldungeon.items.wands.WandOfWarding;
import club.axus.axuspixeldungeon.items.weapon.melee.AssassinsBlade;
import club.axus.axuspixeldungeon.items.weapon.melee.BattleAxe;
import club.axus.axuspixeldungeon.items.weapon.melee.Crossbow;
import club.axus.axuspixeldungeon.items.weapon.melee.Dagger;
import club.axus.axuspixeldungeon.items.weapon.melee.Dirk;
import club.axus.axuspixeldungeon.items.weapon.melee.Flail;
import club.axus.axuspixeldungeon.items.weapon.melee.Gauntlet;
import club.axus.axuspixeldungeon.items.weapon.melee.Glaive;
import club.axus.axuspixeldungeon.items.weapon.melee.Gloves;
import club.axus.axuspixeldungeon.items.weapon.melee.Greataxe;
import club.axus.axuspixeldungeon.items.weapon.melee.Greatshield;
import club.axus.axuspixeldungeon.items.weapon.melee.Greatsword;
import club.axus.axuspixeldungeon.items.weapon.melee.HandAxe;
import club.axus.axuspixeldungeon.items.weapon.melee.Longsword;
import club.axus.axuspixeldungeon.items.weapon.melee.Mace;
import club.axus.axuspixeldungeon.items.weapon.melee.MagesStaff;
import club.axus.axuspixeldungeon.items.weapon.melee.Quarterstaff;
import club.axus.axuspixeldungeon.items.weapon.melee.RoundShield;
import club.axus.axuspixeldungeon.items.weapon.melee.RunicBlade;
import club.axus.axuspixeldungeon.items.weapon.melee.Sai;
import club.axus.axuspixeldungeon.items.weapon.melee.Scimitar;
import club.axus.axuspixeldungeon.items.weapon.melee.Shortsword;
import club.axus.axuspixeldungeon.items.weapon.melee.Spear;
import club.axus.axuspixeldungeon.items.weapon.melee.Sword;
import club.axus.axuspixeldungeon.items.weapon.melee.WarHammer;
import club.axus.axuspixeldungeon.items.weapon.melee.Whip;
import club.axus.axuspixeldungeon.items.weapon.melee.WornShortsword;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {
	
	WEAPONS,
	ARMOR,
	WANDS,
	RINGS,
	ARTIFACTS,
	POTIONS,
	SCROLLS;
	
	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}
	
	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}
	
	static {
		WEAPONS.seen.put( WornShortsword.class,             false);
		WEAPONS.seen.put( Gloves.class,                     false);
		WEAPONS.seen.put( Dagger.class,                     false);
		WEAPONS.seen.put( MagesStaff.class,                 false);
		WEAPONS.seen.put( Shortsword.class,                 false);
		WEAPONS.seen.put( HandAxe.class,                    false);
		WEAPONS.seen.put( Spear.class,                      false);
		WEAPONS.seen.put( Quarterstaff.class,               false);
		WEAPONS.seen.put( Dirk.class,                       false);
		WEAPONS.seen.put( Sword.class,                      false);
		WEAPONS.seen.put( Mace.class,                       false);
		WEAPONS.seen.put( Scimitar.class,                   false);
		WEAPONS.seen.put( RoundShield.class,                false);
		WEAPONS.seen.put( Sai.class,                        false);
		WEAPONS.seen.put( Whip.class,                       false);
		WEAPONS.seen.put( Longsword.class,                  false);
		WEAPONS.seen.put( BattleAxe.class,                  false);
		WEAPONS.seen.put( Flail.class,                      false);
		WEAPONS.seen.put( RunicBlade.class,                 false);
		WEAPONS.seen.put( AssassinsBlade.class,             false);
		WEAPONS.seen.put( Crossbow.class,                   false);
		WEAPONS.seen.put( Greatsword.class,                 false);
		WEAPONS.seen.put( WarHammer.class,                  false);
		WEAPONS.seen.put( Glaive.class,                     false);
		WEAPONS.seen.put( Greataxe.class,                   false);
		WEAPONS.seen.put( Greatshield.class,                false);
		WEAPONS.seen.put( Gauntlet.class,                   false);
	
		ARMOR.seen.put( ClothArmor.class,                   false);
		ARMOR.seen.put( LeatherArmor.class,                 false);
		ARMOR.seen.put( MailArmor.class,                    false);
		ARMOR.seen.put( ScaleArmor.class,                   false);
		ARMOR.seen.put( PlateArmor.class,                   false);
		ARMOR.seen.put( WarriorArmor.class,                 false);
		ARMOR.seen.put( MageArmor.class,                    false);
		ARMOR.seen.put( RogueArmor.class,                   false);
		ARMOR.seen.put( HuntressArmor.class,                false);
	
		WANDS.seen.put( WandOfMagicMissile.class,           false);
		WANDS.seen.put( WandOfLightning.class,              false);
		WANDS.seen.put( WandOfDisintegration.class,         false);
		WANDS.seen.put( WandOfFireblast.class,              false);
		WANDS.seen.put( WandOfCorrosion.class,              false);
		WANDS.seen.put( WandOfBlastWave.class,              false);
		WANDS.seen.put( WandOfLivingEarth.class,            false);
		WANDS.seen.put( WandOfFrost.class,                  false);
		WANDS.seen.put( WandOfPrismaticLight.class,         false);
		WANDS.seen.put( WandOfWarding.class,                false);
		WANDS.seen.put( WandOfTransfusion.class,            false);
		WANDS.seen.put( WandOfCorruption.class,             false);
		WANDS.seen.put( WandOfRegrowth.class,               false);
	
		RINGS.seen.put( RingOfAccuracy.class,               false);
		RINGS.seen.put( RingOfEnergy.class,                 false);
		RINGS.seen.put( RingOfElements.class,               false);
		RINGS.seen.put( RingOfEvasion.class,                false);
		RINGS.seen.put( RingOfForce.class,                  false);
		RINGS.seen.put( RingOfFuror.class,                  false);
		RINGS.seen.put( RingOfHaste.class,                  false);
		RINGS.seen.put( RingOfMight.class,                  false);
		RINGS.seen.put( RingOfSharpshooting.class,          false);
		RINGS.seen.put( RingOfTenacity.class,               false);
		RINGS.seen.put( RingOfWealth.class,                 false);
	
		ARTIFACTS.seen.put( AlchemistsToolkit.class,        false);
		//ARTIFACTS.seen.put( CapeOfThorns.class,             false);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           false);
		ARTIFACTS.seen.put( CloakOfShadows.class,           false);
		ARTIFACTS.seen.put( DriedRose.class,                false);
		ARTIFACTS.seen.put( EtherealChains.class,           false);
		ARTIFACTS.seen.put( HornOfPlenty.class,             false);
		//ARTIFACTS.seen.put( LloydsBeacon.class,             false);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     false);
		ARTIFACTS.seen.put( SandalsOfNature.class,          false);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      false);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     false);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        false);
	
		POTIONS.seen.put( PotionOfHealing.class,            false);
		POTIONS.seen.put( PotionOfStrength.class,           false);
		POTIONS.seen.put( PotionOfLiquidFlame.class,        false);
		POTIONS.seen.put( PotionOfFrost.class,              false);
		POTIONS.seen.put( PotionOfToxicGas.class,           false);
		POTIONS.seen.put( PotionOfParalyticGas.class,       false);
		POTIONS.seen.put( PotionOfPurity.class,             false);
		POTIONS.seen.put( PotionOfLevitation.class,         false);
		POTIONS.seen.put( PotionOfMindVision.class,         false);
		POTIONS.seen.put( PotionOfInvisibility.class,       false);
		POTIONS.seen.put( PotionOfExperience.class,         false);
		POTIONS.seen.put( PotionOfHaste.class,              false);
	
		SCROLLS.seen.put( ScrollOfIdentify.class,           false);
		SCROLLS.seen.put( ScrollOfUpgrade.class,            false);
		SCROLLS.seen.put( ScrollOfRemoveCurse.class,        false);
		SCROLLS.seen.put( ScrollOfMagicMapping.class,       false);
		SCROLLS.seen.put( ScrollOfTeleportation.class,      false);
		SCROLLS.seen.put( ScrollOfRecharging.class,         false);
		SCROLLS.seen.put( ScrollOfMirrorImage.class,        false);
		SCROLLS.seen.put( ScrollOfTerror.class,             false);
		SCROLLS.seen.put( ScrollOfLullaby.class,            false);
		SCROLLS.seen.put( ScrollOfRage.class,               false);
		SCROLLS.seen.put( ScrollOfRetribution.class,        false);
		SCROLLS.seen.put( ScrollOfTransmutation.class,      false);
	}
	
	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(ARMOR, Badges.Badge.ALL_ARMOR_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(POTIONS, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(SCROLLS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}
	
	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}
	
	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}
	
	private static final String CATALOG_ITEMS = "catalog_items";
	
	public static void store( Bundle bundle ){
		
		Badges.loadGlobal();
		
		ArrayList<Class> seen = new ArrayList<>();
		
		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item);
					}
				}
			}
		}
		
		bundle.put( CATALOG_ITEMS, seen.toArray(new Class[0]) );
		
	}
	
	public static void restore( Bundle bundle ){
		
		Badges.loadGlobal();
		
		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}
		
		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}
		
		//general save/load
		//includes "catalogs" for pre-0.8.2 saves
		if (bundle.contains("catalogs") || bundle.contains(CATALOG_ITEMS)) {
			List<Class> seenClasses = new ArrayList<>();
			if (bundle.contains(CATALOG_ITEMS)) {
				seenClasses = Arrays.asList(bundle.getClassArray(CATALOG_ITEMS));
			}
			List<String> seenItems = new ArrayList<>();
			if (bundle.contains("catalogs")) {
				Journal.saveNeeded = true; //we want to overwrite with the newer storage format
				seenItems = Arrays.asList(bundle.getStringArray("catalogs"));
			}
			
			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seenClasses.contains(item) || seenItems.contains(item.getSimpleName())) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}
	
}
