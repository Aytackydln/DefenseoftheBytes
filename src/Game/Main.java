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

	//variables to load from settings.txt
	public String playerName;

	public static void main(String[] args){
		new Main().run();
	}

	Main(){
		variables.add("playerName");
		if(playerName==null)playerName="-";
		main=this;
		map=new TutorialMap();
		player=new Player(map,25,25);
		player.name=playerName;
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
				String s=JOptionPane.showInputDialog("name?",playerName);
				if(s!=null&&s.length()>0){
					player.name=s;
					playerName=s;
				}
			}
		};
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
