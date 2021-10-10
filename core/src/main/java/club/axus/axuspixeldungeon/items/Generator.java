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

package club.axus.axuspixeldungeon.items;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.items.armor.Armor;
import club.axus.axuspixeldungeon.items.armor.ClothArmor;
import club.axus.axuspixeldungeon.items.armor.LeatherArmor;
import club.axus.axuspixeldungeon.items.armor.MailArmor;
import club.axus.axuspixeldungeon.items.armor.PlateArmor;
import club.axus.axuspixeldungeon.items.armor.ScaleArmor;
import club.axus.axuspixeldungeon.items.artifacts.AlchemistsToolkit;
import club.axus.axuspixeldungeon.items.artifacts.Artifact;
import club.axus.axuspixeldungeon.items.artifacts.CapeOfThorns;
import club.axus.axuspixeldungeon.items.artifacts.ChaliceOfBlood;
import club.axus.axuspixeldungeon.items.artifacts.CloakOfShadows;
import club.axus.axuspixeldungeon.items.artifacts.DriedRose;
import club.axus.axuspixeldungeon.items.artifacts.EtherealChains;
import club.axus.axuspixeldungeon.items.artifacts.HornOfPlenty;
import club.axus.axuspixeldungeon.items.artifacts.LloydsBeacon;
import club.axus.axuspixeldungeon.items.artifacts.MasterThievesArmband;
import club.axus.axuspixeldungeon.items.artifacts.SandalsOfNature;
import club.axus.axuspixeldungeon.items.artifacts.TalismanOfForesight;
import club.axus.axuspixeldungeon.items.artifacts.TimekeepersHourglass;
import club.axus.axuspixeldungeon.items.artifacts.UnstableSpellbook;
import club.axus.axuspixeldungeon.items.food.Food;
import club.axus.axuspixeldungeon.items.food.MysteryMeat;
import club.axus.axuspixeldungeon.items.food.Pasty;
import club.axus.axuspixeldungeon.items.potions.Potion;
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
import club.axus.axuspixeldungeon.items.rings.Ring;
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
import club.axus.axuspixeldungeon.items.stones.StoneOfFear;
import club.axus.axuspixeldungeon.items.stones.StoneOfAggression;
import club.axus.axuspixeldungeon.items.stones.StoneOfAugmentation;
import club.axus.axuspixeldungeon.items.stones.StoneOfBlast;
import club.axus.axuspixeldungeon.items.stones.StoneOfBlink;
import club.axus.axuspixeldungeon.items.stones.StoneOfClairvoyance;
import club.axus.axuspixeldungeon.items.stones.StoneOfDeepSleep;
import club.axus.axuspixeldungeon.items.stones.StoneOfDisarming;
import club.axus.axuspixeldungeon.items.stones.StoneOfEnchantment;
import club.axus.axuspixeldungeon.items.stones.StoneOfFlock;
import club.axus.axuspixeldungeon.items.stones.StoneOfIntuition;
import club.axus.axuspixeldungeon.items.stones.StoneOfShock;
import club.axus.axuspixeldungeon.items.wands.Wand;
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
import club.axus.axuspixeldungeon.items.weapon.melee.MeleeWeapon;
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
import club.axus.axuspixeldungeon.items.weapon.missiles.Bolas;
import club.axus.axuspixeldungeon.items.weapon.missiles.FishingSpear;
import club.axus.axuspixeldungeon.items.weapon.missiles.ForceCube;
import club.axus.axuspixeldungeon.items.weapon.missiles.HeavyBoomerang;
import club.axus.axuspixeldungeon.items.weapon.missiles.Javelin;
import club.axus.axuspixeldungeon.items.weapon.missiles.Kunai;
import club.axus.axuspixeldungeon.items.weapon.missiles.MissileWeapon;
import club.axus.axuspixeldungeon.items.weapon.missiles.Shuriken;
import club.axus.axuspixeldungeon.items.weapon.missiles.ThrowingClub;
import club.axus.axuspixeldungeon.items.weapon.missiles.ThrowingHammer;
import club.axus.axuspixeldungeon.items.weapon.missiles.ThrowingKnife;
import club.axus.axuspixeldungeon.items.weapon.missiles.ThrowingSpear;
import club.axus.axuspixeldungeon.items.weapon.missiles.ThrowingStone;
import club.axus.axuspixeldungeon.items.weapon.missiles.Tomahawk;
import club.axus.axuspixeldungeon.items.weapon.missiles.Trident;
import club.axus.axuspixeldungeon.plants.Blindweed;
import club.axus.axuspixeldungeon.plants.Dreamfoil;
import club.axus.axuspixeldungeon.plants.Earthroot;
import club.axus.axuspixeldungeon.plants.Fadeleaf;
import club.axus.axuspixeldungeon.plants.Firebloom;
import club.axus.axuspixeldungeon.plants.Icecap;
import club.axus.axuspixeldungeon.plants.Plant;
import club.axus.axuspixeldungeon.plants.Rotberry;
import club.axus.axuspixeldungeon.plants.Sorrowmoss;
import club.axus.axuspixeldungeon.plants.Starflower;
import club.axus.axuspixeldungeon.plants.Stormvine;
import club.axus.axuspixeldungeon.plants.Sungrass;
import club.axus.axuspixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 4,    MeleeWeapon.class),
		WEP_T1	( 0,    MeleeWeapon.class),
		WEP_T2	( 0,    MeleeWeapon.class),
		WEP_T3	( 0,    MeleeWeapon.class),
		WEP_T4	( 0,    MeleeWeapon.class),
		WEP_T5	( 0,    MeleeWeapon.class),
		
		ARMOR	( 3,    Armor.class ),
		
		MISSILE ( 3,    MissileWeapon.class ),
		MIS_T1  ( 0,    MissileWeapon.class ),
		MIS_T2  ( 0,    MissileWeapon.class ),
		MIS_T3  ( 0,    MissileWeapon.class ),
		MIS_T4  ( 0,    MissileWeapon.class ),
		MIS_T5  ( 0,    MissileWeapon.class ),
		
		WAND	( 2,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		
		FOOD	( 0,    Food.class ),
		
		POTION	( 16,   Potion.class ),
		SEED	( 2,    Plant.Seed.class ),
		
		SCROLL	( 16,   Scroll.class ),
		STONE   ( 2,    Runestone.class),
		
		GOLD	( 20,   Gold.class );
		
		public Class<?>[] classes;

		//some item types use a deck-based system, where the probs decrement as items are picked
		// until they are all 0, and then they reset. Those generator classes should define
		// defaultProbs. If defaultProbs is null then a deck system isn't used.
		//Artifacts in particular don't reset, no duplicates!
		public float[] probs;
		public float[] defaultProbs = null;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}

			//items without a category-defined order are sorted based on the spritesheet
			return Short.MAX_VALUE+item.image();
		}

		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			POTION.classes = new Class<?>[]{
					PotionOfStrength.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.defaultProbs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };
			POTION.probs = POTION.defaultProbs.clone();
			
			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Sungrass.Seed.class,
					Fadeleaf.Seed.class,
					Icecap.Seed.class,
					Firebloom.Seed.class,
					Sorrowmoss.Seed.class,
					Swiftthistle.Seed.class,
					Blindweed.Seed.class,
					Stormvine.Seed.class,
					Earthroot.Seed.class,
					Dreamfoil.Seed.class,
					Starflower.Seed.class};
			SEED.defaultProbs = new float[]{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2 };
			SEED.probs = SEED.defaultProbs.clone();
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class, //3 drop every chapter, see Dungeon.souNeeded()
					ScrollOfIdentify.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfRetribution.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};
			SCROLL.defaultProbs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };
			SCROLL.probs = SCROLL.defaultProbs.clone();
			
			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,   //1 is guaranteed to drop on floors 6-19
					StoneOfIntuition.class,     //1 additional stone is also dropped on floors 1-3
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class,
					StoneOfBlink.class,
					StoneOfDeepSleep.class,
					StoneOfClairvoyance.class,
					StoneOfAggression.class,
					StoneOfBlast.class,
					StoneOfFear.class,
					StoneOfAugmentation.class  //1 is sold in each shop
			};
			STONE.defaultProbs = new float[]{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0 };
			STONE.probs = STONE.defaultProbs.clone();

			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Gloves.class,
					Dagger.class,
					MagesStaff.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 0 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4 };
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Crossbow.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class
			};
			MIS_T1.probs = new float[]{ 6, 5 };
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.probs = new float[]{ 6, 5, 4 };
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.probs = new float[]{ 6, 5, 4 };
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.probs = new float[]{ 6, 5, 4 };
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class
			};
			MIS_T5.probs = new float[]{ 6, 5, 4 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class,
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.defaultProbs = new float[]{ 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1};
			ARTIFACT.probs = ARTIFACT.defaultProbs.clone();
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 75, 20,  4,  1},
			{0, 25, 50, 20,  5},
			{0,  0, 40, 50, 10},
			{0,  0, 20, 40, 40},
			{0,  0,  0, 20, 80}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();

	public static void fullReset() {
		generalReset();
		for (Category cat : Category.values()) {
			reset(cat);
		}
	}

	public static void generalReset(){
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}

	public static void reset(Category cat){
		if (cat.defaultProbs != null) cat.probs = cat.defaultProbs.clone();
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			generalReset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				int i = Random.chances(cat.probs);
				if (i == -1) {
					reset(cat);
					i = Random.chances(cat.probs);
				}
				if (cat.defaultProbs != null) cat.probs[i]--;
				return ((Item) Reflection.newInstance(cat.classes[i])).random();
		}
	}

	//overrides any deck systems and always uses default probs
	public static Item randomUsingDefaults( Category cat ){
		if (cat.defaultProbs == null) {
			return random(cat); //currently covers weapons/armor/missiles
		} else {
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbs)])).random();
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		return Reflection.newInstance(cl).random();
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Armor a = (Armor)Reflection.newInstance(Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])]);
		a.random();
		return a;
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static MeleeWeapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
		MeleeWeapon w = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}
	
	public static MissileWeapon randomMissile(int floorSet) {
		
		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Category c = misTiers[Random.chances(floorSetTierProbs[floorSet])];
		MissileWeapon w = (MissileWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		Category cat = Category.ARTIFACT;
		int i = Random.chances( cat.probs );

		//if no artifacts are left, return null
		if (i == -1){
			return null;
		}

		cat.probs[i]--;
		return (Artifact) Reflection.newInstance((Class<? extends Artifact>) cat.classes[i]).random();

	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++){
			if (cat.classes[i].equals(artifact) && cat.probs[i] > 0) {
				cat.probs[i] = 0;
				return true;
			}
		}
		return false;
	}

	private static final String GENERAL_PROBS = "general_probs";
	private static final String CATEGORY_PROBS = "_probs";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);

		for (Category cat : Category.values()){
			if (cat.defaultProbs == null) continue;
			boolean needsStore = false;
			for (int i = 0; i < cat.probs.length; i++){
				if (cat.probs[i] != cat.defaultProbs[i]){
					needsStore = true;
					break;
				}
			}

			if (needsStore){
				bundle.put(cat.name().toLowerCase() + CATEGORY_PROBS, cat.probs);
			}
		}
	}

	public static void restoreFromBundle(Bundle bundle) {
		fullReset();

		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		}

		for (Category cat : Category.values()){
			if (bundle.contains(cat.name().toLowerCase() + CATEGORY_PROBS)){
				float[] probs = bundle.getFloatArray(cat.name().toLowerCase() + CATEGORY_PROBS);
				if (cat.defaultProbs != null && probs.length == cat.defaultProbs.length){
					cat.probs = probs;
				}
			}
		}

		//pre-0.8.1
		if (bundle.contains("spawned_artifacts")) {
			for (Class<? extends Artifact> artifact : bundle.getClassArray("spawned_artifacts")) {
				Category cat = Category.ARTIFACT;
				for (int i = 0; i < cat.classes.length; i++) {
					if (cat.classes[i].equals(artifact)) {
						cat.probs[i] = 0;
					}
				}
			}
		}
		
	}
}
