package club.axus.axuspixeldungeon.windows;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Chrome;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.buffs.LostInventory;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.items.EquipableItem;
import club.axus.axuspixeldungeon.items.Generator;
import club.axus.axuspixeldungeon.items.Item;
import club.axus.axuspixeldungeon.items.artifacts.Artifact;
import club.axus.axuspixeldungeon.items.artifacts.CloakOfShadows;
import club.axus.axuspixeldungeon.items.bags.Bag;
import club.axus.axuspixeldungeon.items.wands.Wand;
import club.axus.axuspixeldungeon.messages.Languages;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.scenes.PixelScene;
import club.axus.axuspixeldungeon.ui.ItemSlot;
import club.axus.axuspixeldungeon.ui.QuickSlotButton;
import club.axus.axuspixeldungeon.ui.RenderedTextBlock;
import club.axus.axuspixeldungeon.ui.Window;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WndQuickBag extends Window {

	private static Item bag;

	public WndQuickBag(Bag bag){
		super(0, 0, 0, Chrome.get(Chrome.Type.TOAST_TR));

		if( WndBag.INSTANCE != null ){
			WndBag.INSTANCE.hide();
		}
		WndBag.INSTANCE = this;

		WndQuickBag.bag = bag;

		float width = 0, height = 0;
		int maxWidth = PixelScene.landscape() ? 240 : 135;
		int left = 0;
		int top = 10;

		ArrayList<Item> items = new ArrayList<>();

		for (Item i : bag == null ? Dungeon.hero.belongings : bag){
			if (i.defaultAction == null){
				continue;
			}
			if (i instanceof Bag) {
				continue;
			}
			if (i instanceof Artifact
					&& !i.isEquipped(Dungeon.hero)
					&& (!(i instanceof CloakOfShadows) || !Dungeon.hero.hasTalent(Talent.LIGHT_CLOAK))){
				continue;
			}
			items.add(i);
		}

		Collections.sort(items, quickBagComparator);

		if (items.isEmpty()){
			hide();
			return;
		}

		int btnWidth = 16;
		int btnHeight = 20;

		//height of the toolbar and status pane, plus a little extra
		int targetHeight = PixelScene.uiCamera.height - 100;
		int rows = (int)Math.ceil(items.size() / (float)((maxWidth+1) / (btnWidth+1)));
		int expectedHeight = rows * btnHeight + (rows-1);
		while (expectedHeight > targetHeight && btnHeight > 16){
			btnHeight--;
			expectedHeight -= rows;
		}

		for (Item i : items){
			QuickItemButton btn = new QuickItemButton(i);
			btn.setRect(left, top, btnWidth, btnHeight);
			add(btn);

			if (width < btn.right()) width = btn.right();
			if (height < btn.bottom()) height = btn.bottom();

			left += btnWidth+1;

			if (left + btnWidth > maxWidth){
				left = 0;
				top += btnHeight+1;
			}
		}

		//TODO translate this!
		RenderedTextBlock txtTitle;
		if ( Messages.lang() == Languages.ENGLISH){
			txtTitle = PixelScene.renderTextBlock( "Quick-use an Item", 8 );
		} else {
			txtTitle = PixelScene.renderTextBlock( Messages.titleCase(bag != null ? bag.name() : Dungeon.hero.belongings.backpack.name()), 8 );
		}
		txtTitle.hardlight( TITLE_COLOR );
		if (txtTitle.width() > width) width = txtTitle.width();

		txtTitle.setPos(
				(width - txtTitle.width())/2f,
				(10 - txtTitle.height()) / 2f - 1);
		PixelScene.align(txtTitle);
		add( txtTitle );

		resize((int)width, (int)height);

		int bottom = GameScene.uiCamera.height;

		//offset to be above the toolbar
		offset((int) (bottom/2 - 30 - height/2));

	}

	public static final Comparator<Item> quickBagComparator = new Comparator<Item>() {
		@Override
		public int compare( Item lhs, Item rhs ) {
			if (lhs.isEquipped(Dungeon.hero) && !rhs.isEquipped(Dungeon.hero)){
				return -1;
			} else if (!lhs.isEquipped(Dungeon.hero) && rhs.isEquipped(Dungeon.hero)){
				return 1;
			} else {
				return Generator.Category.order(lhs) - Generator.Category.order(rhs);
			}
		}
	};

	@Override
	public void hide() {
		super.hide();
		if (WndBag.INSTANCE == this){
			WndBag.INSTANCE = null;
		}
	}

	private class QuickItemButton extends ItemSlot {

		private static final int NORMAL = 0x9953564D;
		private static final int EQUIPPED	= 0x9991938C;
		private Item item;
		private ColorBlock bg;

		public QuickItemButton(Item item) {

			super(item);
			showExtraInfo(false);

			this.item = item;

		}

		@Override
		protected void createChildren() {
			bg = new ColorBlock(1, 1, NORMAL);
			add(bg);

			super.createChildren();
		}

		@Override
		protected void layout() {
			bg.size(width, height);
			bg.x = x;
			bg.y = y;

			super.layout();
		}

		@Override
		public void item(Item item) {

			super.item(item);
			if (item != null) {

				bg.texture( TextureCache.createSolid( item.isEquipped( Dungeon.hero ) ? EQUIPPED : NORMAL ) );
				if (item.cursed && item.cursedKnown) {
					bg.ra = +0.3f;
					bg.ga = -0.15f;
				} else if (!item.isIdentified()) {
					if ((item instanceof EquipableItem || item instanceof Wand) && item.cursedKnown) {
						bg.ba = 0.3f;
					} else {
						bg.ra = 0.3f;
						bg.ba = 0.3f;
					}
				}

				if (Dungeon.hero.buff(LostInventory.class) != null
						&& !item.keptThoughLostInvent){
					enable(false);
				}

			} else {
				bg.color(NORMAL);
			}
		}

		@Override
		protected void onPointerDown() {
			bg.brightness(1.5f);
			Sample.INSTANCE.play(Assets.Sounds.CLICK, 0.7f, 0.7f, 1.2f);
		}

		protected void onPointerUp() {
			bg.brightness(1.0f);
		}

		@Override
		protected void onClick() {
			if (Dungeon.hero == null || !Dungeon.hero.isAlive() || !Dungeon.hero.belongings.contains(item)){
				Game.scene().addToFront(new WndUseItem(WndQuickBag.this, item));
				return;
			}

			hide();
			item.execute(Dungeon.hero);
			if (item.usesTargeting && bag != null){
				int idx = Dungeon.quickslot.getSlot(WndQuickBag.bag);
				if (idx != -1){
					QuickSlotButton.useTargeting(idx);
				}
			}
		}

		@Override
		protected boolean onLongClick() {
			Game.scene().addToFront(new WndUseItem(WndQuickBag.this, item));
			return true;
		}

	}

}
