
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.craft.Aircraft;

public class PlayerBulletT1 extends Bullet {

	// the max volume of T1 Bullet
	public final static int MAX_NUMBER_OF_BULLET_T1 = 50;

	public final static int WIDTH_OF_BULLET_T1  = 7;
	public final static int HEIGHT_OF_BULLET_T1 = 35;

	// the container of T1 Bullet
	public int[][] bulletT1Pool = null;

	// the image list of T1 Bullet
	public Image IMG_PLAYER_BULLET_TYPE_T1 = null;

	// the speed of T1 Bullet
	public final static int SPEED_BULLET_T1 = 20;

	public PlayerBulletT1(Image imgBT1) {
		IMG_PLAYER_BULLET_TYPE_T1 = imgBT1;
		bulletT1Pool = new int[MAX_NUMBER_OF_BULLET_T1][OFFSET_OF_BE_Y + 1];
	}

	public Image getImage() {
		return IMG_PLAYER_BULLET_TYPE_T1;
	}

	public void setEmptyBullet(int index) {
		super.setEmptyBullet(bulletT1Pool, index);
	}

	public boolean isEmptyBullet(int index) {
		return super.isEmptyBullet(bulletT1Pool, index);
	}

	public boolean isOutOfScreen(int index) {
		return super.isOutofScreen(bulletT1Pool, index);
	}

	public void launch(Aircraft ac) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T1; i++) {
			if (isEmptyBullet(i)) {
				bulletT1Pool[i][OFFSET_OF_BE_X] = ac.getX() + ac.getWidth()/2 - WIDTH_OF_BULLET_T1/2;
				bulletT1Pool[i][OFFSET_OF_BE_Y] = ac.getY();
				break;
			}
		}
	}

	public void runBullets() {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T1; i++) {
			if (!isEmptyBullet(i)) {
				bulletT1Pool[i][OFFSET_OF_BE_Y] -= SPEED_BULLET_T1;
				if (isOutOfScreen(i)) {
					setEmptyBullet(i);
				}
			}
		}
	}

	public void checkCollisionWithOthers(Aircraft ac) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T1; i++) {
			if (!isEmptyBullet(i)) {
				if (ac.collidesWith(getImage(),
					bulletT1Pool[i][Bullet.OFFSET_OF_BE_X],
					bulletT1Pool[i][Bullet.OFFSET_OF_BE_Y],
					true)) {
					ac.decreaseHP(2);
					ac.mgf.addGunFire(MachineGunFire.GUN_FIRE_TYPE_LB,
						bulletT1Pool[i][Bullet.OFFSET_OF_BE_X] - ac.getX() + WIDTH_OF_BULLET_T1/2,
						ac.getY() + ac.getHeight() - bulletT1Pool[i][Bullet.OFFSET_OF_BE_Y]);
					setEmptyBullet(i);
				}
			}
		}
	}

	public void toPaint(Graphics g) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T1; i++) {
			if (!isEmptyBullet(i)) {
				g.drawImage(getImage(),
					bulletT1Pool[i][OFFSET_OF_BE_X],
					bulletT1Pool[i][OFFSET_OF_BE_Y],
					AppletConstant.GTL);
			}
		}
	}
}