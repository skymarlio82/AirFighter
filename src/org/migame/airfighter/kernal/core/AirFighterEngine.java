
package org.migame.airfighter.kernal.core;

import java.io.InputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;

import org.migame.airfighter.kernal.common.AppletConstant;
import org.migame.airfighter.kernal.model.bullet.EnemyStraight01Bullet;
import org.migame.airfighter.kernal.model.bullet.PlayerTraceMissileBullet;
import org.migame.airfighter.kernal.model.craft.AbstractEnemyAircraft;
import org.migame.airfighter.kernal.model.craft.Aircraft;
import org.migame.airfighter.kernal.model.craft.Enemy01Aircraft;
import org.migame.airfighter.kernal.model.craft.EnemyBossAircraft;
import org.migame.airfighter.kernal.model.craft.PlayerAircraft;
import org.migame.airfighter.kernal.model.map.GameBackgroundMap;

public class AirFighterEngine extends GameCanvas implements Runnable {

	// -----------------------------------------
	// all the constants of the game state list
	// -----------------------------------------
	private final static int AF_GAME_STATE_START        = 0x00;
	private final static int AF_GAME_STATE_DISPLAY_INFO = 0x01;
	private final static int AF_GAME_STATE_RUNNING      = 0x02;
	private final static int AF_GAME_STATE_PAUSE        = 0x03;
	private final static int AF_GAME_STATE_OVER         = 0x04;

	// -----------------
	// logo screen size
	// -----------------
	private final static int LOGO_TITLE_SCREEN_WIDTH  = 160;
	private final static int LOGO_TITLE_SCREEN_HEIGHT = 160;

	// -----------------------------------------
	// logo title string set's initial position
	// -----------------------------------------
	private final static int LT_STRING_SET_BEGIN_Y = 96;
	// 'pause' string initial position
	private final static int PS_STRING_SET_BEGIN_Y = 40;

	// ---------------------------------------------------------------------
	// ScreenBuffer2: It should be much larger than ScreenBuffer1 of device
	// ---------------------------------------------------------------------
	public final static int SCR_BUF_2_VIEW_WINDOW_POS_X = 50;
	public final static int SCR_BUF_2_VIEW_WINDOW_POS_Y = 50;
	private final static int SCR_BUF_2_EXT_WIDTH          = 50;
	private final static int SCR_BUF_2_EXT_HEIGHT         = 50;
	private final static int SCR_BUF_2_VIEW_WINDOW_WIDTH  = GameBackgroundMap.MAP_IN_SCREEN_WIDTH + 2*SCR_BUF_2_EXT_WIDTH;
	private final static int SCR_BUF_2_VIEW_WINDOW_HEIGHT = GameBackgroundMap.MAP_IN_SCREEN_HEIGHT + 2*SCR_BUF_2_EXT_HEIGHT;

	// -------------------------
	// Menu - Game Start Screen
	// -------------------------
	private final static int NUM_OF_MENU_ITEM_START_SCREEN   = 3;
	private final static int MENU_ITEM_START_GAME_START      = 0;
	private final static int MENU_ITEM_START_GAME_EXIT       = 1;
	private final static int MENU_ITEM_START_GAME_ABOUT_HELP = 2;
	// the string set of GSS
	private final static String[] START_SCREEN_STRING_SET = { "Start Game", "Exit", "About & Help" };

	// -------------------------
	// Menu - Game Pause Screen
	// -------------------------
	private final static int NUM_OF_MENU_ITEM_PAUSE_SCREEN = 3;
	private final static int MENU_ITEM_PAUSE_RESTART_GAME  = 0;
	private final static int MENU_ITEM_PAUSE_GAME_EXIT     = 1;
	private final static int MENU_ITEM_PAUSE_GAME_CONTINUE = 2;
	// the string set of GPS
	private final static String[] PAUSE_SCREEN_STRING_SET = { "Restart Game", "Exit", "Continue" };

	// -----------------------------------------------
	// the game global variables for overview control
	// -----------------------------------------------
	// game system inspect and control
	private MIDlet midlet = null;
	// the 2nd level screen buffer in order to refine the screen refresh
	private Image screenBuffer2 = null;
	private Graphics gScreenBuf2 = null;
	// The Game State
	private int gameState = 0;
	// Used to mark the current selected item
	private int currentSelectedItem = 0;
	// Used to mark the user's state for pressing down on the menu
	private boolean itemSelectedState = false;
	// used to sign whether the fire button is pressed
	private boolean FIRE_STATE_PRESSED = false;
	private boolean BOOM_BUTTON_PRESS_STATE = false;
	// used to scale the progress of game
	private int timeLine = 0;

