package Tests;

import Engine.Map;
import Engine.Unit;

class MockUnit extends Unit{
	public MockUnit(Map map, double xPos, double yPos, long speed){
		super(map, xPos, yPos);
		this.speed=speed;
	}
}
