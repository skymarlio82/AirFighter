
package org.migame.airfighter.kernal.model.craft;

import java.util.Vector;

import javax.microedition.lcdui.Image;

public abstract class AbstractEnemyAircraft extends Aircraft {

	// the max number of craft's missile to lock the enemy
	public final static int MAX_ENEMY_AIR_CRAFT_LP = 10;

	// the point of locked time
	private int lp = 0;

	// the container of all the enemys which belong to the AFGameKernal
	protected Vector enemyPool = null;

	public AbstractEnemyAircraft(Image img, int frameWidth, int frameHeight,
		int initPosX, int initPosY,
		int idxFrame,
		Image imgACB) {
		super(img, frameWidth, frameHeight, initPosX, initPosY, idxFrame, imgACB);
	}

	public void increaseLP(int lp) {
		this.lp += lp;
	}

	public void decreaseLP(int lp) {
		this.lp -= lp;
	}

	public int getLp() {
		return lp;
	}

	public void setLp(int lp) {
		this.lp = lp;
	}

	/* Used to implement the serial actions */
	public abstract void runAI();

}