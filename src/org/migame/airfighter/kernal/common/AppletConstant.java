
package org.migame.airfighter.kernal.common;

import javax.microedition.lcdui.Graphics;

import net.jscience.math.kvm.MathFP;

public class AppletConstant {

	public final static int FX_180  = MathFP.toFP(180);	
	public final static int PR00_FP = MathFP.div(MathFP.mul(MathFP.toFP(0),  MathFP.PI), FX_180);
	public final static int PR11_FP = MathFP.div(MathFP.mul(MathFP.toFP(11), MathFP.PI), FX_180);
	public final static int PR15_FP = MathFP.div(MathFP.mul(MathFP.toFP(15), MathFP.PI), FX_180);
	public final static int PR22_FP = MathFP.div(MathFP.mul(MathFP.toFP(22), MathFP.PI), FX_180);
	public final static int PR30_FP = MathFP.div(MathFP.mul(MathFP.toFP(30), MathFP.PI), FX_180);
	public final static int PR33_FP = MathFP.div(MathFP.mul(MathFP.toFP(33), MathFP.PI), FX_180);
	public final static int PR45_FP = MathFP.div(MathFP.mul(MathFP.toFP(45), MathFP.PI), FX_180);
	public final static int PR56_FP = MathFP.div(MathFP.mul(MathFP.toFP(56), MathFP.PI), FX_180);
	public final static int PR60_FP = MathFP.div(MathFP.mul(MathFP.toFP(60), MathFP.PI), FX_180);
	public final static int PR67_FP = MathFP.div(MathFP.mul(MathFP.toFP(67), MathFP.PI), FX_180);
	public final static int PR75_FP = MathFP.div(MathFP.mul(MathFP.toFP(75), MathFP.PI), FX_180);
	public final static int PR78_FP = MathFP.div(MathFP.mul(MathFP.toFP(78), MathFP.PI), FX_180);
	public final static int PR90_FP = MathFP.div(MathFP.mul(MathFP.toFP(90), MathFP.PI), FX_180);

	public final static int IDX_QUADRANT_01 = 1;
	public final static int IDX_QUADRANT_02 = 2;
	public final static int IDX_QUADRANT_03 = 3;
	public final static int IDX_QUADRANT_04 = 4;

	public final static int GTH = Graphics.TOP | Graphics.HCENTER;
	public final static int GTL = Graphics.TOP | Graphics.LEFT;

	public final static int FPS_60_MTIME_INTERVAL = 16;
	public final static int FPS_30_MTIME_INTERVAL = 33;
	public final static int FPS_15_MTIME_INTERVAL = 66;

	public final static int TIME_INTERVAL_OF_SELECT_ITEM = 160;
	public final static int INTERVAL_SIZE_BETWEEN_STRINGS = 15;

	public final static int FILE_TYPE_MAP = 0;

	public final static int ENEMY_LB_GF_ATTACKED_FSN = 8;
	public final static int ENEMY_TB_GF_ATTACKED_FSN = 4;
	public final static int PLAYER_BULLET_GF_FSN     = 8;

	public final static String[] STR_SET_HELP_ABOUT_INFO = {
		"About Game", " Created by Marlio Sky", " Copyright(R) Wind-dancing", " Key setting list:", " UP - Up Arrow", " DOWN - Down Arrow", " LEFT - Left Arrow", " RIGHT - Right Arrow", " FIRE - Fire Button", " BOOM - A Button", " PAUSE - B Button", "Press FIRE to continue ..."
	};
}