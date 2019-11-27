
package org.migame.airfighter.kernal.util;

import java.io.DataInputStream;

public class DataStorageHelper {

	private static DataStorageHelper instance = null;

	private final static String GAME_MAP_FOLDER_PATH         = "/resource/map/";
	private final static String GAME_MAP_FILE_COMMON_NAME    = "af_stage";
	private final static String GAME_MAP_FILE_EXTENSION_NAME = ".map";

	public static DataStorageHelper getInstance() {
		if (instance == null) {
			instance = new DataStorageHelper();
		}
		return instance;
	}

	public DataStorageHelper() {

	}

	public int readGameStageMap(int index, byte[] data) {
		DataInputStream dis = null;
		int result = 0;
		try {
			dis = new DataInputStream(getClass().getResourceAsStream(GAME_MAP_FOLDER_PATH + GAME_MAP_FILE_COMMON_NAME + String.valueOf(index) + GAME_MAP_FILE_EXTENSION_NAME));
			result = dis.read(data);
			dis.close();
		} catch (Exception e) {
			
		}
		return result;
	}
}