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
		xSpeed=Math.cos(owner.rotation)*5;
		ySpeed=Math.sin(owner.rotation)*5;
		rotation=owner.rotation;
	}

	@Override
	public void collide(Unit u){
		if(u!=owner&&!hits.contains(u)&&!(u instanceof Arrow)){
			xSpeed=0;
			ySpeed=0;
			life=0.4;
			hits.add(u);
		}
	}
}
