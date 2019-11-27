
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.craft.Aircraft;
import org.migame.airfighter.kernal.model.craft.Enemy01Aircraft;
import org.migame.airfighter.kernal.model.craft.EnemyBossAircraft;

import net.jscience.math.kvm.MathFP;

public class EnemyStraight01Bullet extends Bullet {

	private final static int MAX_VOLUME_OF_ESB_POOL = 180;

	private final static int OFFSET_OF_ESB_FS_IDX    = 2;
	private final static int OFFSET_OF_ESB_DIRECTION = 3;

	public  final static int MAX_NUM_OF_ESB_DIRECTION = 9;

	private final static int ESB_DIRECTION_IDX_00 = 0;
	private final static int ESB_DIRECTION_IDX_01 = 1;
	private final static int ESB_DIRECTION_IDX_02 = 2;
	private final static int ESB_DIRECTION_IDX_03 = 3;
	private final static int ESB_DIRECTION_IDX_04 = 4;
	private final static int ESB_DIRECTION_IDX_05 = 5;
	private final static int ESB_DIRECTION_IDX_06 = 6;
	private final static int ESB_DIRECTION_IDX_07 = 7;
	private final static int ESB_DIRECTION_IDX_08 = 8;

	private final static int SIZE_OF_ENEMY_DIRECTION_BULLET = 6;

	private final static int SPEED_OF_ESB    = 6;
	private final static int SPEED_OF_ESB_FP = MathFP.toFP(SPEED_OF_ESB);

	public final static int MAX_FSN_STRAIGHT_BULLET_POOL = 8;

	private int[][] esbPool = null;
	private Image IMG_ENEMY_STRAIGHT_BULLET_01 = null;

	public EnemyStraight01Bullet(Image imgESB) {
		IMG_ENEMY_STRAIGHT_BULLET_01 = imgESB;
		esbPool = new int[MAX_VOLUME_OF_ESB_POOL][OFFSET_OF_ESB_DIRECTION + 1];
	}

	public Image getImage(int index) {
		return Image.createImage(IMG_ENEMY_STRAIGHT_BULLET_01,
			index*SIZE_OF_ENEMY_DIRECTION_BULLET, 0,
			SIZE_OF_ENEMY_DIRECTION_BULLET, SIZE_OF_ENEMY_DIRECTION_BULLET,
			Sprite.TRANS_NONE);
	}

	public void setEmptyBullet(int index) {
		super.setEmptyBullet(esbPool, index);
		esbPool[index][OFFSET_OF_ESB_FS_IDX] = 0;
		esbPool[index][OFFSET_OF_ESB_DIRECTION] = 0;
	}

	public boolean isEmptyBullet(int index) {
		if (super.isEmptyBullet(esbPool, index) &&
			esbPool[index][OFFSET_OF_ESB_FS_IDX] == 0 &&
			esbPool[index][OFFSET_OF_ESB_DIRECTION] == 0) {
			return true;
		}
		return false;
	}

	public boolean isOutOfScreen(int index) {
		return super.isOutofScreen(esbPool, index);
	}

	public void launch(Enemy01Aircraft c) {
		for (int i = 0; i < MAX_VOLUME_OF_ESB_POOL; i++) {
			if (isEmptyBullet(i)) {
				esbPool[i][OFFSET_OF_BE_X] = c.getX() + c.getWidth()/2 - SIZE_OF_ENEMY_DIRECTION_BULLET/2;
				esbPool[i][OFFSET_OF_BE_Y] = c.getY() + c.getHeight()/2 - SIZE_OF_ENEMY_DIRECTION_BULLET/2;
				esbPool[i][OFFSET_OF_ESB_FS_IDX] = 0;
				esbPool[i][OFFSET_OF_ESB_DIRECTION] = c.idxDirection;
				c.idxDirection = (c.isFlyingOnLeft) ? (c.idxDirection - 1) : (c.idxDirection + 1);
				break;
			}
		}
	}

	public void launch(EnemyBossAircraft boss) {
		for (int i = 0; i < MAX_VOLUME_OF_ESB_POOL; i++) {
			if (isEmptyBullet(i)) {
				esbPool[i][OFFSET_OF_BE_X] = boss.getX() + boss.getWidth()/2 - SIZE_OF_ENEMY_DIRECTION_BULLET/2;
				esbPool[i][OFFSET_OF_BE_Y] = boss.getY() + boss.getHeight()/2 - SIZE_OF_ENEMY_DIRECTION_BULLET/2;
				esbPool[i][OFFSET_OF_ESB_FS_IDX] = 0;
				esbPool[i][OFFSET_OF_ESB_DIRECTION] = ESB_DIRECTION_IDX_04;
				break;
			}
		}
	}

