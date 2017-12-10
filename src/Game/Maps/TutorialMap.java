package Game.Maps;

import Engine.Map;
import Game.Units.Monsters.Skeleton;
import Game.Units.Player;

public class TutorialMap extends Map{
	public TutorialMap(){
		super(500,500);
		new Skeleton(this, 40,60);
		new Player(this,25,25);
	}
}
