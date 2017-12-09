package Engine.MenuItems;

import Engine.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fps extends JMenuItem{

	static ActionListener listener=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			Engine.engine.setFps(((Fps) e.getSource()).fps);
		}
	};

	public final int fps;

	public Fps(int fps){
		this.fps=fps;
		setText(""+fps);
		Engine.engine.menu2.add(this);
		addActionListener(listener);
	}
}