	// ---------------------------------------
	// the global variables for game's screen
	// ---------------------------------------
	// the size of ME's screen (full screen mode, non stretched)
	private int ME_FSM_SCREEN_WIDTH  = 0;
	private int ME_FSM_SCREEN_HEIGHT = 0;
	// the position of game screen
	public static int INIT_GS_POS_X = 0;
	public static int INIT_GS_POS_Y = 0;
	// the position of LOGO Title in the cell phone screen
	private int INIT_LT_POS_X = 0;
	private int INIT_LT_POS_Y = 0;

	// -------------------
	// the image resource
	// -------------------
	// the image of game logo
	private static Image imgLogoTitle = null;
	// the image of all the titles about background
	private static Image imgBGTileSet = null;

	/* all the images of the Aircraft */
	// the image of F-35 sprite (player)
	private static Image imgF35           = null;
	// the image of Russion-33 sprite (enemy)
	private static Image imgRS33          = null;
	// the image of F-16 sprite (enemy)
	private static Image imgF16           = null;
	// the image of Shadow 2000 (enemy)
	private static Image imgShadowX       = null;
	// the image of Apatch-64 Sprite (enemy)
	private static Image imgApache64      = null;
	private static Image imgApache64Screw = null;

	/* all the images of the Bullet */
	// the image of F-16's bullet
	private static Image imgF16Bullet        = null;
	// the image of Russion-33's bullet
	private static Image imgRS33Bullet       = null;
	// the image of Apache-64's bullet
	private static Image imgApache64Bullet   = null;
	// the image of Player's Bullet-T0
	private static Image imgPlayerBulletT0   = null;
	// the image of Player's Bullet-T1
	private static Image imgPlayerBulletT1   = null;
	// the image of Player's Bullet-T2
	private static Image[] imgPlayerBulletT2 = null;

	/* all the images of Gun Fire, Boom and Smog */
	// the image of Air Craft Blast Sprite (player & enemy)
    private static Image imgACB                       = null;
	// the image of Track Missile Smog Sprite (player)
	private static Image imgTmSmog                    = null;
	// the image of Boom Sprite (player)
	private static Image[] imgBoomBlast               = null;
	// the image of Linear Bullet's gun fire Sprite (enemy)
	private static Image[] imgEnemyGunFireLBAttacked  = null;
	// the image of Trace Bullet's gun fire Sprite (enemy)
	private static Image[] imgEnemyGunFireTBAttacked  = null;
	// the image of Trace Bullet's gun fire Sprite (player)
	private static Image[] imgPlayerGunFireAttacked   = null;
	// the image of Straight Bullet Sprite (enemy)
    private static Image imgEB01                      = null;

    // ------------------------
    // the objects of Aircraft
    // ------------------------
    private static PlayerAircraft playerAircraft = null;

	// -------------------------------------
	// the container of all the enemy entry
	// -------------------------------------
	private Vector enemyPool = null;

	// ---------------------------------------------
	// all the objects of enemy's weapon and bullet
	// ---------------------------------------------
	// the object of ELB (Enemy Linear Bullet)'s pool
	private EnemyStraight01Bullet esb01 = null;

	// -----------------------
    // the object of Game map
    // -----------------------
	private GameBackgroundMap gamemap = null;

	// --------------------------------------
	// the object of main theme music player
	// --------------------------------------
	private Player musicPlayer = null;

