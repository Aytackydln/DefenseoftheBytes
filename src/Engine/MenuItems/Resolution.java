package Engine.MenuItems;

import Engine.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resolution extends JMenuItem{

	static ActionListener listener=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			Engine.engine.setFrame(((Resolution) e.getSource()).width,((Resolution) e.getSource()).height);
		}
	};

	public final int width;
	public final int height;

	public Resolution(int width, int height){
		this.width=width;
		this.height=height;
		setText(height+"*"+width);

		Engine.engine.resolutionObjs.add(this);

		addActionListener(listener);
	}
}
