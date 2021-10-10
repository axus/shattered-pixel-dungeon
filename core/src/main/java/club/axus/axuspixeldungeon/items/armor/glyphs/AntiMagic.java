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

package club.axus.axuspixeldungeon.items.armor.glyphs;

import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Charm;
import club.axus.axuspixeldungeon.actors.buffs.Degrade;
import club.axus.axuspixeldungeon.actors.buffs.Hex;
import club.axus.axuspixeldungeon.actors.buffs.MagicalSleep;
import club.axus.axuspixeldungeon.actors.buffs.Vulnerable;
import club.axus.axuspixeldungeon.actors.buffs.Weakness;
import club.axus.axuspixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import club.axus.axuspixeldungeon.actors.mobs.DM100;
import club.axus.axuspixeldungeon.actors.mobs.Eye;
import club.axus.axuspixeldungeon.actors.mobs.Shaman;
import club.axus.axuspixeldungeon.actors.mobs.Warlock;
import club.axus.axuspixeldungeon.actors.mobs.YogFist;
import club.axus.axuspixeldungeon.items.armor.Armor;
import club.axus.axuspixeldungeon.items.wands.WandOfBlastWave;
import club.axus.axuspixeldungeon.items.wands.WandOfDisintegration;
import club.axus.axuspixeldungeon.items.wands.WandOfFireblast;
import club.axus.axuspixeldungeon.items.wands.WandOfFrost;
import club.axus.axuspixeldungeon.items.wands.WandOfLightning;
import club.axus.axuspixeldungeon.items.wands.WandOfLivingEarth;
import club.axus.axuspixeldungeon.items.wands.WandOfMagicMissile;
import club.axus.axuspixeldungeon.items.wands.WandOfPrismaticLight;
import club.axus.axuspixeldungeon.items.wands.WandOfTransfusion;
import club.axus.axuspixeldungeon.items.wands.WandOfWarding;
import club.axus.axuspixeldungeon.levels.traps.DisintegrationTrap;
import club.axus.axuspixeldungeon.levels.traps.GrimTrap;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( WarpBeacon.class );
		
		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see Hero.damage
		return damage;
	}
	
	public static int drRoll( int level ){
		return Random.NormalIntRange(level, 3 + Math.round(level*1.5f));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}