	public AirFighterEngine(MIDlet midlet) {
		// To suppress the regular key event, only to query the event of GameCanvas
		super(true);
		this.midlet = midlet;
		setFullScreenMode(true);
		// set the screen dimention
		ME_FSM_SCREEN_WIDTH = getWidth();
		ME_FSM_SCREEN_HEIGHT = getHeight();
		// check the screen size of native machine
		if (ME_FSM_SCREEN_WIDTH < GameBackgroundMap.MAP_IN_SCREEN_WIDTH ||
			ME_FSM_SCREEN_HEIGHT < GameBackgroundMap.MAP_IN_SCREEN_HEIGHT) {
			postErrorToExit(null, "Screen size too small! The game can't be executed.");
		}
		// acquire the game screen coordinate
		INIT_GS_POS_X = (ME_FSM_SCREEN_WIDTH - GameBackgroundMap.MAP_IN_SCREEN_WIDTH)/2;
		INIT_GS_POS_Y = (ME_FSM_SCREEN_HEIGHT - GameBackgroundMap.MAP_IN_SCREEN_HEIGHT)/2;
		// acquire the Logo title coordinate
		INIT_LT_POS_X = (ME_FSM_SCREEN_WIDTH - LOGO_TITLE_SCREEN_WIDTH)/2;
		INIT_LT_POS_Y = (ME_FSM_SCREEN_HEIGHT - LOGO_TITLE_SCREEN_HEIGHT)/2;
		// load the relevant game resource (Image, sound and Music) 
		loadResource();
		// init all the important objects in the game
		initAllObjects();
		// set the game state to START.
		gameState = AF_GAME_STATE_START;
	}

	/* Exit the system and print the exception message */
	private void postErrorToExit(Exception e, String reason) {
		System.out.println(reason);
		if (e != null) {
			e.printStackTrace();
		}
		midlet.notifyDestroyed();
	}

	/* the geme kernal need to load the resource (Image, Sound, ...) which be necessary */
	private void loadResource() {
		int i = 0;
		try {
			imgLogoTitle = Image.createImage("/resource/image/Logo_Title.png");
			imgBGTileSet = Image.createImage("/resource/image/BG_TileSet.png");
			imgF35 = Image.createImage("/resource/image/SP_F35.png");
			imgRS33 = Image.createImage("/resource/image/SP_RS33.png");
			imgF16 = Image.createImage("/resource/image/SP_F16.png");
			imgShadowX = Image.createImage("/resource/image/SP_ShadowX.png");
			imgApache64 = Image.createImage("/resource/image/SP_Apache64.png");
			imgApache64Screw = Image.createImage("/resource/image/SP_Apache64_Screw.png");
			imgPlayerBulletT0 = Image.createImage("/resource/image/IMG_Player_Bullet_T0.png");
			imgPlayerBulletT1 = Image.createImage("/resource/image/IMG_Player_Bullet_T1.png");
			// devide the source image of Track missile and re-organize them into a new sequence
			// need to modify: re-design the image source
			Image imgTmSf16 = Image.createImage("/resource/image/SP_Player_Trace_Missile.png");
			imgPlayerBulletT2 = new Image[PlayerTraceMissileBullet.FN_OF_TRACE_MISSILE];
			for (i = 0; i < PlayerTraceMissileBullet.FN_OF_TRACE_MISSILE; i++) {
				imgPlayerBulletT2[i] = Image.createImage(imgTmSf16,
					(i%2)*PlayerTraceMissileBullet.SIZE_OF_TM, (i/2)*PlayerTraceMissileBullet.SIZE_OF_TM,
					PlayerTraceMissileBullet.SIZE_OF_TM, PlayerTraceMissileBullet.SIZE_OF_TM,
					Sprite.TRANS_NONE);
			}
			imgF16Bullet = Image.createImage("/resource/image/SP_Enemy_Bullet_1.png");
			imgRS33Bullet = Image.createImage("/resource/image/IMG_Enemy_Bullet_3.png");
			imgApache64Bullet = Image.createImage("/resource/image/SP_Enemy_Bullet_1.png");
			imgACB = Image.createImage("/resource/image/SP_Aircraft_Blast_1.png");
			// seperate the big image into the small images
			Image imgGFELBA = Image.createImage("/resource/image/SP_GunFire_Enemy_LB_Attacked.png");
			imgEnemyGunFireLBAttacked = new Image[AppletConstant.ENEMY_LB_GF_ATTACKED_FSN];
			for (i = 0; i < AppletConstant.ENEMY_LB_GF_ATTACKED_FSN; i++) {
				imgEnemyGunFireLBAttacked[i] = Image.createImage(imgGFELBA,
					i*(imgGFELBA.getWidth()/AppletConstant.ENEMY_LB_GF_ATTACKED_FSN), 0,
					imgGFELBA.getWidth()/AppletConstant.ENEMY_LB_GF_ATTACKED_FSN, imgGFELBA.getHeight(),
					Sprite.TRANS_NONE);
			}
			// seperate the big image into the small images
			Image imgGFETBA = Image.createImage("/resource/image/SP_GunFire_Enemy_TB_Attacked.png");
			imgEnemyGunFireTBAttacked = new Image[AppletConstant.ENEMY_TB_GF_ATTACKED_FSN];
			for (i = 0; i < AppletConstant.ENEMY_TB_GF_ATTACKED_FSN; i++) {
				imgEnemyGunFireTBAttacked[i] = Image.createImage(imgGFETBA, 
					i*(imgGFETBA.getWidth()/AppletConstant.ENEMY_TB_GF_ATTACKED_FSN), 0,
					imgGFETBA.getWidth()/AppletConstant.ENEMY_TB_GF_ATTACKED_FSN, imgGFETBA.getHeight(),
					Sprite.TRANS_NONE);
			}
			// seperate the big image into the small images
			Image imgGFPA = Image.createImage("/resource/image/SP_GunFire_Player_Attacked.png");
			imgPlayerGunFireAttacked = new Image[AppletConstant.PLAYER_BULLET_GF_FSN];
			for (i = 0; i < AppletConstant.PLAYER_BULLET_GF_FSN; i++) {
				imgPlayerGunFireAttacked[i] = Image.createImage(imgGFPA,
					(i%4)*(imgGFPA.getWidth()/4), (i/4)*(imgGFPA.getHeight()/2),
					imgGFPA.getWidth()/4, imgGFPA.getHeight()/2,
					Sprite.TRANS_NONE);
			}
			// seperate the big image into the small images
			Image imgBoom = Image.createImage("/resource/image/SP_Aircraft_Blast_2.png");
			imgBoomBlast = new Image[PlayerAircraft.FSN_BOOM_BLAST];
			for (i = 0; i < PlayerAircraft.FSN_BOOM_BLAST; i++) {
				imgBoomBlast[i] = Image.createImage(imgBoom,
					(i%2)*PlayerAircraft.BOOM_BLAST_WIDTH, (i/2)*PlayerAircraft.BOOM_BLAST_HEIGHT,
					PlayerAircraft.BOOM_BLAST_WIDTH, PlayerAircraft.BOOM_BLAST_HEIGHT,
					Sprite.TRANS_NONE);
			}
			imgTmSmog = Image.createImage("/resource/image/SP_TraceMissile_Smog.png");
			imgEB01 = Image.createImage("/resource/image/SP_Enemy_Bullet_1.png");
			// load the background music of game
			// need to modify: re-design the music source (midi)
			InputStream is = getClass().getResourceAsStream("/resource/sound/MainTheme.wav");
			musicPlayer = Manager.createPlayer(is, "audio/x-wav");
			musicPlayer.setLoopCount(-1);
			musicPlayer.realize();
		} catch (Exception e) {
			if (e instanceof IOException || e instanceof MediaException) {
				postErrorToExit(e, "Loading resource, failed!");
			}
		}
	}

