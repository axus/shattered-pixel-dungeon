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

package club.axus.axuspixeldungeon.actors.hero.abilities.warrior;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.FlavourBuff;
import club.axus.axuspixeldungeon.actors.buffs.Invisibility;
import club.axus.axuspixeldungeon.actors.buffs.Vulnerable;
import club.axus.axuspixeldungeon.actors.buffs.Weakness;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.actors.hero.abilities.ArmorAbility;
import club.axus.axuspixeldungeon.items.armor.ClassArmor;
import club.axus.axuspixeldungeon.items.wands.WandOfBlastWave;
import club.axus.axuspixeldungeon.mechanics.Ballistica;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class HeroicLeap extends ArmorAbility {

	{
		baseChargeUse = 25f;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	public float chargeUse( Hero hero ) {
		float chargeUse = super.chargeUse(hero);
		if (hero.buff(DoubleJumpTracker.class) != null){
			//reduced charge use by 20%/36%/50%/60%
			chargeUse *= Math.pow(0.795, hero.pointsInTalent(Talent.DOUBLE_JUMP));
		}
		return chargeUse;
	}

	@Override
	public void activate( ClassArmor armor, Hero hero, Integer target ) {
		if (target != null) {

			Ballistica route = new Ballistica(hero.pos, target, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
			int cell = route.collisionPos;

			//can't occupy the same cell as another char, so move back one.
			int backTrace = route.dist-1;
			while (Actor.findChar( cell ) != null && cell != hero.pos) {
				cell = route.path.get(backTrace);
				backTrace--;
			}

			armor.charge -= chargeUse( hero );
			armor.updateQuickslot();

			final int dest = cell;
			hero.busy();
			hero.sprite.jump(hero.pos, cell, new Callback() {
				@Override
				public void call() {
					hero.move(dest);
					Dungeon.level.occupyCell(hero);
					Dungeon.observe();
					GameScene.updateFog();

					for (int i : PathFinder.NEIGHBOURS8) {
						Char mob = Actor.findChar(hero.pos + i);
						if (mob != null && mob != hero && mob.alignment != Char.Alignment.ALLY) {
							if (hero.hasTalent(Talent.BODY_SLAM)){
								int damage = hero.drRoll();
								damage = Math.round(damage*0.25f*hero.pointsInTalent(Talent.BODY_SLAM));
								mob.damage(damage, hero);
							}
							if (mob.pos == hero.pos + i && hero.hasTalent(Talent.IMPACT_WAVE)){
								Ballistica trajectory = new Ballistica(mob.pos, mob.pos + i, Ballistica.MAGIC_BOLT);
								int strength = 1+hero.pointsInTalent(Talent.IMPACT_WAVE);
								WandOfBlastWave.throwChar(mob, trajectory, strength, true);
								if (Random.Int(4) < hero.pointsInTalent(Talent.IMPACT_WAVE)){
									Buff.prolong(mob, Vulnerable.class, 3f);
								}
							}
						}
					}

					WandOfBlastWave.BlastWave.blast(dest);
					Camera.main.shake(2, 0.5f);

					Invisibility.dispel();
					hero.spendAndNext(Actor.TICK);

					if (hero.buff(DoubleJumpTracker.class) != null){
						hero.buff(DoubleJumpTracker.class).detach();
					} else {
						if (hero.hasTalent(Talent.DOUBLE_JUMP)) {
							Buff.affect(hero, DoubleJumpTracker.class, 5);
						}
					}
				}
			});
		}
	}

	public static class DoubleJumpTracker extends FlavourBuff{};

	@Override
	public int icon() {
		return HeroIcon.HEROIC_LEAP;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.BODY_SLAM, Talent.IMPACT_WAVE, Talent.DOUBLE_JUMP, Talent.HEROIC_ENERGY};
	}
}
