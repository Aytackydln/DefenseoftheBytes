package Game.Units.Projectiles;

import Engine.Unit;
import Game.Units.Player;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Slash extends Projectile{
	double life;
	public Slash(Player owner, double time, double rotation){
		try{
			image=ImageIO.read(new File("slash.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		xPos=owner.xPos+Math.cos(rotation)*owner.size;
		yPos=owner.yPos+Math.sin(rotation)*owner.size;
		life=time;
		this.rotation=rotation;
		this.owner=owner;
	}

	@Override
	public void tick(double delta){
		life-=delta;
		if(life<0)kill();
	}
}
