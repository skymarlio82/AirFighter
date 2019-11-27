
package org.migame.airfighter.kernal.model.craft;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.bullet.MachineGunFire;
import org.migame.airfighter.kernal.model.bullet.PlayerBulletT0;
import org.migame.airfighter.kernal.model.bullet.PlayerBulletT1;
import org.migame.airfighter.kernal.model.bullet.PlayerTraceMissileBullet;

public class PlayerAircraft extends Aircraft {

	public final static int PLAYER_AIRCRAFT_WIDTH  = 23;
	public final static int PLAYER_AIRCRAFT_HEIGHT = 32;

	// -----------------------------------------------------------
	// define the area of collision rectangle on the player image
	// -----------------------------------------------------------
	public final static int PLAYER_CR_POS_X  = 5;
	public final static int PLAYER_CR_POS_Y  = 11;
	public final static int PLAYER_CR_WIDTH  = 12;
	public final static int PLAYER_CR_HEIGHT = 20;

	public final static int SEQ_IDX_FRAME_LEFT   = 0;
	public final static int SEQ_IDX_FRAME_NORMAL = 1;
	public final static int SEQ_IDX_FRAME_RIGHT  = 2;

	public final static int MAX_OF_PLAYER_BULLET_LEV  = 3;
	public final static int LEV_OF_PLAYER_BULLET_L1   = 1;
	public final static int LEV_OF_PLAYER_BULLET_L2   = 2;
	public final static int LEV_OF_PLAYER_BULLET_L3   = 3;
	public final static int MAX_OF_PLAYER_BULLET_TYPE = 3;
	public final static int TYPE_OF_PLAYER_BULLET_T0  = 0;
	public final static int TYPE_OF_PLAYER_BULLET_T1  = 1;
	public final static int TYPE_OF_PLAYER_BULLET_T2  = 2;

	public final static int PLAYER_AIRCRAFT_SPEED = 10;

	// ------------------------------
	// the explosion sprite of craft
	// ------------------------------
	public final static int FSN_BOOM_BLAST    = 16;
	public final static int BOOM_BLAST_WIDTH  = 60;
	public final static int BOOM_BLAST_HEIGHT = 60;

	private final static int MAX_BOOM_BLAST_FRAMES = 20*FSN_BOOM_BLAST;

	// the image of craft explosion
	private Image[] IMG_BOOM_BLAST = null;

	private int idxBoomBlast = 0;

	// the point of bomb
	private int bp = 0;

	private int bulletLevel = 0;

	public PlayerBulletT0           pbT0 = null;
	public PlayerBulletT1           pbT1 = null;
	public PlayerTraceMissileBullet pbT2 = null;

	public final static int BULLET_FREQUENCE = 2;
	public int freqB = 0;

	public final static int TMS_FREQUENCE = 15;
	public int freqTM = 0;

	public PlayerAircraft(Image img, int initPosX, int initPosY, // used to set the features of sprite object
        Image[] imgTB, // used to set the gun fire (only one)
        Image imgACB, // the image of Air Craft Blast
        Vector enemys, // all the sets of enemy craft
        Image imgTmSmog, // the image of Track Missile's smog
        Image[] imgBoom, 
        Image imgPBT0, Image imgPBT1, Image[] imgPBT2) {
		super(img, PLAYER_AIRCRAFT_WIDTH, PLAYER_AIRCRAFT_HEIGHT, initPosX, initPosY, SEQ_IDX_FRAME_NORMAL, imgACB);
		super.setMachineGunFire(new MachineGunFire(imgTB, imgTB[0].getWidth(), imgTB[0].getHeight(), this));
		defineCollisionRectangle(PLAYER_CR_POS_X, PLAYER_CR_POS_Y, PLAYER_CR_WIDTH, PLAYER_CR_HEIGHT);
		IMG_BOOM_BLAST = imgBoom;
		pbT0 = new PlayerBulletT0(imgPBT0);
		pbT1 = new PlayerBulletT1(imgPBT1);
		pbT2 = new PlayerTraceMissileBullet(imgPBT2, imgTmSmog, enemys);
		bulletLevel = LEV_OF_PLAYER_BULLET_L3;
	}

	public void fire() {
		if ((freqB = (++freqB)%BULLET_FREQUENCE) == 0) {
			switch (bulletLevel) {
				case LEV_OF_PLAYER_BULLET_L1:
					pbT0.launch((Aircraft)this);
					break;
				case LEV_OF_PLAYER_BULLET_L2:
				case LEV_OF_PLAYER_BULLET_L3:
					pbT1.launch((Aircraft)this);
					if (bulletLevel == LEV_OF_PLAYER_BULLET_L3) {
						if ((freqTM = (++freqTM)%TMS_FREQUENCE) == 0) {
							pbT2.launchTM((Aircraft) this);
						}
					}
					break;
				default:
					break;
			}
		}
	}

	public void runWeapon() {
		pbT0.runBullets();
		pbT1.runBullets();
		pbT2.runBullets();
	}

	public void toPaintWeapon(Graphics g) {
		pbT0.toPaint(g);
		pbT1.toPaint(g);
		pbT2.toPaint(g);
	}

	public void increaseBP(int bp) {
		this.bp += bp;
	}

	public void decreaseBP(int bp) {
		this.bp -= bp;
	}

	public int getBp() {
		return bp;
	}

	public void setBp(int bp) {
		this.bp = bp;
	}

	// --------------------------
	// handle with craft's blast
	// --------------------------
	public boolean isBoomBlastOver() {
		if (idxBoomBlast >= MAX_BOOM_BLAST_FRAMES) {
			idxBoomBlast = 0;
			return true;
		}
		return false;
	}

	public void runBoomBlast() {
		if (!isBoomBlastOver()) {
			idxBoomBlast++;
		}
	}

	public void toPaintBoomBlast(Graphics g) {
		g.drawImage(IMG_BOOM_BLAST[idxBoomBlast%FSN_BOOM_BLAST],
			getX() + getWidth()/2 - BOOM_BLAST_WIDTH/2,
			getY() + getHeight()/2 - BOOM_BLAST_HEIGHT/2,
			AppletConstant.GTL);
	}
}