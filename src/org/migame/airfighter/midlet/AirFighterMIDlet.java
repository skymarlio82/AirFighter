
package org.migame.airfighter.midlet;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.migame.airfighter.kernal.core.AirFighterEngine;

public class AirFighterMIDlet extends MIDlet {

	private Display display = null;

	// ----------------------------------------------------------------------------------------------
	// the real game runtime entry which can make use of the resource to paint screen and play sound
	// ----------------------------------------------------------------------------------------------
	private AirFighterEngine gEngine = null;

	private Thread gThread = null;

	public AirFighterMIDlet() throws SecurityException {
		gEngine = new AirFighterEngine(this);
	}

	public void startApp() throws MIDletStateChangeException {
		if (display == null) {
			display = Display.getDisplay(this);
		}
		display.setCurrent(gEngine);
		// start the game thread
		gThread = new Thread(gEngine);
		gThread.start();
	}

	public void pauseApp() {
		//notifyPaused();
	}

	public void destroyApp(boolean unconditional) {
		//display.setCurrent((Displayable)null);
		display.setCurrent(null);
		gThread = null;
		//notifyDestroyed();
	}
}