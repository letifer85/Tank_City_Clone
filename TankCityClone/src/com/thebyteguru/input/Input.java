package com.thebyteguru.input;

import java.awt.event.ActionEvent;
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
				private int keyCode = Integer.parseInt(press) / 2;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					keyMap[keyCode] = true;
				}
			});
			getActionMap().put(release, new AbstractAction(release) {
				private int keyCode = (Integer.parseInt(release) - 1) / 2;

				@Override
				public void actionPerformed(ActionEvent arg0) {

					keyMap[keyCode] = false;
				}
			});
		}
	}

	public boolean getKey(int keyCode) {
		return keyMap[keyCode];
	}

}
