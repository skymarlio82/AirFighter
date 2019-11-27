
package org.migame.airfighter.kernal.model.craft;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import net.jscience.math.kvm.MathFP;

import org.migame.airfighter.kernal.model.bullet.EnemyStraight01Bullet;
import org.migame.airfighter.kernal.model.bullet.MachineGunFire;

public class Enemy01Aircraft extends AbstractEnemyAircraft {

	// ---------------------------------------------
	// the width and height of this enemy air craft
	// ---------------------------------------------
	public final static int ENEMY_01_AIRCRAFT_WIDTH  = 21;
	public final static int ENEMY_01_AIRCRAFT_HEIGHT = 32;

	// ------------------------------------------------
	// the range of collision rectangle of this sprite
	// ------------------------------------------------
	private final static int ENEMY_01_CR_POS_X  = 3;
	private final static int ENEMY_01_CR_POS_Y  = 6;
	private final static int ENEMY_01_CR_WIDTH  = 15;
	private final static int ENEMY_01_CR_HEIGHT = 20;

	public final static int SEQ_IDX_FRAME_LEFT   = 0;
	public final static int SEQ_IDX_FRAME_NORMAL = 1;
	public final static int SEQ_IDX_FRAME_RIGHT  = 2;

	// the speed of Enemy-1 Aircraft
	public final static int ENEMY_01_AIRCRAFT_SPEED    = 6;
	public final static int ENEMY_01_AIRCRAFT_SPEED_FP = MathFP.toFP(ENEMY_01_AIRCRAFT_SPEED);

	// the weapon of this enemy
	private EnemyStraight01Bullet es01b = null;

	private Aircraft playerAircraft = null;

	public Enemy01Aircraft(Image img, int initPosX, int initPosY, // used to set the features of sprite object
		Image[] imgLB, // used to set the gun fire (Linear Bullet)
		Image[] imgTB, // used to set the gun fire (Trace Bullet)
		Image imgACB, // the image of Air Craft Blast
		EnemyStraight01Bullet es01b, 
		Vector enemyPool, 
		Aircraft playerAircraft) {
		super(img, ENEMY_01_AIRCRAFT_WIDTH, ENEMY_01_AIRCRAFT_HEIGHT, initPosX, initPosY, SEQ_IDX_FRAME_NORMAL, imgACB);
		super.setMachineGunFire(new MachineGunFire(imgLB,
			imgLB[0].getWidth(), imgLB[0].getHeight(),
			imgTB,
			imgTB[0].getWidth(), imgTB[0].getHeight(),
			this));
		super.enemyPool = enemyPool;
		defineCollisionRectangle(ENEMY_01_CR_POS_X, ENEMY_01_CR_POS_Y, ENEMY_01_CR_WIDTH, ENEMY_01_CR_HEIGHT);
		this.es01b = es01b;
		this.playerAircraft = playerAircraft;
	}

	public void fire() {
		es01b.launch(this);
	}

	// -------------------------------------------
	// the actions of this Artificial Intelligent
	// -------------------------------------------
	private int aiLocus           = 0;
	private int PV_FP             = 0;
	public int idxDirection       = 0;
	public boolean isFlyingOnLeft = false;

	public void runAI() {
		if (aiLocus >= 0 && aiLocus < 20) {
			move(0, 1);
		} else {
			if (aiLocus == 20) {
				isFlyingOnLeft = (getX() > playerAircraft.getX()) ? true : false;
				idxDirection = (isFlyingOnLeft) ? (EnemyStraight01Bullet.MAX_NUM_OF_ESB_DIRECTION - 1) : 0;
				int X_FP = MathFP.toFP(Math.abs(getX() - playerAircraft.getX()));
				int Y_FP = MathFP.toFP(Math.abs(playerAircraft.getY() - getY()));
				PV_FP = MathFP.atan2(Y_FP, X_FP);
				if (isFlyingOnLeft) {
					setFrame(SEQ_IDX_FRAME_LEFT);
				} else {
					setFrame(SEQ_IDX_FRAME_RIGHT);
				}
			}
			if (aiLocus > 20) {
				if (idxDirection >= 0 && idxDirection <= EnemyStraight01Bullet.MAX_NUM_OF_ESB_DIRECTION - 1) {
					fire();
				}
				int deltaX = MathFP.toInt(MathFP.mul(ENEMY_01_AIRCRAFT_SPEED_FP, MathFP.cos(PV_FP)));
				int deltaY = MathFP.toInt(MathFP.mul(ENEMY_01_AIRCRAFT_SPEED_FP, MathFP.sin(PV_FP)));
				if (isFlyingOnLeft) {
					move(-deltaX, deltaY);
				} else {
					move(deltaX, deltaY);
				}
			}
		}
		if (isOutOfScreen()) {
			enemyPool.removeElement(this);
		}
		aiLocus++;
	}
}