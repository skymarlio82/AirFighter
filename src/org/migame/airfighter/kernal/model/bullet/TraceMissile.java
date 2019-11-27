
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.game.Sprite;

import org.migame.airfighter.kernal.core.AirFighterEngine;
import org.migame.airfighter.kernal.model.map.GameBackgroundMap;

public class TraceMissile {

	// the amount of smog's frame
	public final static int NUM_OF_MISSILE_SMOG = 11;

	// the coordinate of missile
	public int[] xyz = null;
	// the locked craft of enemy
	public Sprite lockedEnemy = null;
	// the index in the Track Missile Shape list
	public int shapeIdx = 0;
	public int quadrantId  = 0;
	// the angle from this to target
	public int angleToTarget = 0;
	// the array of missile's smog
	public int[][] smogPool = null;

	private int lenOfSmogs = 1;

	public TraceMissile() {
		xyz = new int[Bullet.OFFSET_OF_BE_Y + 1];
		smogPool = new int[NUM_OF_MISSILE_SMOG][Bullet.OFFSET_OF_BE_Y + 1];
	}

	public void dragSmogTail() {
		int i = 0;
		for (i = lenOfSmogs - 1; i > 0; i--) {
			smogPool[i][Bullet.OFFSET_OF_BE_X] = smogPool[i - 1][Bullet.OFFSET_OF_BE_X];
			smogPool[i][Bullet.OFFSET_OF_BE_Y] = smogPool[i - 1][Bullet.OFFSET_OF_BE_Y];
		}
		if (lenOfSmogs < NUM_OF_MISSILE_SMOG) {
			lenOfSmogs++;
		}
	}

	public void blowSmogHead() {
		smogPool[0][Bullet.OFFSET_OF_BE_X] = xyz[Bullet.OFFSET_OF_BE_X];
		smogPool[0][Bullet.OFFSET_OF_BE_Y] = xyz[Bullet.OFFSET_OF_BE_Y];
	}

	public void reset() {
		xyz[Bullet.OFFSET_OF_BE_X] = xyz[Bullet.OFFSET_OF_BE_Y] = 0;
		lockedEnemy = null;
		shapeIdx = 0;
		quadrantId = 0;
		angleToTarget = 0;
		for (int i = 0; i < NUM_OF_MISSILE_SMOG; i++) {
			smogPool[i][Bullet.OFFSET_OF_BE_X] = 0;
			smogPool[i][Bullet.OFFSET_OF_BE_Y] = 0;
		}
		lenOfSmogs = 1;
	}

	public boolean isEmpty() {
		if (xyz[Bullet.OFFSET_OF_BE_X] == 0 &&
			xyz[Bullet.OFFSET_OF_BE_Y] == 0 &&
			lockedEnemy == null &&
			shapeIdx == 0 &&
			quadrantId == 0 &&
			angleToTarget == 0) {
			return true;
		}
		return false;
	}

	public boolean isEmptySmog(int index) {
		if (smogPool[index][Bullet.OFFSET_OF_BE_X] == 0 && smogPool[index][Bullet.OFFSET_OF_BE_Y] == 0) {
			return true;
		}
		return false;
	}

	public boolean isOutOfScreen() {
		if (xyz[Bullet.OFFSET_OF_BE_X] < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X || 
			xyz[Bullet.OFFSET_OF_BE_X] > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X + GameBackgroundMap.MAP_IN_SCREEN_WIDTH - PlayerTraceMissileBullet.SIZE_OF_TM || 
			xyz[Bullet.OFFSET_OF_BE_Y] < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y || 
			xyz[Bullet.OFFSET_OF_BE_Y] > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y + GameBackgroundMap.MAP_IN_SCREEN_HEIGHT - PlayerTraceMissileBullet.SIZE_OF_TM) {
			return true;
		}
		return false;
	}
}