package Game;

import Engine.*;
import Engine.MenuItems.GameButton;
import Engine.MenuItems.Resolution;
import Game.Maps.TutorialMap;
import Game.Units.Player;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends Engine{
	public Player player;
	public static Main main;

	public static void main(String[] args){
		new Main().run();
	}

	Main(){
		main=this;
		map=new TutorialMap();
		player=new Player(map,25,25);
	}

	@Override
	protected void gameCodes(){
		for(Unit u:map.staticUnits)u.tick(delta);
		player.tick(delta);
		map.tick(delta);
	}

	@Override
	protected void reset(){

	}

	@Override
	protected void menuBar(){
		new GameButton("Set name:"){
			@Override
			public void buttonAction(){
				player.name=JOptionPane.showInputDialog("name?");
			}
		};
	}

	@Override
	protected void actions(ActionEvent e){

	}

	@Override
	protected void draw(Graphics g){
		map.render(g);
		player.render(g);
	}

	@Override
	public void resolutions(){
		new Resolution(600,450);
		new Resolution(800,600);
	}
}