	private void initAllObjects() {
		// create the instance of ScreenBuffer2
		screenBuffer2 = Image.createImage(GameBackgroundMap.MAP_IN_SCREEN_WIDTH + 2*SCR_BUF_2_EXT_WIDTH,
			GameBackgroundMap.MAP_IN_SCREEN_HEIGHT + 2*SCR_BUF_2_EXT_HEIGHT);
		gScreenBuf2 = screenBuffer2.getGraphics();
		// instantiate the game map
		gamemap = new GameBackgroundMap(imgBGTileSet);
		// instantiate the containter of enemy
		enemyPool = new Vector();
		playerAircraft = new PlayerAircraft(imgF35,
			(SCR_BUF_2_VIEW_WINDOW_WIDTH - PlayerAircraft.PLAYER_AIRCRAFT_WIDTH)/2,
			(SCR_BUF_2_VIEW_WINDOW_HEIGHT - PlayerAircraft.PLAYER_AIRCRAFT_HEIGHT)/2,
			imgPlayerGunFireAttacked,
			imgACB,
			enemyPool,
			imgTmSmog,
			imgBoomBlast,
			imgPlayerBulletT0,
			imgPlayerBulletT1,
			imgPlayerBulletT2);
		playerAircraft.setHp(100);
		FIRE_STATE_PRESSED = true;
		esb01 = new EnemyStraight01Bullet(imgEB01);
	}

