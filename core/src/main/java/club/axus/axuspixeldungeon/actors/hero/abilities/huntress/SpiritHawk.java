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

package club.axus.axuspixeldungeon.actors.hero.abilities.huntress;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.blobs.Electricity;
import club.axus.axuspixeldungeon.actors.blobs.Freezing;
import club.axus.axuspixeldungeon.actors.buffs.Bleeding;
import club.axus.axuspixeldungeon.actors.buffs.Blindness;
import club.axus.axuspixeldungeon.actors.buffs.BlobImmunity;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Corrosion;
import club.axus.axuspixeldungeon.actors.buffs.Corruption;
import club.axus.axuspixeldungeon.actors.buffs.Invisibility;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.actors.hero.abilities.ArmorAbility;
import club.axus.axuspixeldungeon.actors.mobs.npcs.DirectableAlly;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.effects.particles.ShaftParticle;
import club.axus.axuspixeldungeon.items.armor.ClassArmor;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfTeleportation;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.BatSprite;
import club.axus.axuspixeldungeon.sprites.MobSprite;
import club.axus.axuspixeldungeon.ui.HeroIcon;
import club.axus.axuspixeldungeon.utils.GLog;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpiritHawk extends ArmorAbility {

	@Override
	public String targetingPrompt() {
		if (getHawk() == null) {
			return super.targetingPrompt();
		} else {
			return Messages.get(this, "prompt");
		}
	}

	@Override
	public boolean useTargeting(){
		return false;
	}

	{
		baseChargeUse = 35f;
	}

	@Override
	public float chargeUse(Hero hero) {
		if (getHawk() == null) {
			return super.chargeUse(hero);
		} else {
			return 0;
		}
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		HawkAlly ally = getHawk();

		if (ally != null){
			if (target == null){
				return;
			} else {
				ally.directTocell(target);
			}
		} else {
			ArrayList<Integer> spawnPoints = new ArrayList<>();
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (!spawnPoints.isEmpty()){
				armor.charge -= chargeUse(hero);
				armor.updateQuickslot();

				ally = new HawkAlly();
				ally.pos = Random.element(spawnPoints);
				GameScene.add(ally);

				ScrollOfTeleportation.appear(ally, ally.pos);
				Dungeon.observe();

				Invisibility.dispel();
				hero.spendAndNext(Actor.TICK);

			} else {
				GLog.w(Messages.get(this, "no_space"));
			}
		}

	}

	@Override
	public int icon() {
		return HeroIcon.SPIRIT_HAWK;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.EAGLE_EYE, Talent.GO_FOR_THE_EYES, Talent.SWIFT_SPIRIT, Talent.HEROIC_ENERGY};
	}

	private static HawkAlly getHawk(){
		for (Char ch : Actor.chars()){
			if (ch instanceof HawkAlly){
				return (HawkAlly) ch;
			}
		}
		return null;
	}

	public static class HawkAlly extends DirectableAlly {

		{
			spriteClass = HawkSprite.class;

			HP = HT = 10;
			defenseSkill = 60;

			flying = true;
			viewDistance = (int)GameMath.gate(6, 6+Dungeon.hero.pointsInTalent(Talent.EAGLE_EYE), 8);
			baseSpeed = 2f + Dungeon.hero.pointsInTalent(Talent.SWIFT_SPIRIT)/2f;
			attacksAutomatically = false;

			immunities.addAll(new BlobImmunity().immunities());
			immunities.add(Corruption.class);
		}

		@Override
		public int attackSkill(Char target) {
			return 60;
		}

		private int dodgesUsed = 0;
		private float timeRemaining = 60f;

		@Override
		public int defenseSkill(Char enemy) {
			if (Dungeon.hero.hasTalent(Talent.SWIFT_SPIRIT) &&
					dodgesUsed < 1 + Dungeon.hero.pointsInTalent(Talent.SWIFT_SPIRIT)) {
				dodgesUsed++;
				return Char.INFINITE_EVASION;
			}
			return super.defenseSkill(enemy);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(5, 10);
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			damage = super.attackProc( enemy, damage );
			if (Dungeon.hero.hasTalent(Talent.GO_FOR_THE_EYES)) {
				Buff.prolong( enemy, Blindness.class, 2*Dungeon.hero.pointsInTalent(Talent.GO_FOR_THE_EYES) );
			}

			return damage;
		}

		@Override
		protected boolean act() {
			if (timeRemaining <= 0){
				die(null);
				return true;
			}
			viewDistance = (int)GameMath.gate(6, 6+Dungeon.hero.pointsInTalent(Talent.EAGLE_EYE), 8);
			baseSpeed = 2f + Dungeon.hero.pointsInTalent(Talent.SWIFT_SPIRIT)/2f;
			boolean result = super.act();
			Dungeon.level.updateFieldOfView( this, fieldOfView );
			GameScene.updateFog(pos, viewDistance+(int)Math.ceil(speed()));
			return result;
		}

		@Override
		protected void spend(float time) {
			super.spend(time);
			timeRemaining -= time;
		}

		@Override
		public void destroy() {
			super.destroy();
			Dungeon.observe();
			GameScene.updateFog();
		}

		@Override
		public void followHero() {
			GLog.i(Messages.get(this, "direct_follow"));
			super.followHero();
		}

		@Override
		public void targetChar(Char ch) {
			GLog.i(Messages.get(this, "direct_attack"));
			super.targetChar(ch);
		}

		@Override
		public String description() {
			String message = Messages.get(this, "desc", (int)timeRemaining);
			if (dodgesUsed < Dungeon.hero.pointsInTalent(Talent.SWIFT_SPIRIT)){
				message += "\n" + Messages.get(this, "desc_dodges", (Dungeon.hero.pointsInTalent(Talent.SWIFT_SPIRIT) - dodgesUsed));
			}
			return message;
		}

		private static final String DODGES_USED     = "dodges_used";
		private static final String TIME_REMAINING  = "time_remaining";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DODGES_USED, dodgesUsed);
			bundle.put(TIME_REMAINING, timeRemaining);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			dodgesUsed = bundle.getInt(DODGES_USED);
			timeRemaining = bundle.getFloat(TIME_REMAINING);
		}
	}

	public static class HawkSprite extends MobSprite {

		public HawkSprite() {
			super();

			texture( Assets.Sprites.SPIRIT_HAWK );

			TextureFilm frames = new TextureFilm( texture, 15, 15 );

			int c = 0;

			idle = new Animation( 6, true );
			idle.frames( frames, 0, 1 );

			run = new Animation( 8, true );
			run.frames( frames, 0, 1 );

			attack = new Animation( 12, false );
			attack.frames( frames, 2, 3, 0, 1 );

			die = new Animation( 12, false );
			die.frames( frames, 4, 5, 6 );

			play( idle );
		}

		@Override
		public int blood() {
			return 0xFF00FFFF;
		}

		@Override
		public void die() {
			super.die();
			emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
			emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
		}
	}
}
