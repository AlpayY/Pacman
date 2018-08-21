package com.alpayyildiray.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alpayyildiray.pacman.Pacman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("sun.java2d.opengl","True");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		config.width = 1280;
		config.height = 720;
		config.forceExit = true;
		new LwjglApplication(new Pacman(), config);
	}
}
