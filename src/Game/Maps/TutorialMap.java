package Game.Maps;

import Engine.Map;
import Game.Units.Monsters.Skeleton;

public class TutorialMap extends Map{
	public TutorialMap(){
		super(500,500);
		new Skeleton(this, 40,60);
	}
}
