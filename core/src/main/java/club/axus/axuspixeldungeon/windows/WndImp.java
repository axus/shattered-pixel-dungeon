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

package club.axus.axuspixeldungeon.windows;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.mobs.npcs.Imp;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.quest.DwarfToken;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.PixelScene;
import club.axus.axuspixeldungeon.sprites.ItemSprite;
import club.axus.axuspixeldungeon.ui.RedButton;
import club.axus.axuspixeldungeon.ui.RenderedTextBlock;
import club.axus.axuspixeldungeon.ui.Window;
import club.axus.axuspixeldungeon.utils.GLog;

public class WndImp extends Window {
	
	private static final int WIDTH      = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP        = 2;

	public WndImp( final Imp imp, final DwarfToken tokens ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( tokens.image(), null ) );
		titlebar.label( Messages.titleCase( tokens.name() ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		RenderedTextBlock message = PixelScene.renderTextBlock( Messages.get(this, "message"), 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );
		
		RedButton btnReward = new RedButton( Messages.get(this, "reward") ) {
			@Override
			protected void onClick() {
				takeReward( imp, tokens, Imp.Quest.reward );
			}
		};
		btnReward.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnReward );
		
		resize( WIDTH, (int)btnReward.bottom() );
	}
	
	private void takeReward( Imp imp, DwarfToken tokens, Item reward ) {
		
		hide();
		
		tokens.detachAll( Dungeon.hero.belongings.backpack );
		if (reward == null) return;

		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
		} else {
			Dungeon.level.drop( reward, imp.pos ).sprite.drop();
		}
		
		imp.flee();
		
		Imp.Quest.complete();
	}
}
