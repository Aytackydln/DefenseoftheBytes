package Game.Units.Projectiles;

import Engine.Unit;
import Game.Units.Player;
import Game.Units.Projectile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Slash extends Projectile{
	public Slash(Player owner, double time, double rotation){
		super(owner.map,owner,owner.xPos+Math.cos(rotation)*owner.size,owner.yPos+Math.sin(rotation)*owner.size);
		try{
			image=ImageIO.read(new File("slash.png"));
		}catch(IOException e){
			e.printStackTrace();
		}

		size=(image.getHeight()+image.getWidth())/2;
		life=time;
		this.rotation=rotation;
		this.owner=owner;
	}

	@Override
	public void collide(Unit u){
		super.collide(u);
	}
}