	private void postforStateExchange() {
		clearScreen();
		itemSelectedState = false;
		currentSelectedItem = 0;
	}

	/* dispatch the game procedure */
	private void processAllTransaction() {
		switch (gameState) {
			case AF_GAME_STATE_START:
				if (itemSelectedState == false) {
					return;
				}
				switch (currentSelectedItem) {
					case MENU_ITEM_START_GAME_START:
						// game transition: 'GAME START' -> 'GAME RUNNING'
						gameState = AF_GAME_STATE_RUNNING;
						gamemap.loadGameMapDataByStage(1);
						try {
							musicPlayer.stop();
						} catch (Exception e) {
							postErrorToExit(e, "Error of music player occurs!");
						}
						break;
					case MENU_ITEM_START_GAME_EXIT:
						// exit the game and the applet
						midlet.notifyDestroyed();
						break;
					case MENU_ITEM_START_GAME_ABOUT_HELP:
						// game transition: 'GAME START' -> 'GAME DISPLAY INFO'
						gameState = AF_GAME_STATE_DISPLAY_INFO;
						break;
					default:
						break;
				}
				postforStateExchange();
				break;
			case AF_GAME_STATE_DISPLAY_INFO:
				if (itemSelectedState == true) {
					gameState = AF_GAME_STATE_START;
					postforStateExchange();
				}
				break;
			case AF_GAME_STATE_RUNNING:
				// game map action
				gamemap.runMapAction();
				// the action of player's weapon
				if (FIRE_STATE_PRESSED) {
					playerAircraft.fire();
				}
				playerAircraft.runWeapon();
				playerAircraft.mgf.runGunFire();
				if (BOOM_BUTTON_PRESS_STATE) {
					if (!playerAircraft.isBoomBlastOver()) {
						playerAircraft.runBoomBlast();
					} else {
						BOOM_BUTTON_PRESS_STATE = false;
					}
				}
				processGameProgress();
				Enumeration enu = enemyPool.elements();
				while (enu.hasMoreElements()) {
					AbstractEnemyAircraft ec = (AbstractEnemyAircraft)enu.nextElement();
					if (ec.getHp() <= 0) {
						if (ec.isBlastOver()) {
							enemyPool.removeElement(ec);
							playerAircraft.pbT2.clearTMBuffer();
							continue;
						} else {
							ec.runBlast();
						}
					} else {
						ec.runAI();
					}
					ec.mgf.runGunFire();
				}
				esb01.runBullets();
				// increment all the validated game running frames
				timeLine++;
				break;
			case AF_GAME_STATE_PAUSE:
				if (itemSelectedState == true) {
					switch (currentSelectedItem) {
						case MENU_ITEM_PAUSE_RESTART_GAME:
							// game transition: 'GAME PAUSE' -> 'GAME START'
							gameState = AF_GAME_STATE_START;
							timeLine = 0;
							break;
						case MENU_ITEM_PAUSE_GAME_EXIT:
							// exit the game and the applet
							midlet.notifyDestroyed();
						case MENU_ITEM_PAUSE_GAME_CONTINUE:
							// game transition: 'GAME PAUSE' -> 'GAME RUNNING'
							gameState = AF_GAME_STATE_RUNNING;
							try {
								musicPlayer.stop();
							} catch (Exception ex) {
								postErrorToExit(ex, "Error of music player occurs!");
							}
							break;
						default:
							break;
					}
					postforStateExchange();
				}
				break;
			default:
				break;
		}
	}

	private void selectMenuItems(int KeyState, int numItem) {
		int temp = 0;
		if ((KeyState & UP_PRESSED) != 0) {
			temp = currentSelectedItem - 1;
			currentSelectedItem = (temp < 0) ? (temp + numItem) : temp;
		} else if ((KeyState & DOWN_PRESSED) != 0) {
			temp = currentSelectedItem + 1;
			currentSelectedItem = (temp >= numItem) ? (temp - numItem) : temp;
		} else if ((KeyState & FIRE_PRESSED) != 0) {
			itemSelectedState = true;
		}
	}

