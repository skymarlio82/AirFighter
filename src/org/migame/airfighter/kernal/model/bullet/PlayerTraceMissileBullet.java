
package org.migame.airfighter.kernal.model.bullet;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import net.jscience.math.kvm.MathFP;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.craft.Aircraft;
import org.migame.airfighter.kernal.model.craft.AbstractEnemyAircraft;

public class PlayerTraceMissileBullet extends Bullet {

	// the max volume of missile
	public final static int MAX_NUMBER_OF_TM = 8;

	public final static int SPEED_BULLET_T2    = 10;
	public final static int SPEED_BULLET_T2_FP = MathFP.toFP(SPEED_BULLET_T2);

	// the initial position Y of Track Missile
	public final static int INIT_POS_Y_OF_TM    = 20;
	// the amount of Track Missile Frame
	public final static int FN_OF_TRACE_MISSILE = 16;
	// the size of Track Missile's image
	public final static int SIZE_OF_TM          = 16;
	// the size of Missile's smog
	public final static int SIZE_OF_MS          = 16;

	// the container of Track Missile
	private TraceMissile[] tmPool = null;

	// the image list of track missile frame shape
	private Image[] IMG_PLAYER_TM_FS = null;
	// the image sprite of missile smog
	private Image IMG_MISSILE_SMOG = null;

	// the container of all the enemy craft
	private Vector enemyPool = null;

	public PlayerTraceMissileBullet(Image[] imgTmFs, Image imgMs, Vector enemyPool) {
		IMG_PLAYER_TM_FS = imgTmFs;
		IMG_MISSILE_SMOG = imgMs;
		this.enemyPool = enemyPool;
		tmPool = new TraceMissile[MAX_NUMBER_OF_TM];
		for (int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			tmPool[i] = new TraceMissile();
		}
	}

	public Image getImage(int index) {
		return IMG_PLAYER_TM_FS[index];
	}

	public void setEmptyBullet(int index) {
		tmPool[index].reset();
	}

	public boolean isEmptyBullet(int index) {
		return tmPool[index].isEmpty();
	}

	public boolean isBulletOutofScreen(int index) {
		return tmPool[index].isOutOfScreen();
	}

	public Sprite getLockedEnemyAircraft() {
		Enumeration enu = enemyPool.elements();
		while (enu.hasMoreElements()) {
			AbstractEnemyAircraft eac = (AbstractEnemyAircraft)enu.nextElement();
			if (eac.getLp() < AbstractEnemyAircraft.MAX_ENEMY_AIR_CRAFT_LP) {
				eac.increaseLP(1);
				return (Sprite)eac;
			}
		}
		return (Sprite)null;
	}

	public void launchTM(Aircraft ac) {
		int count = 0;
		for (int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			if (count >= 2) {
				break;
			}
			if (isEmptyBullet(i)) {
				tmPool[i].xyz[OFFSET_OF_BE_Y] = ac.getY();
				switch (count) {
					case 0:
						tmPool[i].xyz[OFFSET_OF_BE_X] = ac.getX() - SIZE_OF_TM;
						tmPool[i].shapeIdx = 15;
						tmPool[i].quadrantId = AppletConstant.IDX_QUADRANT_04;
						tmPool[i].angleToTarget = AppletConstant.PR78_FP;
						break;
					case 1:
						tmPool[i].xyz[OFFSET_OF_BE_X] = ac.getX() + ac.getWidth();
						tmPool[i].shapeIdx = 1;
						tmPool[i].quadrantId = AppletConstant.IDX_QUADRANT_01;
						tmPool[i].angleToTarget = AppletConstant.PR78_FP;
					default:
						break;
				}
				count++;
			}
		}
	}

