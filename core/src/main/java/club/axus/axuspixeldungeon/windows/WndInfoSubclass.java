package club.axus.axuspixeldungeon.windows;

import club.axus.axuspixeldungeon.actors.hero.HeroClass;
import club.axus.axuspixeldungeon.actors.hero.HeroSubClass;
import club.axus.axuspixeldungeon.actors.hero.Talent;
import club.axus.axuspixeldungeon.messages.Messages;
import club.axus.axuspixeldungeon.ui.HeroIcon;
import club.axus.axuspixeldungeon.ui.TalentsPane;
import club.axus.axuspixeldungeon.windows.WndHeroInfo;
import club.axus.axuspixeldungeon.windows.WndTitledMessage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WndInfoSubclass extends WndTitledMessage {

	public WndInfoSubclass(HeroClass cls, HeroSubClass subCls){
		super( new HeroIcon(subCls), Messages.titleCase(subCls.title()), subCls.desc());

		ArrayList<LinkedHashMap<Talent, Integer>> talentList = new ArrayList<>();
		Talent.initClassTalents(cls, talentList);
		Talent.initSubclassTalents(subCls, talentList);

		TalentsPane.TalentTierPane talentPane = new TalentsPane.TalentTierPane(talentList.get(2), 3, false);
		talentPane.title.text( Messages.titleCase(Messages.get(WndHeroInfo.class, "talents")));
		talentPane.setRect(0, height + 5, width, talentPane.height());
		add(talentPane);
		resize(width, (int) talentPane.bottom());

	}

}
