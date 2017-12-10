package Game.Maps;

import Engine.Map;
import Game.Units.Monsters.Skeleton;

public class TutorialMap extends Map{
	public TutorialMap(){
		new Skeleton(this, 40,60);
	}
}
