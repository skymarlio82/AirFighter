
package org.migame.airfighter.kernal.model.map;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.core.AirFighterEngine;
import org.migame.airfighter.kernal.util.DataStorageHelper;

public class GameBackgroundMap {

	public final static int MAP_TILE_SIZE = 16;

	public final static int MAP_IN_SCREEN_UNIT_WIDTH  = 11;
	public final static int MAP_IN_SCREEN_UNIT_HEIGHT = 12;
	public final static int MAP_IN_SCREEN_WIDTH  = MAP_IN_SCREEN_UNIT_WIDTH*MAP_TILE_SIZE;
	public final static int MAP_IN_SCREEN_HEIGHT = MAP_IN_SCREEN_UNIT_HEIGHT*MAP_TILE_SIZE;

	public final static int MAP_CACHE_UNIT_WIDTH  = 11;
	public final static int MAP_CACHE_UNIT_HEIGHT = 2*MAP_IN_SCREEN_UNIT_HEIGHT;
	public final static int MAP_CACHE_WIDTH  = MAP_CACHE_UNIT_WIDTH*MAP_TILE_SIZE;
	public final static int MAP_CACHE_HEIGHT = MAP_CACHE_UNIT_HEIGHT*MAP_TILE_SIZE;

	public final static int MAX_SIZE_OF_MAP_IDX_SET = 4*1024;

	// the object of game map
	private TiledLayer mapLayer = null;
	// the off-screen buffer
	private Image mapOffScrBuf  = null;
	// the pen of off-screen buffer
	private Graphics gm         = null;

	private byte[] map     = null;
	private int    lenMap  = 0;
	private int    mapStep = 0;

	private final static int MAP_UNIT_WIDTH = MAP_IN_SCREEN_UNIT_WIDTH;
	private final static int MAP_WIDTH      = MAP_IN_SCREEN_WIDTH;
	private int MAP_UNIT_HEIGHT = 0;
	private int MAP_HEIGHT      = 0;
	private int TOTAL_OF_MAP_STEP = 0;
	private int mapTopPosition    = 0;

	public GameBackgroundMap(Image imgMap) {
		mapLayer = new TiledLayer(MAP_CACHE_UNIT_WIDTH, MAP_CACHE_UNIT_HEIGHT, imgMap, MAP_TILE_SIZE, MAP_TILE_SIZE);
		mapOffScrBuf = Image.createImage(MAP_CACHE_WIDTH, MAP_CACHE_HEIGHT);
		gm = mapOffScrBuf.getGraphics();
		map = new byte[MAX_SIZE_OF_MAP_IDX_SET];
	}

	public void loadGameMapDataByStage(int stage) {
		lenMap = DataStorageHelper.getInstance().readGameStageMap(stage, map);
		MAP_UNIT_HEIGHT = lenMap/MAP_UNIT_WIDTH;
		MAP_HEIGHT = MAP_UNIT_HEIGHT*MAP_TILE_SIZE;
		TOTAL_OF_MAP_STEP = MAP_UNIT_HEIGHT/MAP_IN_SCREEN_UNIT_HEIGHT;
		mapStep = 0;
		rollGameMapStep();
	}

	private void rollGameMapStep() {
		int i = 0, j = 0, k = 0;
		k = lenMap - 1 - mapStep*(MAP_CACHE_UNIT_WIDTH*MAP_IN_SCREEN_UNIT_HEIGHT);
		for (i = MAP_CACHE_UNIT_HEIGHT - 1; i >= 0; i--) {
			for (j = MAP_CACHE_UNIT_WIDTH - 1; j >= 0; j--) {
				mapLayer.setCell(j, i, map[k]);
				k--;
			}
		}
		mapTopPosition = MAP_IN_SCREEN_HEIGHT;
		mapLayer.paint(gm);
		mapStep++;
	}

	public void runMapAction() {
		if (mapStep < TOTAL_OF_MAP_STEP - 1) {
			if (mapTopPosition == 0) {
				rollGameMapStep();
				return;
			}
			mapTopPosition--;
		}
	}

	public void paintVisualMapScreen(Graphics g) {
		g.drawRegion(mapOffScrBuf, 0, mapTopPosition, MAP_IN_SCREEN_WIDTH, MAP_IN_SCREEN_HEIGHT, Sprite.TRANS_NONE, AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_X, AirFighterEngine.SCR_BUF_2_VIEW_WINDOW_POS_Y, AppletConstant.GTL);
	}
}