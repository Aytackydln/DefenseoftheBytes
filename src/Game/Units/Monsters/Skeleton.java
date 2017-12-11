package Game.Units.Monsters;

import Engine.*;
import Game.Main;
import Game.Units.MonsterBase;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Skeleton extends MonsterBase{
	public Skeleton(Map map,double xPos, double yPos){
		super(map,xPos,yPos);
		try{
			image=ImageIO.read(new File("skull.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		size=(image.getHeight()+image.getWidth())/2;
	}

	@Override
	public void tick(double delta){
		super.tick(delta);
		if(Main.player!=null)rotation=Math.atan2(Main.player.yPos-yPos,Main.player.xPos-xPos);
	}
}