	public void checkCollisionWithOthers(Aircraft ac) {
		int rx = 0, ry = 0;
		for(int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			if (!isEmptyBullet(i)) {
				if (ac.collidesWith(getImage(tmPool[i].shapeIdx),
					tmPool[i].xyz[Bullet.OFFSET_OF_BE_X],
					tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y],
					false)) {
					ac.decreaseHP(2);
					int qid = getQuadrantId(tmPool[i].xyz[Bullet.OFFSET_OF_BE_X] + SIZE_OF_TM/2,
						tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y] + SIZE_OF_TM/2,
						ac.getX() + ac.getWidth()/2,
						ac.getY() + ac.getHeight()/2);
					switch (qid) {
						case AppletConstant.IDX_QUADRANT_01:
							rx = (tmPool[i].xyz[Bullet.OFFSET_OF_BE_X] + SIZE_OF_TM/2) - (ac.getX() + ac.getWidth()/2);
							ry = -((ac.getY() + ac.getHeight()/2) - (tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y] + SIZE_OF_TM/2));
							break;
						case AppletConstant.IDX_QUADRANT_02:
							rx = (tmPool[i].xyz[Bullet.OFFSET_OF_BE_X] + SIZE_OF_TM/2) - (ac.getX() + ac.getWidth()/2);
							ry = (tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y] + SIZE_OF_TM/2) - (ac.getY() + ac.getHeight()/2);
							break;
						case AppletConstant.IDX_QUADRANT_03:
							rx = -((ac.getX() + ac.getWidth()/2) - (tmPool[i].xyz[Bullet.OFFSET_OF_BE_X] + SIZE_OF_TM/2));
							ry = (tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y] + SIZE_OF_TM/2) - (ac.getY() + ac.getHeight()/2);
							break;
						case AppletConstant.IDX_QUADRANT_04:
							rx = -((ac.getX() + ac.getWidth()/2) - (tmPool[i].xyz[Bullet.OFFSET_OF_BE_X] + SIZE_OF_TM/2));
							ry = -((ac.getY() + ac.getHeight()/2) - (tmPool[i].xyz[Bullet.OFFSET_OF_BE_Y] + SIZE_OF_TM/2));
							break;
					}
					ac.mgf.addGunFire(MachineGunFire.GUN_FIRE_TYPE_TB, rx, ry);
					setEmptyBullet(i);
				}
			}
		}
	}

	public void processTM(TraceMissile tm) {
		int X_FP = 0, Y_FP = 0, PV_FP = 0;
		tm.dragSmogTail();
		tm.blowSmogHead();
		int dx = tm.lockedEnemy.getX() + tm.lockedEnemy.getWidth()/2;
		int dy = tm.lockedEnemy.getY() + tm.lockedEnemy.getHeight()/2;
		Y_FP = MathFP.toFP(Math.abs(tm.xyz[OFFSET_OF_BE_Y] - dy));
		X_FP = MathFP.toFP(Math.abs(tm.xyz[OFFSET_OF_BE_X] - dx));
		PV_FP = MathFP.atan2(Y_FP, X_FP);
		int deltaX_FP = MathFP.mul(SPEED_BULLET_T2_FP, MathFP.cos(PV_FP));
		int deltaY_FP = MathFP.mul(SPEED_BULLET_T2_FP, MathFP.sin(PV_FP));
		/*
			E   |        E   |   E
			    |            |    
			----M----    ----M----
			    |            |    
			E   |            |    
		*/
		// adjust the coordinate-X of missile's position 
		if (dx <= tm.xyz[OFFSET_OF_BE_X]) {
			tm.xyz[OFFSET_OF_BE_X] -= MathFP.toInt(deltaX_FP);
		} else {
			tm.xyz[OFFSET_OF_BE_X] += MathFP.toInt(deltaX_FP);
		}
		// adjust the coordinate-Y of missile's position 
		if (dy <= tm.xyz[OFFSET_OF_BE_Y]) {
			tm.xyz[OFFSET_OF_BE_Y] -= MathFP.toInt(deltaY_FP);
		} else {
			tm.xyz[OFFSET_OF_BE_Y] += MathFP.toInt(deltaY_FP);
		}
		// check whether the missile is running out of the range
		if (tm.isOutOfScreen()) {
			tm.reset();
			return;
		}
		tm.quadrantId = getQuadrantId(tm.xyz[OFFSET_OF_BE_X], tm.xyz[OFFSET_OF_BE_Y], dx, dy);
		tm.shapeIdx = getMissileTraceDirectionIdx(PV_FP, tm.quadrantId);
		tm.angleToTarget = PV_FP;
	}

	/*
		    |   E        |            |        E   |    
		    |            |            |            |    
		----M----    ----M----    ----M----    ----M----
		    |            |            |            |    
		    |            |   E    E   |            |    
	*/
	public int getMissileTraceDirectionIdx(int pv, int quadrant) {
		int p = 0, index = 0;
		if (pv > AppletConstant.PR78_FP && pv <= AppletConstant.PR90_FP) {
			p = 0;
		} else if(pv > AppletConstant.PR56_FP && pv <= AppletConstant.PR78_FP) {
			p = 1;
		} else if(pv > AppletConstant.PR33_FP && pv <= AppletConstant.PR56_FP) {
			p = 2;
		} else if(pv > AppletConstant.PR11_FP && pv <= AppletConstant.PR33_FP) {
			p = 3;
		} else if(pv >= AppletConstant.PR00_FP && pv <= AppletConstant.PR11_FP) {
			p = 4;
		}
		switch (quadrant) {
			case AppletConstant.IDX_QUADRANT_01:
				index = p;
				break;
			case AppletConstant.IDX_QUADRANT_02:
				index = 8 - p;
				break;
			case AppletConstant.IDX_QUADRANT_03:
				index = 8 + p;
				break;
			case AppletConstant.IDX_QUADRANT_04:
				index = (16 - p)%16;
				break;
			default:
				break;
		}
		return index;
	}

	public void exceedBullet(TraceMissile tm, int speed) {
		tm.dragSmogTail();
		tm.blowSmogHead();
		int deltaX_FP = MathFP.mul(speed, MathFP.cos(tm.angleToTarget));
		int deltaY_FP = MathFP.mul(speed, MathFP.sin(tm.angleToTarget));
		switch (tm.quadrantId) {
			case AppletConstant.IDX_QUADRANT_01:
				tm.xyz[OFFSET_OF_BE_X] += MathFP.toInt(deltaX_FP);
				tm.xyz[OFFSET_OF_BE_Y] -= MathFP.toInt(deltaY_FP);
				break;
			case AppletConstant.IDX_QUADRANT_02:
				tm.xyz[OFFSET_OF_BE_X] += MathFP.toInt(deltaX_FP);
				tm.xyz[OFFSET_OF_BE_Y] += MathFP.toInt(deltaY_FP);
				break;
			case AppletConstant.IDX_QUADRANT_03:
				tm.xyz[OFFSET_OF_BE_X] -= MathFP.toInt(deltaX_FP);
				tm.xyz[OFFSET_OF_BE_Y] += MathFP.toInt(deltaY_FP);
				break;
			case AppletConstant.IDX_QUADRANT_04:
				tm.xyz[OFFSET_OF_BE_X] -= MathFP.toInt(deltaX_FP);
				tm.xyz[OFFSET_OF_BE_Y] -= MathFP.toInt(deltaY_FP);
				break;
		}
		// check whether the missile is running out of the range
		if (tm.isOutOfScreen()) {
			tm.reset();
		}
	}

	public void clearTMBuffer() {
		for (int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			tmPool[i].reset();
		}
	}

	public void runBullets() {
		for (int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			if (tmPool[i].lockedEnemy != null) {
				processTM(tmPool[i]);
			} else {
				if (tmPool[i].quadrantId != 0) {
					if ((tmPool[i].lockedEnemy = getLockedEnemyAircraft()) != null) {
						processTM(tmPool[i]);
					} else {
						exceedBullet(tmPool[i], SPEED_BULLET_T2_FP);
					}
				}
			}
		}
	}

	public void toPaint(Graphics g) {
		for (int i = 0; i < MAX_NUMBER_OF_TM; i++) {
			TraceMissile tme = tmPool[i];
			if (!isEmptyBullet(i)) {
				g.drawImage(getImage(tme.shapeIdx),
					tme.xyz[OFFSET_OF_BE_X],
					tme.xyz[OFFSET_OF_BE_Y],
					AppletConstant.GTL);
				for (int j = 0; j < TraceMissile.NUM_OF_MISSILE_SMOG; j++) {
					if (!tme.isEmptySmog(j)) {
						g.drawRegion(IMG_MISSILE_SMOG,
							j*SIZE_OF_MS, 0,
							SIZE_OF_MS, SIZE_OF_MS,
							Sprite.TRANS_NONE,
							tme.smogPool[j][OFFSET_OF_BE_X], tme.smogPool[j][OFFSET_OF_BE_Y],
							AppletConstant.GTL);
					}
				}
			}
		}
	}
}