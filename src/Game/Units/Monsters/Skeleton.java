package Game.Units.Monsters;

import Engine.Engine;
import Game.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Skeleton extends MonsterBase{
	public Skeleton(){
		try{
			image=ImageIO.read(new File("skull.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void tick(double delta){
		rotation=Math.atan2(Main.main.player.yPos-yPos,Main.main.player.xPos-xPos);
	}
}
