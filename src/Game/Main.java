package Game;

import Engine.*;
import Engine.MenuItems.Resolution;

import java.awt.event.ActionEvent;

public class Main extends Engine{
	public static void main(String[] args){
		new Main().run();
	}
	@Override
	protected void gameCodes(){

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
	public void resolutions(){
		new Resolution(200,100);
	}
}
