package Game.Units.Projectiles;

import Engine.Map;
import Engine.Unit;
import Game.Units.Projectile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Arrow extends Projectile{
	public Arrow(Map map, Unit owner){
		super(map,owner,owner.xPos,owner.yPos);

		try{
			image=ImageIO.read(new File("arrow.png"));
		}catch(IOException e){
			e.printStackTrace();
		}

		size=(image.getHeight()+image.getWidth())/2;
		life=3;
		rotation=owner.rotation;
		speed=300;
	}

	@Override
	public void collide(Unit u){
		if(u!=owner&&!hits.contains(u)&&!(u instanceof Arrow)){
			speed=0;
			life=0.9;
			hits.add(u);
		}
	}

	@Override
	public void tick(double delta){
		super.tick(delta);

		xSpeed=Math.cos(rotation)*speed*delta;
		ySpeed=Math.sin(rotation)*speed*delta;
	}
}
