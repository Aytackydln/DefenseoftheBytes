package Game.MenuButtons;

import Engine.Map;
import Engine.ShapedUnits.Rectangle;
import Game.Main;
import Game.Maps.TutorialMap;

public class Tutorial extends Rectangle{
	public Tutorial(Map map, int startX, int startY, int lengthX, int lengthY){
		super(map, startX, startY, lengthX, lengthY);
	}

	@Override
	public void onClick(){
		Main.main.loadMap(new TutorialMap());
	}
}
