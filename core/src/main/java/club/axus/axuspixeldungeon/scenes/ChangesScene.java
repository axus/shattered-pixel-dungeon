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

package club.axus.axuspixeldungeon.scenes;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Chrome;
import club.axus.axuspixeldungeon.axusPixelDungeon;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.ui.Archs;
import club.axus.axuspixeldungeon.ui.ExitButton;
import club.axus.axuspixeldungeon.ui.RenderedTextBlock;
import club.axus.axuspixeldungeon.ui.ScrollPane;
import club.axus.axuspixeldungeon.ui.StyledButton;
import club.axus.axuspixeldungeon.ui.Window;
import club.axus.axuspixeldungeon.ui.changelist.ChangeInfo;
import club.axus.axuspixeldungeon.ui.changelist.v0_1_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_2_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_3_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_4_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_5_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_6_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_7_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_8_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v0_9_X_Changes;
import club.axus.axuspixeldungeon.ui.changelist.v1_X_Changes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class ChangesScene extends PixelScene {
	
	public static int changesSelected = 0;
	
	@Override
	public void create() {
		super.create();

		Music.INSTANCE.playTracks(
				new String[]{Assets.Music.THEME_1, Assets.Music.THEME_2},
				new float[]{1, 1},
				false);

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(w - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 36;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.bottom() + 5;
		align( panel );
		add( panel );
		
		final ArrayList<ChangeInfo> changeInfos = new ArrayList<>();
		
		switch (changesSelected){
			case 0: default:
				v1_X_Changes.addAllChanges(changeInfos);
				break;
			case 1:
				v0_9_X_Changes.addAllChanges(changeInfos);
				break;
			case 2:
				v0_8_X_Changes.addAllChanges(changeInfos);
				break;
			case 3:
				v0_7_X_Changes.addAllChanges(changeInfos);
				break;
			case 4:
				v0_6_X_Changes.addAllChanges(changeInfos);
				break;
			case 5:
				v0_5_X_Changes.addAllChanges(changeInfos);
				v0_4_X_Changes.addAllChanges(changeInfos);
				v0_3_X_Changes.addAllChanges(changeInfos);
				v0_2_X_Changes.addAllChanges(changeInfos);
				v0_1_X_Changes.addAllChanges(changeInfos);
				break;
		}

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : changeInfos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );

		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second = false;
		for (ChangeInfo info : changeInfos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}

		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth() + 2,
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		StyledButton btn1_1 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "1.X"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 0) {
					changesSelected = 0;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 0) btn1_1.textColor( 0xBBBBBB );
		btn1_1.setRect(list.left()-4f, list.bottom(), 22, changesSelected == 0 ? 19 : 15);
		addToBack(btn1_1);

		StyledButton btn0_9 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "0.9"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 1) {
					changesSelected = 1;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 1) btn0_9.textColor( 0xBBBBBB );
		btn0_9.setRect(btn1_1.right()+1, list.bottom(), 22, changesSelected == 1 ? 19 : 15);
		addToBack(btn0_9);

		StyledButton btn0_8 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "0.8"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 2) {
					changesSelected = 2;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 2) btn0_8.textColor( 0xBBBBBB );
		btn0_8.setRect(btn0_9.right() + 1, list.bottom(), 22, changesSelected == 2 ? 19 : 15);
		addToBack(btn0_8);
		
		StyledButton btn0_7 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "0.7"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 3) {
					changesSelected = 3;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 3) btn0_7.textColor( 0xBBBBBB );
		btn0_7.setRect(btn0_8.right() + 1, btn0_8.top(), 22, changesSelected == 3 ? 19 : 15);
		addToBack(btn0_7);
		
		StyledButton btn0_6 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "0.6"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 4) {
					changesSelected = 4;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 4) btn0_6.textColor( 0xBBBBBB );
		btn0_6.setRect(btn0_7.right() + 1, btn0_8.top(), 22, changesSelected == 4 ? 19 : 15);
		addToBack(btn0_6);
		
		StyledButton btnOld = new StyledButton(Chrome.Type.GREY_BUTTON_TR,"0.5-1"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 5) {
					changesSelected = 5;
					axusPixelDungeon.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 5) btnOld.textColor( 0xBBBBBB );
		btnOld.setRect(btn0_6.right() + 1, btn0_8.top(), 26, changesSelected == 5 ? 19 : 15);
		addToBack(btnOld);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		axusPixelDungeon.switchNoFade(TitleScene.class);
	}

}
