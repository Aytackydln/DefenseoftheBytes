package Engine.MenuItems;

import Engine.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ups extends JMenuItem{
	static ActionListener listener=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			Engine.engine.setUps(((Ups) e.getSource()).ups);
		}
	};

	public final int ups;

	public Ups(int ups){
		this.ups=ups;
		setText(""+ups);
		Engine.engine.menu2.add(this);
		addActionListener(listener);
	}
}
