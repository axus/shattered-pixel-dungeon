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

package club.axus.axuspixeldungeon.items.scrolls;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.buffs.Degrade;
import club.axus.axuspixeldungeon.actors.hero.Belongings;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.effects.Flare;
import club.axus.axuspixeldungeon.effects.particles.ShadowParticle;
import club.axus.axuspixeldungeon.items.EquipableItem;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.armor.Armor;
import club.axus.axuspixeldungeon.items.wands.Wand;
import club.axus.axuspixeldungeon.items.weapon.Weapon;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.utils.GLog;

public class ScrollOfRemoveCurse extends InventoryScroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_REMCURSE;
		preferredBag = Belongings.Backpack.class;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return uncursable(item);
	}

	public static boolean uncursable( Item item ){
		if (item.isEquipped(Dungeon.hero) && Dungeon.hero.buff(Degrade.class) != null) {
			return true;
		} if ((item instanceof EquipableItem || item instanceof Wand) && ((!item.isIdentified() && !item.cursedKnown) || item.cursed)){
			return true;
		} else if (item instanceof Weapon){
			return ((Weapon)item).hasCurseEnchant();
		} else if (item instanceof Armor){
			return ((Armor)item).hasCurseGlyph();
		} else {
			return false;
		}
	}

	@Override
	protected void onItemSelected(Item item) {
		new Flare( 6, 32 ).show( curUser.sprite, 2f ) ;

		boolean procced = uncurse( curUser, item );

		Degrade.detach( curUser, Degrade.class );

		if (procced) {
			GLog.p( Messages.get(this, "cleansed") );
		} else {
			GLog.i( Messages.get(this, "not_cleansed") );
		}
	}

	public static boolean uncurse( Hero hero, Item... items ) {
		
		boolean procced = false;
		for (Item item : items) {
			if (item != null) {
				item.cursedKnown = true;
				if (item.cursed) {
					procced = true;
					item.cursed = false;
				}
			}
			if (item instanceof Weapon){
				Weapon w = (Weapon) item;
				if (w.hasCurseEnchant()){
					w.enchant(null);
					procced = true;
				}
			}
			if (item instanceof Armor){
				Armor a = (Armor) item;
				if (a.hasCurseGlyph()){
					a.inscribe(null);
					procced = true;
				}
			}
			if (item instanceof Wand){
				((Wand) item).updateLevel();
			}
		}
		
		if (procced && hero != null) {
			hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
			hero.updateHT( false ); //for ring of might
			updateQuickslot();
		}
		
		return procced;
	}
	
	@Override
	public int value() {
		return isKnown() ? 30 * quantity : super.value();
	}
}
