package Game.Units;

import Engine.Engine;
import Game.Main;
import Game.Units.Projectiles.Projectile;
import Game.Units.Projectiles.Slash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends UnitBase{
	public int size=50;
	double attackTime=1.3;
	double attackWait;
	public ArrayList<Projectile> projectiles=new ArrayList<>();
	public ArrayList<Projectile> projectileRemover=new ArrayList<>();


	public Player(int x, int y){
		xPos=x;
		yPos=y;
		xSpeed=50;
		ySpeed=50;


		try{
			image=ImageIO.read(new File("eyes.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void tick(double delta){
		if(Main.pressed.contains(KeyEvent.VK_S))yPos+=ySpeed*delta;
		if(Main.pressed.contains(KeyEvent.VK_W))yPos-=ySpeed*delta;
		if(Main.pressed.contains(KeyEvent.VK_D))xPos+=xSpeed*delta;
		if(Main.pressed.contains(KeyEvent.VK_A))xPos-=xSpeed*delta;
		if(Main.pressed.contains(KeyEvent.VK_SPACE)&&attackWait<=0){
			spawnProjectile(new Slash(this, 2, rotation));
			attackWait=attackTime;
		}
		Main.engine.camera.moveTo(xPos-300,yPos-150);

		attackWait-=delta;
		rotation=Math.atan2(Main.engine.mouseY-yPos+Main.engine.camera.yPos,Main.engine.mouseX-xPos+Main.engine.camera.xPos);
		for(Projectile p:projectiles)p.tick(delta);
		for(Projectile p:projectileRemover)projectiles.remove(p);
	}

	@Override
	public void render(Graphics g){
		transform.setToRotation(rotation,Engine.engine.scaleX(xPos),Engine.engine.scaleY(yPos));
		transform.translate(Engine.engine.scaleX(xPos-size/2), Engine.engine.scaleY(yPos-size/2));

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image,transform,null);
		g.drawOval(Engine.engine.scaleX(xPos-size/2), Engine.engine.scaleY(yPos-size/2), Engine.engine.scaleSizeX(size), Engine.engine.scaleSizeY(size));
		for(Projectile p:projectiles)p.render(g);
	}
	public void spawnProjectile(Projectile proj){
		projectiles.add(proj);
	}
}
