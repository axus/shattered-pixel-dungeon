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

package club.axus.axuspixeldungeon.items.keys;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.journal.Notes;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.windows.WndJournal;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public abstract class Key extends Item {

	public static final float TIME_TO_UNLOCK = 1f;
	
	{
		stackable = true;
		unique = true;
	}
	
	public int depth;
	
	@Override
	public boolean isSimilar( Item item ) {
		return super.isSimilar(item) && ((Key)item).depth == depth;
	}

	@Override
	public boolean doPickUp(Hero hero) {
		GameScene.pickUpJournal(this, hero.pos);
		WndJournal.last_index = 2;
		Notes.add(this);
		Sample.INSTANCE.play( Assets.Sounds.ITEM );
		hero.spendAndNext( TIME_TO_PICK_UP );
		GameScene.updateKeyDisplay();
		return true;
	}

	private static final String DEPTH = "depth";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DEPTH, depth );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		depth = bundle.getInt( DEPTH );
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

}
