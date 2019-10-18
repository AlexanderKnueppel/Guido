package de.tu.bs.guido.ui;

import org.eclipse.swt.widgets.Shell;

public class InitFileWindow {

	public static void main (String[] args) {
		Shell s = new Shell();
		s.setSize(500, 500);
		s.open();
		FileWindow fw = new FileWindow(s);
		fw.open();
		
	}
	
}
