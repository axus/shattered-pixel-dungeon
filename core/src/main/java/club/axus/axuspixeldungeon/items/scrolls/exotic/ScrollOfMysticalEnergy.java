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

package club.axus.axuspixeldungeon.items.scrolls.exotic;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.actors.buffs.ArtifactRecharge;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.effects.SpellSprite;
import club.axus.axuspixeldungeon.items.scrolls.ScrollOfRecharging;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class ScrollOfMysticalEnergy extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_MYSTENRG;
	}
	
	@Override
	public void doRead() {
		
		//append buff
		Buff.affect(curUser, ArtifactRecharge.class).set( 30 ).ignoreHornOfPlenty = false;

		Sample.INSTANCE.play( Assets.Sounds.READ );
		Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
		
		SpellSprite.show( curUser, SpellSprite.CHARGE );
		identify();
		ScrollOfRecharging.charge(curUser);
		
		readAnimation();
	}
	
}