	private void processUserInput() {
		int keyStates = getKeyStates(), temp = 0;
		Enemy01Aircraft afe = null;
		Random rdm = new Random();
		int i = 0;
		switch (gameState) {
			case AF_GAME_STATE_START:
				selectMenuItems(keyStates, NUM_OF_MENU_ITEM_START_SCREEN);
				break;
			case AF_GAME_STATE_DISPLAY_INFO:
				if ((keyStates&FIRE_PRESSED) != 0) {
					itemSelectedState = true;
				}
				break;
			case AF_GAME_STATE_RUNNING:
				playerAircraft.setFrame(PlayerAircraft.SEQ_IDX_FRAME_NORMAL);
				if ((keyStates&GAME_A_PRESSED) != 0) {
					BOOM_BUTTON_PRESS_STATE = true;
					Enumeration enu = enemyPool.elements();
					while (enu.hasMoreElements()) {
						AbstractEnemyAircraft ec = (AbstractEnemyAircraft)enu.nextElement();
						ec.setHp(0);
					}
				} else if ((keyStates&GAME_B_PRESSED) != 0) {
					gameState = AF_GAME_STATE_PAUSE;
					try {
						musicPlayer.start();
					} catch (Exception e) {
						postErrorToExit(e, "Error of music player occurs!");
					}
				} else if ((keyStates&GAME_C_PRESSED) != 0) {
					afe = new Enemy01Aircraft(imgRS33,
						SCR_BUF_2_VIEW_WINDOW_POS_X + Math.abs(rdm.nextInt()%(GameBackgroundMap.MAP_IN_SCREEN_WIDTH - Enemy01Aircraft.ENEMY_01_AIRCRAFT_WIDTH)),
						SCR_BUF_2_VIEW_WINDOW_POS_Y + 1,
						imgEnemyGunFireLBAttacked,
						imgEnemyGunFireTBAttacked,
						imgACB,
						esb01,
						enemyPool,
						playerAircraft);
					afe.setHp(3);
					enemyPool.addElement(afe);
				} else if ((keyStates&UP_PRESSED) != 0) {
					if (playerAircraft.getY() - PlayerAircraft.PLAYER_AIRCRAFT_SPEED >= SCR_BUF_2_VIEW_WINDOW_POS_Y) {
						playerAircraft.move(0, -PlayerAircraft.PLAYER_AIRCRAFT_SPEED);
					}
				} else if ((keyStates&DOWN_PRESSED) != 0) {
					if (playerAircraft.getY() + PlayerAircraft.PLAYER_AIRCRAFT_HEIGHT + PlayerAircraft.PLAYER_AIRCRAFT_SPEED
						<= SCR_BUF_2_VIEW_WINDOW_POS_Y + GameBackgroundMap.MAP_IN_SCREEN_HEIGHT) {
						playerAircraft.move(0, PlayerAircraft.PLAYER_AIRCRAFT_SPEED);
					}
				} else if ((keyStates&LEFT_PRESSED) != 0) {
					playerAircraft.setFrame(PlayerAircraft.SEQ_IDX_FRAME_LEFT);
					if (playerAircraft.getX() - PlayerAircraft.PLAYER_AIRCRAFT_SPEED >= SCR_BUF_2_VIEW_WINDOW_POS_X) {
						playerAircraft.move(-PlayerAircraft.PLAYER_AIRCRAFT_SPEED, 0);
					}
				} else if ((keyStates&RIGHT_PRESSED) != 0) {
					playerAircraft.setFrame(PlayerAircraft.SEQ_IDX_FRAME_RIGHT);
					if (playerAircraft.getX() + PlayerAircraft.PLAYER_AIRCRAFT_WIDTH + PlayerAircraft.PLAYER_AIRCRAFT_SPEED
						<= SCR_BUF_2_VIEW_WINDOW_POS_X + GameBackgroundMap.MAP_IN_SCREEN_WIDTH) {
						playerAircraft.move(PlayerAircraft.PLAYER_AIRCRAFT_SPEED, 0);
					}
				} else if ((keyStates & FIRE_PRESSED) != 0) {
					FIRE_STATE_PRESSED = (!FIRE_STATE_PRESSED);
				}
				break;
			case AF_GAME_STATE_PAUSE:
				selectMenuItems(keyStates, NUM_OF_MENU_ITEM_PAUSE_SCREEN);
				break;
		}
	}

