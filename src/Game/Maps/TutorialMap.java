package Game.Maps;

import Engine.Map;
import Game.Units.Monsters.Skeleton;

public class TutorialMap extends Map{
	public TutorialMap(){
		staticUnits.add(new Skeleton());
	}
}
