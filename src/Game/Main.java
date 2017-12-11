package Game;

import Engine.*;
import Engine.MenuItems.GameButton;
import Engine.MenuItems.Resolution;
import Game.Maps.MainMenu;
import Game.Units.Player;

import javax.swing.*;
import java.awt.*;

public class Main extends Engine{
	public static Player player;
	public static Main main;

	//variables to load from settings.txt
	public static String playerName;

	public static void main(String[] args){
		new Main().run();
	}

	public Main(){
		variables.add("playerName");
		if(playerName==null)playerName="Nameless";
		main=this;
		map=new MainMenu();
	}

	@Override
	protected void gameCodes(){
		for(Unit u:map.staticUnits)u.tick(delta);
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
	}

	@Override
	public void resolutions(){
		new Resolution(600,450);
		new Resolution(800,600);
	}
}