	private void processGameProgress() {
		Enemy01Aircraft afe = null;
		EnemyBossAircraft afeb = null;
		Random rdm = new Random();
		int i = 0;
		switch (timeLine) {
			case 100: case 200: case 300: case 400: case 550: case 600: case 700: case 800: case 900: case 1000: 
			case 1100: case 1200: case 1300: case 1400: case 1500: case 1600: case 1700: case 1800: case 1900: 
				for (i = 0; i < 3; i++) {
					afe = new Enemy01Aircraft(imgRS33,
						SCR_BUF_2_VIEW_WINDOW_POS_X + Math.abs(rdm.nextInt()%(GameBackgroundMap.MAP_IN_SCREEN_WIDTH - Enemy01Aircraft.ENEMY_01_AIRCRAFT_WIDTH)),
						SCR_BUF_2_VIEW_WINDOW_POS_Y + 1,
						imgEnemyGunFireLBAttacked,
						imgEnemyGunFireTBAttacked,
						imgACB,
						esb01,
						enemyPool,
						playerAircraft);
					afe.setHp(3);
					enemyPool.addElement(afe);
				}
				break;
			case 2000:
				afeb = new EnemyBossAircraft(imgShadowX,
					(SCR_BUF_2_VIEW_WINDOW_WIDTH - EnemyBossAircraft.ENEMY_BOSS_AIRCRAFT_WIDTH)/2,
					SCR_BUF_2_VIEW_WINDOW_HEIGHT - 190,
					imgEnemyGunFireLBAttacked,
					imgEnemyGunFireTBAttacked,
					imgACB,
					esb01,
					enemyPool,
					playerAircraft);
				afeb.setHp(100);
				enemyPool.addElement(afeb);
				break;
			default:
				break;
		}
	}

	private void checkCollision() {
		Enumeration enu = enemyPool.elements();
		while (enu.hasMoreElements()) {
			Aircraft ac = (Aircraft)enu.nextElement();
			if (playerAircraft.collidesWith(ac, false)) {
				ac.decreaseHP(5);
				playerAircraft.decreaseHP(1);
			}
			playerAircraft.pbT0.checkCollisionWithOthers(ac);
			playerAircraft.pbT1.checkCollisionWithOthers(ac);
			playerAircraft.pbT2.checkCollisionWithOthers(ac);
		}
		if (!BOOM_BUTTON_PRESS_STATE) {
			esb01.checkCollisionWithOthers(playerAircraft);
		}
	}

    /* The main loop and flow of game */
	public void run() {
		long curMTime = 0, preMTime = 0;
		// Obtains the Graphics object for rendering a GameCanvas.
		Graphics g = getGraphics();
		try {
			clearScreen();
			musicPlayer.start();
			while (true) {
				curMTime = System.currentTimeMillis();
				if (curMTime - preMTime > AppletConstant.FPS_30_MTIME_INTERVAL) {
					preMTime = curMTime;
					checkCollision();
					processAllTransaction();
					processUserInput();
					paintGameScreen(g);
					flushGraphics();
					serviceRepaints();
				}
				if (gameState != AF_GAME_STATE_RUNNING) {
					Thread.sleep(AppletConstant.TIME_INTERVAL_OF_SELECT_ITEM);
				}
			}
		} catch (Exception e) {
			postErrorToExit(e, "The execusition of system error!");
		}
	}

	// refresh and clear the screen into 'black'
	private void clearScreen() {
		// Obtains the Graphics object for rendering a GameCanvas.
		Graphics g = getGraphics();
		g.setColor(0x000000);
		g.fillRect(0, 0, ME_FSM_SCREEN_WIDTH, ME_FSM_SCREEN_HEIGHT);
		serviceRepaints();
	}

	private void paintSelectItems(Graphics g, int numItem, String[] strset, int initFramePosY, int initStringPosY) {
		int temp = 0;
		for (int i = 0; i < numItem; i++) {
			g.setColor(0xFFFFFF);
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
			if (i == currentSelectedItem) {
				g.setColor(0xFF7f00);
				g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			}
			g.drawString(strset[i], ME_FSM_SCREEN_WIDTH/2, initFramePosY + initStringPosY + temp, AppletConstant.GTH);
			temp += AppletConstant.INTERVAL_SIZE_BETWEEN_STRINGS;
		}
	}

