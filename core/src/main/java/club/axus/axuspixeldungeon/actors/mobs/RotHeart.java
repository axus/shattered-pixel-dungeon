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

package club.axus.axuspixeldungeon.actors.mobs;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.blobs.Blob;
import club.axus.axuspixeldungeon.actors.blobs.ToxicGas;
import club.axus.axuspixeldungeon.actors.buffs.Amok;
import club.axus.axuspixeldungeon.actors.buffs.Burning;
import club.axus.axuspixeldungeon.actors.buffs.Paralysis;
import club.axus.axuspixeldungeon.actors.buffs.Sleep;
import club.axus.axuspixeldungeon.actors.buffs.Terror;
import club.axus.axuspixeldungeon.actors.buffs.Vertigo;
import club.axus.axuspixeldungeon.plants.Rotberry;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.RotHeartSprite;
import com.watabou.utils.Random;

public class RotHeart extends Mob {

	{
		spriteClass = RotHeartSprite.class;

		HP = HT = 80;
		defenseSkill = 0;

		EXP = 4;

		state = PASSIVE;

		properties.add(Property.IMMOVABLE);
		properties.add(Property.MINIBOSS);
	}

	@Override
	public void damage(int dmg, Object src) {
		//TODO: when effect properties are done, change this to FIRE
		if (src instanceof Burning) {
			destroy();
			sprite.die();
		} else {
			super.damage(dmg, src);
		}
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		GameScene.add(Blob.seed(pos, 20, ToxicGas.class));

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void beckon(int cell) {
		//do nothing
	}

	@Override
	protected boolean getCloser(int target) {
		return false;
	}

	@Override
	public void destroy() {
		super.destroy();
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[Dungeon.level.mobs.size()])){
			if (mob instanceof RotLasher){
				mob.die(null);
			}
		}
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Dungeon.level.drop( new Rotberry.Seed(), pos ).sprite.drop();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public int damageRoll() {
		return 0;
	}

	@Override
	public int attackSkill( Char target ) {
		return 0;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}
	
	{
		immunities.add( Paralysis.class );
		immunities.add( Amok.class );
		immunities.add( Sleep.class );
		immunities.add( ToxicGas.class );
		immunities.add( Terror.class );
		immunities.add( Vertigo.class );
	}

}
