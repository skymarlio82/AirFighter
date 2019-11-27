
package org.migame.airfighter.kernal.model.craft;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import net.jscience.math.kvm.MathFP;

import org.migame.airfighter.kernal.model.bullet.EnemyStraight01Bullet;
import org.migame.airfighter.kernal.model.bullet.MachineGunFire;

public class EnemyBossAircraft extends AbstractEnemyAircraft {

	// ---------------------------------------------
	// the width and height of boss enemy air craft
	// ---------------------------------------------
	public final static int ENEMY_BOSS_AIRCRAFT_WIDTH  = 50;
	public final static int ENEMY_BOSS_AIRCRAFT_HEIGHT = 39;

	// ------------------------------------------------
	// the range of collision rectangle of this sprite
	// ------------------------------------------------
	private final static int ENEMY_BOSS_CR_POS_X  = 18;
	private final static int ENEMY_BOSS_CR_POS_Y  = 7;
	private final static int ENEMY_BOSS_CR_WIDTH  = 14;
	private final static int ENEMY_BOSS_CR_HEIGHT = 21;

	public final static int SEQ_IDX_FRAME_NORMAL = 0;

	// the speed of Enemy Boss Aircraft
	public final static int ENEMY_BOSS_AIRCRAFT_SPEED    = 1;
	public final static int ENEMY_BOSS_AIRCRAFT_SPEED_FP = MathFP.toFP(ENEMY_BOSS_AIRCRAFT_SPEED);

	// the weapon of this enemy
	private EnemyStraight01Bullet es01b = null;

	private Aircraft playerAircraft = null;

	public EnemyBossAircraft(Image img, int initPosX, int initPosY, // used to set the features of sprite object
		Image[] imgLB, // used to set the gun fire (Linear Bullet)
		Image[] imgTB, // used to set the gun fire (Trace Bullet)
		Image imgACB, // the image of Air Craft Blast
		EnemyStraight01Bullet es01b, 
		Vector enemyPool, 
		Aircraft playerAircraft) {
		super(img,
			ENEMY_BOSS_AIRCRAFT_WIDTH, ENEMY_BOSS_AIRCRAFT_HEIGHT,
			initPosX, initPosY,
			SEQ_IDX_FRAME_NORMAL,
			imgACB);
		super.setMachineGunFire(new MachineGunFire(imgLB,
			imgLB[0].getWidth(), imgLB[0].getHeight(),
			imgTB,
			imgTB[0].getWidth(), imgTB[0].getHeight(),
			this));
		super.enemyPool = enemyPool;
		defineCollisionRectangle(ENEMY_BOSS_CR_POS_X, ENEMY_BOSS_CR_POS_Y, ENEMY_BOSS_CR_WIDTH, ENEMY_BOSS_CR_HEIGHT);
		this.es01b = es01b;
		this.playerAircraft = playerAircraft;
	}

	public void fire() {
		es01b.launch(this);
	}

	// -------------------------------------------
	// the actions of this Artificial Intelligent
	// -------------------------------------------
	private int aiLocus = 0;

	public void runAI() {
		if (aiLocus%5 == 0) {
			fire();
		}
		aiLocus++;
	}
}