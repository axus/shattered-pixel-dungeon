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

package club.axus.axuspixeldungeon.ui;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.scenes.PixelScene;
import club.axus.axuspixeldungeon.windows.WndInfoBuff;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BuffIndicator extends Component {
	
	//transparent icon
	public static final int NONE    = 63;

	//FIXME this is becoming a mess, should do a big cleaning pass on all of these
	//and think about tinting options
	public static final int MIND_VISION = 0;
	public static final int LEVITATION  = 1;
	public static final int FIRE        = 2;
	public static final int POISON      = 3;
	public static final int PARALYSIS   = 4;
	public static final int HUNGER      = 5;
	public static final int STARVATION  = 6;
	public static final int TIME        = 7;
	public static final int OOZE        = 8;
	public static final int AMOK        = 9;
	public static final int TERROR      = 10;
	public static final int ROOTS       = 11;
	public static final int INVISIBLE   = 12;
	public static final int SHADOWS     = 13;
	public static final int WEAKNESS    = 14;
	public static final int FROST       = 15;
	public static final int BLINDNESS   = 16;
	public static final int COMBO       = 17;
	public static final int FURY        = 18;
	public static final int HERB_HEALING= 19;
	public static final int ARMOR       = 20;
	public static final int HEART       = 21;
	public static final int LIGHT       = 22;
	public static final int CRIPPLE     = 23;
	public static final int BARKSKIN    = 24;
	public static final int IMMUNITY    = 25;
	public static final int BLEEDING    = 26;
	public static final int MARK        = 27;
	public static final int DEFERRED    = 28;
	public static final int DROWSY      = 29;
	public static final int MAGIC_SLEEP = 30;
	public static final int THORNS      = 31;
	public static final int FORESIGHT   = 32;
	public static final int VERTIGO     = 33;
	public static final int RECHARGING  = 34;
	public static final int LOCKED_FLOOR= 35;
	public static final int CORRUPT     = 36;
	public static final int BLESS       = 37;
	public static final int RAGE        = 38;
	public static final int SACRIFICE   = 39;
	public static final int BERSERK     = 40;
	public static final int HASTE       = 41;
	public static final int PREPARATION = 42;
	public static final int WELL_FED    = 43;
	public static final int HEALING     = 44;
	public static final int WEAPON      = 45;
	public static final int VULNERABLE  = 46;
	public static final int HEX         = 47;
	public static final int DEGRADE     = 48;
	public static final int PINCUSHION  = 49;
	public static final int UPGRADE     = 50;
	public static final int MOMENTUM    = 51;
	public static final int ANKH        = 52;
	public static final int NOINV       = 53;

	public static final int SIZE    = 7;
	
	private static BuffIndicator heroInstance;
	
	private LinkedHashMap<Buff, BuffButton> buffButtons = new LinkedHashMap<>();
	private boolean needsRefresh;
	private Char ch;
	
	public BuffIndicator( Char ch ) {
		super();
		
		this.ch = ch;
		if (ch == Dungeon.hero) {
			heroInstance = this;
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		if (this == heroInstance) {
			heroInstance = null;
		}
	}

	@Override
	public synchronized void update() {
		super.update();
		if (needsRefresh){
			needsRefresh = false;
			layout();
		}
	}
	
	@Override
	protected void layout() {
		
		ArrayList<Buff> newBuffs = new ArrayList<>();
		for (Buff buff : ch.buffs()) {
			if (buff.icon() != NONE) {
				newBuffs.add(buff);
			}
		}
		
		//remove any icons no longer present
		for (Buff buff : buffButtons.keySet().toArray(new Buff[0])){
			if (!newBuffs.contains(buff)){
				Image icon = buffButtons.get( buff ).icon;
				icon.origin.set( SIZE / 2f );
				icon.alpha(0.6f);
				add( icon );
				add( new AlphaTweener( icon, 0, 0.6f ) {
					@Override
					protected void updateValues( float progress ) {
						super.updateValues( progress );
						image.scale.set( 1 + 5 * progress );
					}
					
					@Override
					protected void onComplete() {
						image.killAndErase();
					}
				} );
				
				buffButtons.get( buff ).destroy();
				remove(buffButtons.get( buff ));
				buffButtons.remove( buff );
			}
		}
		
		//add new icons
		for (Buff buff : newBuffs) {
			if (!buffButtons.containsKey(buff)) {
				BuffButton icon = new BuffButton(buff);
				add(icon);
				buffButtons.put( buff, icon );
			}
		}
		
		//layout
		int pos = 0;
		for (BuffButton icon : buffButtons.values()){
			icon.updateIcon();
			icon.setRect(x + pos * (SIZE + 2), y, 9, 12);
			PixelScene.align(icon);
			pos++;
		}
	}

	private static class BuffButton extends IconButton {

		private Buff buff;

		//Todo maybe move into buff icon?
		public Image grey;

		public BuffButton(Buff buff ){
			super( new BuffIcon(buff, false));
			this.buff = buff;

			bringToFront(grey);
		}

		@Override
		protected void createChildren() {
			super.createChildren();
			grey = new Image( TextureCache.createSolid(0xCC666666));
			add( grey );
		}

		public void updateIcon(){
			((BuffIcon)icon).refresh(buff);
			//round up to the nearest pixel if <50% faded, otherwise round down
			float fadeHeight = buff.iconFadePercent() * icon.height();
			float zoom = (camera() != null) ? camera().zoom : 1;
			if (fadeHeight < icon.height()/2f){
				grey.scale.set( icon.width(), (float)Math.ceil(zoom*fadeHeight)/zoom);
			} else {
				grey.scale.set( icon.width(), (float)Math.floor(zoom*fadeHeight)/zoom);
			}
		}

		@Override
		protected void layout() {
			super.layout();
			grey.x = icon.x = this.x + 1;
			grey.y = icon.y = this.y + 2;
		}

		@Override
		protected void onClick() {
			if (buff.icon() != NONE) GameScene.show(new WndInfoBuff(buff));
		}

		@Override
		protected void onPointerDown() {
			//don't affect buff color
			Sample.INSTANCE.play( Assets.Sounds.CLICK );
		}

		@Override
		protected void onPointerUp() {
			//don't affect buff color
		}
	}
	
	public static void refreshHero() {
		if (heroInstance != null) {
			heroInstance.needsRefresh = true;
		}
	}
}
