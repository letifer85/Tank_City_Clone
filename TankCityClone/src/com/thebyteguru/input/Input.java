package com.thebyteguru.input;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Input extends JComponent {

	private boolean[] keyMap;

	public Input() {
		keyMap = new boolean[256];
		initKeys();
	}

	private void initKeys() {
				for (int i = 0; i < keyMap.length; i++) {
					String press = "" + (i * 2);
					String release = "" + (i * 2 + 1);
					getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), press);
					getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), release);
					getActionMap().put(press, new AbstractAction(press) {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int index = Integer.parseInt(press) / 2;
							keyMap[index] = true;
						}
					});
					getActionMap().put(release, new AbstractAction(release) {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int index = (Integer.parseInt(release) - 1) / 2;
							keyMap[index] = false;
						}
					});
				}
	}

	public boolean getKey(int keyCode) {
		return keyMap[keyCode];
	}

}
