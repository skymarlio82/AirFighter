
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.Graphics;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.core.AirFighterEngine;
import org.migame.airfighter.kernal.model.map.GameBackgroundMap;

public abstract class Bullet {

	/*
		--------------------------
		 the structure of missile
		--------------------------
			0  0  0
			0  0  0
			|  |  +--- the X position in the screen
			|  +------ the Y position in the screen
			+--------- the index in the Shape Frame Sequence (only for the track missile)
	*/
	public final static int OFFSET_OF_BE_X = 0;
	public final static int OFFSET_OF_BE_Y = 1;

	public Bullet() {
		
	}

	public void setEmptyBullet(int[][] pool, int index) {
		pool[index][OFFSET_OF_BE_X] = 0;
		pool[index][OFFSET_OF_BE_Y] = 0;
	}

	public boolean isEmptyBullet(int[][] pool, int index) {
		if (pool[index][OFFSET_OF_BE_X] == 0 && pool[index][OFFSET_OF_BE_Y] == 0) {
			return true;
		}
		return false;
	}

	public boolean isOutofScreen(int pool[][], int index) {
		if (pool[index][OFFSET_OF_BE_X] < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X || 
			pool[index][OFFSET_OF_BE_X] > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X + GameBackgroundMap.MAP_IN_SCREEN_WIDTH || 
			pool[index][OFFSET_OF_BE_Y] < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y || 
			pool[index][OFFSET_OF_BE_Y] > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y + GameBackgroundMap.MAP_IN_SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}

	public int getQuadrantId(int srcX, int srcY, int destX, int destY) {
		int id = 0;
		if (destX >= srcX && destY <= srcY) {
			id = AppletConstant.IDX_QUADRANT_01;
		} else if (destX >= srcX && destY >= srcY) {
			id = AppletConstant.IDX_QUADRANT_02;
		} else if (destX <= srcX && destY >= srcY) {
			id = AppletConstant.IDX_QUADRANT_03;
		} else if (destX <= srcX && destY <= srcY) {
			id = AppletConstant.IDX_QUADRANT_04;
		}
		return id;
	}

	public abstract void runBullets();

	public abstract void toPaint(Graphics g);

}