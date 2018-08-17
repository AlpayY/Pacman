package com.alpayyildiray.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alpayyildiray.pacman.Pacman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("sun.java2d.opengl","True");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Pacman(), config);
	}
}
