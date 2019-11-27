
package org.migame.airfighter.kernal.model.craft;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.core.AirFighterEngine;
import org.migame.airfighter.kernal.model.bullet.MachineGunFire;
import org.migame.airfighter.kernal.model.map.GameBackgroundMap;

public abstract class Aircraft extends Sprite {

	// ------------------------------
	// the explosion sprite of craft
	// ------------------------------
	private final static int FSN_AIR_CRAFT_BLAST    = 14;
	private final static int AIR_CRAFT_BLAST_WIDTH  = 48;
	private final static int AIR_CRAFT_BLAST_HEIGHT = 67;

	// the image of craft blast
	private Image IMG_AIR_CRAFT_BLAST = null;

	// the point of health
	private int hp = 0;

	// the object of gun fire
	public MachineGunFire mgf = null;

	// the index of air craft destroy's sprite
	private int idxACD = 0;

	public Aircraft(Image img, int frameWidth, int frameHeight,
		int initPosX, int initPosY,
		int idxFrame,
		Image imgACB) {
		super(img, frameWidth, frameHeight);
		setPosition(initPosX, initPosY);
		setFrame(idxFrame);
		IMG_AIR_CRAFT_BLAST = imgACB;
	}

	/* set the internal object */
	public void setMachineGunFire(MachineGunFire mgf) {
		this.mgf = mgf;
	}

	/* check whether the craft is out of Mobile Screen */
	public boolean isOutOfScreen() {
		if (getX() < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X || 
			getX() > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X + GameBackgroundMap.MAP_IN_SCREEN_WIDTH - getWidth() || 
			getY() < AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y || 
			getY() > AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y + GameBackgroundMap.MAP_IN_SCREEN_HEIGHT - getHeight()) {
			return true;
		}
		return false;
	}

	// -------------------
	// the Java Bean - HP
	// -------------------
	public void increaseHP(int HP) {
		this.hp += HP;
	}

	public void decreaseHP(int HP) {
		this.hp -= HP;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	// --------------------------
	// handle with craft's blast
	// --------------------------
	public boolean isBlastOver() {
		if (idxACD >= FSN_AIR_CRAFT_BLAST) {
			return true;
		}
		return false;
	}

	public void runBlast() {
		if (!isBlastOver()) {
			idxACD++;
		}
	}

	public void toPaintBlast(Graphics g) {
		g.drawRegion(IMG_AIR_CRAFT_BLAST,
			AIR_CRAFT_BLAST_WIDTH*idxACD, 0,
			AIR_CRAFT_BLAST_WIDTH, AIR_CRAFT_BLAST_HEIGHT,
			Sprite.TRANS_NONE,
			getX(), getY(),
			AppletConstant.GTL);
	}

	public abstract void fire();
}