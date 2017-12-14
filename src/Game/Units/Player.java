package Game.Units;

import Engine.*;
import Game.Main;
import Game.Units.Projectiles.Arrow;
import Game.Units.Projectiles.Slash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Player extends Unit{
	double movementSpeed=75;
	double attackTime=1.3;
	double attackWait;

	public String name;


	public Player(Map map, int x, int y){
		super(map,x,y);
		Main.player=this;
		name=Main.playerName;

		size=50;

		try{
			image=ImageIO.read(new File("eyes.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void tick(double delta){
		if(Main.pressed.contains(KeyEvent.VK_S))ySpeed=movementSpeed*delta;
		else if(Main.pressed.contains(KeyEvent.VK_W))ySpeed=-movementSpeed*delta;
		else ySpeed=0;
		if(Main.pressed.contains(KeyEvent.VK_D))xSpeed=movementSpeed*delta;
		else if(Main.pressed.contains(KeyEvent.VK_A))xSpeed=-movementSpeed*delta;
		else xSpeed=0;
		map.moveUnit(this);


		if(Main.pressed.contains(KeyEvent.VK_SPACE)&&attackWait<=0){
			new Slash(this, 2, rotation);
			attackWait=attackTime;
		}

		if(Main.clicked&&attackWait<=0){
			new Arrow(map,this);
			attackWait=attackTime;
		}

		Main.engine.camera.chanePos(xPos-Camera.cam.getFrameWidth()/(2*Camera.cam.viewScale),yPos-Camera.cam.getFrameHeight()/(2*Camera.cam.viewScale));

		attackWait-=delta;
		rotation=Math.atan2(Main.engine.mouseY-getScreenYPos(),Main.engine.mouseX-getScreenXPos());
	}

	@Override
	public void render(Graphics g){
		super.render(g);

		g.drawOval(Camera.cam.screenXPos(xPos-size/2), Camera.cam.screenYPos(yPos-size/2), Camera.cam.screenXSize(size), Camera.cam.screenYSize(size));

		g.drawString(name, Camera.cam.screenXPos( xPos-name.length()*3.5),Camera.cam.screenYPos(yPos+size));
	}
}
