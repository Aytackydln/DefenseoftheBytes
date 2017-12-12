package Engine;

import javax.swing.*;
import java.awt.event.*;

public class ComboListener implements KeyListener, ActionListener, ItemListener, MouseListener, MouseMotionListener{
	final Engine engine;

	ComboListener(Engine engine){
		this.engine=engine;

	}

	@Override
	public void keyPressed(KeyEvent e){
		Integer a=e.getKeyCode();
		engine.pressed.add(a);
		if(a=='K'){
			if(!engine.menuBar.isVisible()){
				engine.menuBar.setVisible(true);
				Camera.cam.updateScales();
			}
		}
		else if(a=='L'){
			if(engine.menuBar.isVisible()){
				engine.menuBar.setVisible(false);
				Camera.cam.updateScales();
			}
		}else if(a==KeyEvent.VK_NUMPAD6) engine.camera.move(2, 0);
		else if(a==KeyEvent.VK_NUMPAD4) engine.camera.move(-2, 0);
		else if(a==KeyEvent.VK_NUMPAD8) engine.camera.move(0, -2);
		else if(a==KeyEvent.VK_NUMPAD2) engine.camera.move(0, 2);
		else if(a==KeyEvent.VK_NUMPAD9) engine.camera.chanceScale(5);
		else if(a==KeyEvent.VK_NUMPAD3) engine.camera.chanceScale(-5);
	}

	@Override
	public void keyReleased(KeyEvent e){
		engine.pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem) (e.getSource());
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if((e.getSource())==engine.m21){
			System.out.println("show stats");
			engine.showStats=!engine.showStats;
		}else if((e.getSource())==engine.m11){
			engine.reset();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){
		engine.clicked=true;
		for(Unit u:engine.map.units){
			if(u.clickHit())u.onClick();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){
		engine.clicked=true;
		engine.mouseX=e.getX();
		engine.mouseY=e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e){
		engine.mouseX=e.getX();
		engine.mouseY=e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
}
