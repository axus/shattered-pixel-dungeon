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
 
 /* Axus Pixel Dungeon
  *    2021 - Shattered is perfect, it is hard to think of changes
  */

package club.axus.axuspixeldungeon;

import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.scenes.PixelScene;
import club.axus.axuspixeldungeon.scenes.TitleScene;
import club.axus.axuspixeldungeon.scenes.WelcomeScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PlatformSupport;

public class axusPixelDungeon extends Game {

	//variable constants for specific older versions of shattered, used for data conversion
	//versions older than v0.8.0b are no longer supported, and data from them is ignored
	public static final int v0_8_0b = 414;
	public static final int v0_8_1a = 422;
	public static final int v0_8_2d = 463;

	public static final int v0_9_0b  = 489;
	public static final int v0_9_1d  = 511;
	public static final int v0_9_2b  = 531;
	public static final int v0_9_3c  = 557; //557 on iOS, 554 on other platforms

	public static final int v1_0_0   = 565;
	
	public axusPixelDungeon(PlatformSupport platform ) {
		super( sceneClass == null ? WelcomeScene.class : sceneClass, platform );

		//v1.0.0
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.items.stones.StoneOfFear.class,
				"club.axus.axuspixeldungeon.items.stones.StoneOfAffection" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.items.stones.StoneOfDeepSleep.class,
				"club.axus.axuspixeldungeon.items.stones.StoneOfDeepenedSleep" );

		//v0.9.3
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.actors.mobs.Tengu.class,
				"club.axus.axuspixeldungeon.actors.mobs.NewTengu" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.PrisonBossLevel.class,
				"club.axus.axuspixeldungeon.levels.NewPrisonBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.PrisonBossLevel.ExitVisual.class,
				"club.axus.axuspixeldungeon.levels.NewPrisonBossLevel$exitVisual" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.PrisonBossLevel.ExitVisualWalls.class,
				"club.axus.axuspixeldungeon.levels.NewPrisonBossLevel$exitVisualWalls" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.actors.mobs.DM300.class,
				"club.axus.axuspixeldungeon.actors.mobs.NewDM300" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CavesBossLevel.class,
				"club.axus.axuspixeldungeon.levels.NewCavesBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CavesBossLevel.PylonEnergy.class,
				"club.axus.axuspixeldungeon.levels.NewCavesBossLevel$PylonEnergy" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CavesBossLevel.ArenaVisuals.class,
				"club.axus.axuspixeldungeon.levels.NewCavesBossLevel$ArenaVisuals" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CavesBossLevel.CityEntrance.class,
				"club.axus.axuspixeldungeon.levels.NewCavesBossLevel$CityEntrance" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CavesBossLevel.EntranceOverhang.class,
				"club.axus.axuspixeldungeon.levels.NewCavesBossLevel$EntranceOverhang" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CityBossLevel.class,
				"club.axus.axuspixeldungeon.levels.NewCityBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CityBossLevel.CustomGroundVisuals.class,
				"club.axus.axuspixeldungeon.levels.NewCityBossLevel$CustomGroundVisuals" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.CityBossLevel.CustomWallVisuals.class,
				"club.axus.axuspixeldungeon.levels.NewCityBossLevel$CustomWallVisuals" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.HallsBossLevel.class,
				"club.axus.axuspixeldungeon.levels.NewHallsBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.HallsBossLevel.CenterPieceVisuals.class,
				"club.axus.axuspixeldungeon.levels.NewHallsBossLevel$CenterPieceWalls" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.levels.HallsBossLevel.CenterPieceWalls.class,
				"club.axus.axuspixeldungeon.levels.NewHallsBossLevel$CenterPieceWalls" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.items.Waterskin.class,
				"club.axus.axuspixeldungeon.items.DewVial" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.items.TengusMask.class,
				"club.axus.axuspixeldungeon.items.TomeOfMastery" );
		com.watabou.utils.Bundle.addAlias(
				club.axus.axuspixeldungeon.items.KingsCrown.class,
				"club.axus.axuspixeldungeon.items.ArmorKit" );
		
	}
	
	@Override
	public void create() {
		super.create();

		updateSystemUI();
		SPDAction.loadBindings();
		
		Music.INSTANCE.enable( SPDSettings.music() );
		Music.INSTANCE.volume( SPDSettings.musicVol()*SPDSettings.musicVol()/100f );
		Sample.INSTANCE.enable( SPDSettings.soundFx() );
		Sample.INSTANCE.volume( SPDSettings.SFXVol()*SPDSettings.SFXVol()/100f );

		Sample.INSTANCE.load( Assets.Sounds.all );
		
	}

	@Override
	public void finish() {
		if (!DeviceCompat.isiOS()) {
			super.finish();
		} else {
			//can't exit on iOS (Apple guidelines), so just go to title screen
			switchScene(TitleScene.class);
		}
	}

	public static void switchNoFade(Class<? extends PixelScene> c){
		switchNoFade(c, null);
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}
	
	public static void seamlessResetScene(SceneChangeCallback callback) {
		if (scene() instanceof PixelScene){
			((PixelScene) scene()).saveWindows();
			switchNoFade((Class<? extends PixelScene>) sceneClass, callback );
		} else {
			resetScene();
		}
	}
	
	public static void seamlessResetScene(){
		seamlessResetScene(null);
	}
	
	@Override
	protected void switchScene() {
		super.switchScene();
		if (scene instanceof PixelScene){
			((PixelScene) scene).restoreWindows();
		}
	}
	
	@Override
	public void resize( int width, int height ) {
		if (width == 0 || height == 0){
			return;
		}

		if (scene instanceof PixelScene &&
				(height != Game.height || width != Game.width)) {
			PixelScene.noFade = true;
			((PixelScene) scene).saveWindows();
		}

		super.resize( width, height );

		updateDisplaySize();

	}
	
	@Override
	public void destroy(){
		super.destroy();
		GameScene.endActorThread();
	}
	
	public void updateDisplaySize(){
		platform.updateDisplaySize();
	}

	public static void updateSystemUI() {
		platform.updateSystemUI();
	}
}