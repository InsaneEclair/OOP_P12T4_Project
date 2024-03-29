package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Google Chrome's Dino Game");
		config.setWindowedMode(810, 480);
		config.setForegroundFPS(60);
		config.setIdleFPS(60);
		new Lwjgl3Application(new GameEngine(), config);
	}
}
