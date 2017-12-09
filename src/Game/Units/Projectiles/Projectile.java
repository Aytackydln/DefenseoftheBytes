package Game.Units.Projectiles;

import Engine.Unit;
import Game.Units.Player;

public class Projectile extends Unit{
	public Player owner;

	public void kill(){
		owner.projectiles.remove(this);
	}
}
