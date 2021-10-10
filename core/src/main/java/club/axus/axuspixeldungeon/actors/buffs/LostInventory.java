package club.axus.axuspixeldungeon.actors.buffs;

import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.ui.BuffIndicator;

public class LostInventory extends Buff {

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.NOINV;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}

}
