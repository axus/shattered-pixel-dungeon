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

package club.axus.axuspixeldungeon.items.wands;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Challenges;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Blindness;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Cripple;
import club.axus.axuspixeldungeon.actors.buffs.Light;
import club.axus.axuspixeldungeon.effects.Beam;
import club.axus.axuspixeldungeon.effects.CellEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.effects.particles.RainbowParticle;
import club.axus.axuspixeldungeon.effects.particles.ShadowParticle;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfMagicMapping;
import club.axus.axuspixeldungeon.items.weapon.melee.MagesStaff;
import club.axus.axuspixeldungeon.levels.Terrain;
import club.axus.axuspixeldungeon.mechanics.Ballistica;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfPrismaticLight extends DamageWand {

	{
		image = ItemSpriteSheet.WAND_PRISMATIC_LIGHT;

		collisionProperties = Ballistica.MAGIC_BOLT;
	}

	public int min(int lvl){
		return 1+lvl;
	}

	public int max(int lvl){
		return 5+3*lvl;
	}

	@Override
	public void onZap(Ballistica beam) {
		affectMap(beam);
		
		if (Dungeon.level.viewDistance < 6 ){
			if (Dungeon.isChallenged(Challenges.DARKNESS)){
				Buff.prolong( curUser, Light.class, 2f + buffedLvl());
			} else {
				Buff.prolong( curUser, Light.class, 10f+buffedLvl()*5);
			}
		}
		
		Char ch = Actor.findChar(beam.collisionPos);
		if (ch != null){
			wandProc(ch, chargesPerCast());
			affectTarget(ch);
		}
	}

	private void affectTarget(Char ch){
		int dmg = damageRoll();

		//three in (5+lvl) chance of failing
		if (Random.Int(5+buffedLvl()) >= 3) {
			Buff.prolong(ch, Blindness.class, 2f + (buffedLvl() * 0.333f));
			ch.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6 );
		}

		if (ch.properties().contains(Char.Property.DEMONIC) || ch.properties().contains(Char.Property.UNDEAD)){
			ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+buffedLvl() );
			Sample.INSTANCE.play(Assets.Sounds.BURNING);

			ch.damage(Math.round(dmg*1.333f), this);
		} else {
			ch.sprite.centerEmitter().burst( RainbowParticle.BURST, 10+buffedLvl() );

			ch.damage(dmg, this);
		}

	}

	private void affectMap(Ballistica beam){
		boolean noticed = false;
		for (int c : beam.subPath(0, beam.dist)){
			if (!Dungeon.level.insideMap(c)){
				continue;
			}
			for (int n : PathFinder.NEIGHBOURS9){
				int cell = c+n;

				if (Dungeon.level.discoverable[cell])
					Dungeon.level.mapped[cell] = true;

				int terr = Dungeon.level.map[cell];
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

					Dungeon.level.discover( cell );

					GameScene.discoverTile( cell, terr );
					ScrollOfMagicMapping.discover(cell);

					noticed = true;
				}
			}

			CellEmitter.center(c).burst( RainbowParticle.BURST, Random.IntRange( 1, 2 ) );
		}
		if (noticed)
			Sample.INSTANCE.play( Assets.Sounds.SECRET );

		GameScene.updateFog();
	}

	@Override
	public void fx(Ballistica beam, Callback callback) {
		curUser.sprite.parent.add(
				new Beam.LightRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)));
		callback.call();
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		//cripples enemy
		Buff.prolong( defender, Cripple.class, 1f+staff.buffedLvl());
	}

	@Override
	public void staffFx(MagesStaff.StaffParticle particle) {
		particle.color( Random.Int( 0x1000000 ) );
		particle.am = 0.5f;
		particle.setLifespan(1f);
		particle.speed.polar(Random.Float(PointF.PI2), 2f);
		particle.setSize( 1f, 2f);
		particle.radiateXY( 0.5f);
	}

}
