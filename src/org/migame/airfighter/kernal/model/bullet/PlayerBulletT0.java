
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.craft.Aircraft;

public class PlayerBulletT0 extends Bullet {

	// the max volume of T0 Bullet
	public final static int MAX_NUMBER_OF_BULLET_T0 = 50;

	public final static int WIDTH_OF_BULLET_T0  = 3;
	public final static int HEIGHT_OF_BULLET_T0 = 35;

	// the container of Bullet T0
	private int[][] bulletT0Pool = null;

	// the image list of T0 Bullet
	public Image IMG_PLAYER_BULLET_TYPE_T0 = null;

	// the speed of T0 Bullet
	public final static int SPEED_BULLET_T0 = 20;

	public PlayerBulletT0(Image imgBT0) {
		IMG_PLAYER_BULLET_TYPE_T0 = imgBT0;
		bulletT0Pool = new int[MAX_NUMBER_OF_BULLET_T0][OFFSET_OF_BE_Y + 1];
	}

	public Image getImage() {
		return IMG_PLAYER_BULLET_TYPE_T0;
	}

	public void setEmptyBullet(int index) {
		super.setEmptyBullet(bulletT0Pool, index);
	}

	public boolean isEmptyBullet(int index) {
		return super.isEmptyBullet(bulletT0Pool, index);
	}

	public boolean isOutOfScreen(int index) {
		return super.isOutofScreen(bulletT0Pool, index);
	}

	public void launch(Aircraft ac) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T0; i++) {
			if (isEmptyBullet(i)) {
				bulletT0Pool[i][OFFSET_OF_BE_X] = ac.getX() + ac.getWidth()/2 - WIDTH_OF_BULLET_T0/2;
				bulletT0Pool[i][OFFSET_OF_BE_Y] = ac.getY();
				break;
			}
		}
	}

	public void runBullets() {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T0; i++) {
			if (!isEmptyBullet(i)) {
				bulletT0Pool[i][OFFSET_OF_BE_Y] -= SPEED_BULLET_T0;
				if (isOutOfScreen(i)) {
					setEmptyBullet(i);
				}
			}
		}
	}

	public void checkCollisionWithOthers(Aircraft ac) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T0; i++) {
			if (!isEmptyBullet(i)) {
				if (ac.collidesWith(getImage(),
						bulletT0Pool[i][Bullet.OFFSET_OF_BE_X],
						bulletT0Pool[i][Bullet.OFFSET_OF_BE_Y],
						false)) {
					ac.decreaseHP(2);
					ac.mgf.addGunFire(MachineGunFire.GUN_FIRE_TYPE_LB,
						bulletT0Pool[i][Bullet.OFFSET_OF_BE_X] - ac.getX() + WIDTH_OF_BULLET_T0/2,
						ac.getY() + ac.getHeight() - bulletT0Pool[i][Bullet.OFFSET_OF_BE_Y]);
					setEmptyBullet(i);
				}
			}
		}
	}

	public void toPaint(Graphics g) {
		for (int i = 0; i < MAX_NUMBER_OF_BULLET_T0; i++) {
			if (!isEmptyBullet(i)) {
				g.drawImage(getImage(),
					bulletT0Pool[i][OFFSET_OF_BE_X],
					bulletT0Pool[i][OFFSET_OF_BE_Y],
					AppletConstant.GTL);
			}
		}
	}
}