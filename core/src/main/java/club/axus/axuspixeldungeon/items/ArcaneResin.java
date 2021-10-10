package club.axus.axuspixeldungeon.items;

import club.axus.axuspixeldungeon.Assets;
import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.hero.Hero;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.items.bags.Bag;
import club.axus.axuspixeldungeon.items.bags.MagicalHolster;
import club.axus.axuspixeldungeon.items.wands.Wand;
import club.axus.axuspixeldungeon.items.weapon.melee.MagesStaff;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.scenes.GameScene;
import club.axus.axuspixeldungeon.sprites.ItemSpriteSheet;
import club.axus.axuspixeldungeon.utils.GLog;
import club.axus.axuspixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class ArcaneResin extends Item {

	{
		image = ItemSpriteSheet.ARCANE_RESIN;

		stackable = true;

		defaultAction = AC_APPLY;

		bones = true;
	}

	private static final String AC_APPLY = "APPLY";

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_APPLY );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_APPLY)) {

			curUser = hero;
			GameScene.selectItem( itemSelector );

		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int value() {
		return 30*quantity();
	}

	private final WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			//FIXME give this its own prompt string
			return Messages.get(MagesStaff.class, "prompt");
		}

		@Override
		public Class<?extends Bag> preferredBag(){
			return MagicalHolster.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return item instanceof Wand && item.isIdentified();
		}

		@Override
		public void onSelect( Item item ) {
			if (item != null && item instanceof Wand) {
				Wand w = (Wand)item;

				if (w.level() >= 3){
					GLog.w(Messages.get(ArcaneResin.class, "level_too_high"));
					return;
				}

				int resinToUse = w.level()+1;

				if (quantity() < resinToUse){
					GLog.w(Messages.get(ArcaneResin.class, "not_enough"));

				} else {

					if (resinToUse < quantity()){
						quantity(quantity()-resinToUse);
					} else {
						detachAll(Dungeon.hero.belongings.backpack);
					}

					w.resinBonus++;
					w.curCharges++;
					w.updateLevel();
					Item.updateQuickslot();

					curUser.sprite.operate(curUser.pos);
					Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
					curUser.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );

					curUser.spendAndNext(Actor.TICK);
					GLog.p(Messages.get(ArcaneResin.class, "apply"));
				}
			}
		}
	};

	public static class Recipe extends club.axus.axuspixeldungeon.items.Recipe {

		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			return ingredients.size() == 1 && ingredients.get(0) instanceof Wand && ingredients.get(0).isIdentified();
		}

		@Override
		public int cost(ArrayList<Item> ingredients) {
			return 5;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			Item result = sampleOutput(ingredients);

			ingredients.get(0).quantity(0);

			return result;
		}

		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			Wand w = (Wand)ingredients.get(0);
			int level = w.level() - w.resinBonus;
			return new ArcaneResin().quantity(2*(level+1));
		}
	}

}
