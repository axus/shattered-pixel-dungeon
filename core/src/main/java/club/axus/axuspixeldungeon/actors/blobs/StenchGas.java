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

package club.axus.axuspixeldungeon.actors.blobs;

import club.axus.axuspixeldungeon.Dungeon;
import club.axus.axuspixeldungeon.actors.Actor;
import club.axus.axuspixeldungeon.actors.Char;
import club.axus.axuspixeldungeon.actors.buffs.Buff;
import club.axus.axuspixeldungeon.actors.buffs.Paralysis;
import club.axus.axuspixeldungeon.effects.BlobEmitter;
import club.axus.axuspixeldungeon.effects.Speck;
import club.axus.axuspixeldungeon.messages.Messages;

public class StenchGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		int cell;

		for (int i = area.left; i < area.right; i++){
			for (int j = area.top; j < area.bottom; j++){
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0 && (ch = Actor.findChar( cell )) != null) {
					if (!ch.isImmune(this.getClass()))
						Buff.prolong( ch, Paralysis.class, Paralysis.DURATION/5 );
				}
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );

		emitter.pour( Speck.factory(Speck.STENCH), 0.4f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