	private void paintGameScreen(Graphics g) {
		int temp = 0, i = 0;
		switch (gameState) {
			case AF_GAME_STATE_START:
				g.setColor(0xFFFFFF);
				// add the border of logo screen
				g.drawRect(INIT_LT_POS_X - 1, INIT_LT_POS_Y - 1,
					LOGO_TITLE_SCREEN_WIDTH + 2, LOGO_TITLE_SCREEN_HEIGHT + 2);
				// show the logo form and let the user select menu
				g.drawImage(imgLogoTitle, INIT_LT_POS_X, INIT_LT_POS_Y, AppletConstant.GTL);
				/* show the menu items which be selected in the logo Sign the current item line */
				paintSelectItems(g,
					NUM_OF_MENU_ITEM_START_SCREEN,
					START_SCREEN_STRING_SET,
					INIT_LT_POS_Y,
					LT_STRING_SET_BEGIN_Y);
				break;
			case AF_GAME_STATE_DISPLAY_INFO:
				g.setColor(0x00FFFF);
				// add the border for displaying information
				g.drawRect(INIT_GS_POS_X - 1, INIT_GS_POS_Y - 1,
					GameBackgroundMap.MAP_IN_SCREEN_WIDTH + 2, GameBackgroundMap.MAP_IN_SCREEN_HEIGHT + 2);
				/* display the information of copyright, author and key setting */
				g.setColor(0xFFFFFF);
				g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
				g.drawString(AppletConstant.STR_SET_HELP_ABOUT_INFO[0],
					ME_FSM_SCREEN_WIDTH/2, INIT_GS_POS_Y + 2,
					AppletConstant.GTH);
				for (i = 1; i < AppletConstant.STR_SET_HELP_ABOUT_INFO.length - 1; i++) {
					temp += AppletConstant.INTERVAL_SIZE_BETWEEN_STRINGS;
					g.drawString(AppletConstant.STR_SET_HELP_ABOUT_INFO[i],
						INIT_GS_POS_X + 2, INIT_GS_POS_Y + temp,
						AppletConstant.GTL);
				}
				g.setColor(0xC06030);
				g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
				g.drawString(AppletConstant.STR_SET_HELP_ABOUT_INFO[i],
					ME_FSM_SCREEN_WIDTH/2, INIT_GS_POS_Y + temp + AppletConstant.INTERVAL_SIZE_BETWEEN_STRINGS + 5,
					AppletConstant.GTH);
				break;
			case AF_GAME_STATE_PAUSE:
				clearScreen();
				g.setColor(0xFFFFFF);
				g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
				g.drawString("- PAUSE -",
					ME_FSM_SCREEN_WIDTH/2,
					INIT_LT_POS_Y + PS_STRING_SET_BEGIN_Y,
					AppletConstant.GTH);
				paintSelectItems(g,
					NUM_OF_MENU_ITEM_PAUSE_SCREEN,
					PAUSE_SCREEN_STRING_SET,
					INIT_GS_POS_Y,
					PS_STRING_SET_BEGIN_Y + 40);
				break;
			case AF_GAME_STATE_RUNNING:
				gamemap.paintVisualMapScreen(gScreenBuf2);
				Enumeration enu = enemyPool.elements();
				while (enu.hasMoreElements()) {
					Aircraft c = (Aircraft)enu.nextElement();
					if (c.getHp() <= 0) {
						if (!c.isBlastOver()) {
							c.toPaintBlast(gScreenBuf2);
						}
					} else {
						c.paint(gScreenBuf2);
						c.mgf.toPaint(gScreenBuf2);
					}
				}
				esb01.toPaint(gScreenBuf2);
				playerAircraft.paint(gScreenBuf2);
				playerAircraft.toPaintWeapon(gScreenBuf2);
				playerAircraft.mgf.toPaint(gScreenBuf2);
				if (BOOM_BUTTON_PRESS_STATE) {
					playerAircraft.toPaintBoomBlast(gScreenBuf2);
				}
				// Integer a = new Integer((int)(1000/t));
				gScreenBuf2.setColor(0xFFFFFF);
				g.drawRegion(screenBuffer2,
					SCR_BUF_2_VIEW_WINDOW_POS_X,
					SCR_BUF_2_VIEW_WINDOW_POS_Y,
					GameBackgroundMap.MAP_IN_SCREEN_WIDTH,
					GameBackgroundMap.MAP_IN_SCREEN_HEIGHT,
					Sprite.TRANS_NONE,
					INIT_GS_POS_X,
					INIT_GS_POS_Y,
					Graphics.TOP|Graphics.LEFT);
				break;
			default:
				break;
		}
	}
}