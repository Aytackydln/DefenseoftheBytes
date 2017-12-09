package Game;

import Engine.*;
import Engine.MenuItems.Resolution;
import Game.Maps.TutorialMap;
import Game.Units.Player;

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
		player=new Player(25,25);
		map=new TutorialMap();
	}

	@Override
	protected void gameCodes(){
		for(Unit u:map.staticUnits)u.tick(delta);
		player.tick(delta);
	}

	@Override
	protected void reset(){

	}

	@Override
	protected void menuBar(){

	}

	@Override
	protected void actions(ActionEvent e){

	}

	@Override
	protected void draw(Graphics g){
		player.render(g);
	}

	@Override
	public void resolutions(){
		new Resolution(600,450);
	}
}
