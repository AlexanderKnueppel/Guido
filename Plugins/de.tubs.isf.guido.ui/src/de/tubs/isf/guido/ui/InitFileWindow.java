package de.tubs.isf.guido.ui;

import org.eclipse.swt.widgets.Shell;

public class InitFileWindow {

	public static void main(String[] args) {
		Shell s = new Shell();
		s.setSize(500, 500);
		s.open();
		ProjectSettings.setSettings(SettingsControl.getSettings());
		FileWindow fw = new FileWindow(s);
		fw.open();

	}

}