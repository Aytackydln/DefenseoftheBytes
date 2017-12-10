package Game.Maps;

import Engine.Camera;
import Engine.Map;
import Engine.ShapedUnits.Button;
import Game.Main;

public class MainMenu extends Map{
	public MainMenu(){
		super(300, 400);
		new Button(this,-100,-140,200,120,"Load Tutorial Map"){
			@Override
			public void onClick(){
				Main.main.loadMap(new TutorialMap());
			}
		};
		new Button(this,-100,-10,200,120,"print NOTHING"){
			@Override
			public void onClick(){
				System.out.println("NOTHING");
			}
		};

		Camera.cam.moveTo(-height/2-10,-width/2-10);
	}
}
