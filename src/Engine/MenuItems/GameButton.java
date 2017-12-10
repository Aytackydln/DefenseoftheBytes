package Engine.MenuItems;

import Engine.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GameButton extends JMenuItem{
	static ActionListener listener=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			((GameButton) e.getSource()).buttonAction();
		}
	};

	public GameButton(String text){
		setText(text);
		addActionListener(listener);
		Engine.engine.menu1.add(this);
	}
	public abstract void buttonAction();
}
