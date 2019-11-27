
package org.migame.airfighter.kernal.model.bullet;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.craft.Aircraft;

public class MachineGunFire {

	public final static int MAX_NUMBER_OF_GF_POOL = 5;

	public final static int OFFSET_GF_R_POS_X = 0;
	public final static int OFFSET_GF_R_POS_Y = 1;
	public final static int OFFSET_GF_TYPE    = 2;
	public final static int OFFSET_GF_IDX     = 3;

	// the types of gun fire (Linear Bullet, Trace Bullet, and ...)
	public final static int MAX_GUN_FIRE_TYPE = 2;
	public final static int GUN_FIRE_TYPE_LB  = 1;
	public final static int GUN_FIRE_TYPE_TB  = 2;

	private int[][] gunFirePool = null;

	private Aircraft aircraft = null;

	private Image[] IMG_LINEAR_BULLET = null;
	private Image[] IMG_TRACE_BULLET  = null;

	private int GF_FSN_LINEAR_BULLET = 0;
	private int GF_FSN_TRACE_BULLET  = 0;

	private int LINEAR_BULLET_WIDTH  = 0;
	private int LINEAR_BULLET_HEIGHT = 0;

	private int TRACE_BULLET_WIDTH  = 0;
	private int TRACE_BULLET_HEIGHT = 0;

	public MachineGunFire(Image[] imgTB, int widthTB, int heightTB, Aircraft aircraft) {
		IMG_TRACE_BULLET = imgTB;
		TRACE_BULLET_WIDTH = widthTB;
		TRACE_BULLET_HEIGHT = heightTB;
		GF_FSN_TRACE_BULLET = imgTB.length;
		this.aircraft = aircraft;
		gunFirePool = new int[MAX_NUMBER_OF_GF_POOL][OFFSET_GF_IDX + 1];
	}

	public MachineGunFire(Image[] imgLB, int widthLB, int heightLB, Image[] imgTB, int widthTB, int heightTB, Aircraft aircraft) {
		this(imgTB, widthTB, heightTB, aircraft);
		IMG_LINEAR_BULLET = imgLB;
		LINEAR_BULLET_WIDTH = widthLB;
		LINEAR_BULLET_HEIGHT = heightLB;
		GF_FSN_LINEAR_BULLET = imgLB.length;
	}

	public void reset(int index) {
		gunFirePool[index][OFFSET_GF_R_POS_X] = 0;
		gunFirePool[index][OFFSET_GF_R_POS_Y] = 0;
		gunFirePool[index][OFFSET_GF_TYPE] = 0;
		gunFirePool[index][OFFSET_GF_IDX] = 0;
	}

	public boolean isEmpty(int index) {
		if (gunFirePool[index][OFFSET_GF_R_POS_X] == 0 && gunFirePool[index][OFFSET_GF_R_POS_Y] == 0 && gunFirePool[index][OFFSET_GF_TYPE] == 0 && gunFirePool[index][OFFSET_GF_IDX] == 0) {
			return true;
		}
		return false;
	}

	public void addGunFire(int gtype, int rx, int ry) {
		for (int i = 0; i < MAX_NUMBER_OF_GF_POOL; i++) {
			if (isEmpty(i)) {
				gunFirePool[i][OFFSET_GF_R_POS_X] = rx;
				gunFirePool[i][OFFSET_GF_R_POS_Y] = ry;
				gunFirePool[i][OFFSET_GF_TYPE] = gtype;
				gunFirePool[i][OFFSET_GF_IDX] = 0;
				break;
			}
		}
	}

	public void runGunFire() {
		for (int i = 0; i < MAX_NUMBER_OF_GF_POOL; i++) {
			if (!isEmpty(i)) {
				int fsn = (gunFirePool[i][OFFSET_GF_TYPE] == GUN_FIRE_TYPE_LB) ? GF_FSN_LINEAR_BULLET : GF_FSN_TRACE_BULLET;
				gunFirePool[i][OFFSET_GF_IDX]++;
				if (gunFirePool[i][OFFSET_GF_IDX] >= fsn) {
					reset(i);
				}
			}
		}
	}

	public void toPaint(Graphics g) {
		for (int i = 0; i < MAX_NUMBER_OF_GF_POOL; i++) {
			if (!isEmpty(i)) {
				if (gunFirePool[i][OFFSET_GF_TYPE] == GUN_FIRE_TYPE_TB) {
					g.drawImage(IMG_TRACE_BULLET[gunFirePool[i][OFFSET_GF_IDX]], aircraft.getX() + aircraft.getWidth()/2 + gunFirePool[i][OFFSET_GF_R_POS_X] - TRACE_BULLET_WIDTH/2, aircraft.getY() + aircraft.getHeight()/2 + gunFirePool[i][OFFSET_GF_R_POS_Y] - TRACE_BULLET_HEIGHT/2, AppletConstant.GTL);
				} else {
					g.drawImage(IMG_LINEAR_BULLET[gunFirePool[i][OFFSET_GF_IDX]], aircraft.getX() + gunFirePool[i][OFFSET_GF_R_POS_X] - LINEAR_BULLET_WIDTH/2, aircraft.getY() + gunFirePool[i][OFFSET_GF_R_POS_Y] - LINEAR_BULLET_HEIGHT, AppletConstant.GTL);
				}
			}
		}
	}
}