	public void runBullets() {
		for (int i = 0; i < MAX_VOLUME_OF_ESB_POOL; i++) {
			if (!isEmptyBullet(i)) {
				esbPool[i][OFFSET_OF_ESB_FS_IDX] = (esbPool[i][OFFSET_OF_ESB_FS_IDX] + 1)%MAX_FSN_STRAIGHT_BULLET_POOL;
				switch (esbPool[i][OFFSET_OF_ESB_DIRECTION]) {
					case ESB_DIRECTION_IDX_00:
						esbPool[i][OFFSET_OF_BE_X] -= MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR30_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR30_FP)));
						break;
					case ESB_DIRECTION_IDX_01:
						esbPool[i][OFFSET_OF_BE_X] -= MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR45_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR45_FP)));
						break;
					case ESB_DIRECTION_IDX_02:
						esbPool[i][OFFSET_OF_BE_X] -= MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR60_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR60_FP)));
						break;
					case ESB_DIRECTION_IDX_03:
						esbPool[i][OFFSET_OF_BE_X] -= MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR75_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR75_FP)));
						break;
					case ESB_DIRECTION_IDX_04:
						esbPool[i][OFFSET_OF_BE_Y] += SPEED_OF_ESB;
						break;
					case ESB_DIRECTION_IDX_05:
						esbPool[i][OFFSET_OF_BE_X] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR75_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR75_FP)));
						break;
					case ESB_DIRECTION_IDX_06:
						esbPool[i][OFFSET_OF_BE_X] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR60_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR60_FP)));
						break;
					case ESB_DIRECTION_IDX_07:
						esbPool[i][OFFSET_OF_BE_X] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR45_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR45_FP)));
						break;
					case ESB_DIRECTION_IDX_08:
						esbPool[i][OFFSET_OF_BE_X] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.cos(AppletConstant.PR30_FP)));
						esbPool[i][OFFSET_OF_BE_Y] += MathFP.toInt(MathFP.mul(SPEED_OF_ESB_FP, MathFP.sin(AppletConstant.PR30_FP)));
						break;
					default:
						break;
				}
				if (isOutOfScreen(i)) {
					setEmptyBullet(i);
				}
			}
		}
	}

	public void checkCollisionWithOthers(Aircraft ac) {
		int rx = 0, ry = 0;		
		for (int i = 0; i < MAX_VOLUME_OF_ESB_POOL; i++) {
			if (!isEmptyBullet(i)) {
				if (ac.collidesWith(getImage(esbPool[i][OFFSET_OF_ESB_FS_IDX]),
					esbPool[i][OFFSET_OF_BE_X], esbPool[i][OFFSET_OF_BE_Y],
					false)) {
					ac.decreaseHP(1);
					int qid = getQuadrantId(esbPool[i][OFFSET_OF_BE_X] + SIZE_OF_ENEMY_DIRECTION_BULLET/2,
						esbPool[i][OFFSET_OF_BE_Y] + SIZE_OF_ENEMY_DIRECTION_BULLET/2,
						ac.getX() + ac.getWidth()/2, ac.getY() + ac.getHeight()/2);
					switch (qid) {
						case AppletConstant.IDX_QUADRANT_01:
							rx = (esbPool[i][OFFSET_OF_BE_X] + SIZE_OF_ENEMY_DIRECTION_BULLET/2) - (ac.getX() + ac.getWidth()/2);
							ry = -((ac.getY() + ac.getHeight()/2) - (esbPool[i][OFFSET_OF_BE_Y] + SIZE_OF_ENEMY_DIRECTION_BULLET/2));
							break;
						case AppletConstant.IDX_QUADRANT_02:
							rx = (esbPool[i][OFFSET_OF_BE_X] + SIZE_OF_ENEMY_DIRECTION_BULLET/2) - (ac.getX() + ac.getWidth()/2);
							ry = (esbPool[i][OFFSET_OF_BE_Y] + SIZE_OF_ENEMY_DIRECTION_BULLET/2) - (ac.getY() + ac.getHeight()/2);
							break;
						case AppletConstant.IDX_QUADRANT_03:
							rx = -((ac.getX() + ac.getWidth()/2) - (esbPool[i][OFFSET_OF_BE_X] + SIZE_OF_ENEMY_DIRECTION_BULLET/2));
							ry = (esbPool[i][OFFSET_OF_BE_Y] + SIZE_OF_ENEMY_DIRECTION_BULLET/2) - (ac.getY() + ac.getHeight()/2);
							break;
						case AppletConstant.IDX_QUADRANT_04:
							rx = -((ac.getX() + ac.getWidth()/2) - (esbPool[i][OFFSET_OF_BE_X] + SIZE_OF_ENEMY_DIRECTION_BULLET/2));
							ry = -((ac.getY() + ac.getHeight()/2) - (esbPool[i][OFFSET_OF_BE_Y] + SIZE_OF_ENEMY_DIRECTION_BULLET/2));
							break;
						default:
							break;
					}
					ac.mgf.addGunFire(MachineGunFire.GUN_FIRE_TYPE_TB, rx, ry);
					setEmptyBullet(i);
				}
			}
		}
	}

	public void toPaint(Graphics g) {
		for (int i = 0; i < MAX_VOLUME_OF_ESB_POOL; i++) {
			if (!isEmptyBullet(i)) {
				g.drawImage(getImage(esbPool[i][OFFSET_OF_ESB_FS_IDX]),
					esbPool[i][OFFSET_OF_BE_X], esbPool[i][OFFSET_OF_BE_Y],
					AppletConstant.GTL);
			}
		}
	}
}