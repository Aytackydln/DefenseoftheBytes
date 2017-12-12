package Engine;

import Game.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Unit{
	public Map map;
	public double xPos,yPos;
	public int size;
	protected long speed=0;
	protected double xSpeed,ySpeed;
	public double rotation;
	protected BufferedImage image;
	protected AffineTransform transform=new AffineTransform();
	public boolean collides=true;
	public boolean pierces=false;

	public void tick(double delta){
		xSpeed=Math.cos(rotation)*speed*delta;
		ySpeed=Math.sin(rotation)*speed*delta;
		map.moveUnit(this);
	}

	public void render(Graphics g){


		transform.setToScale(1,1);
		transform.setToRotation(rotation, Camera.cam.screenXPos(xPos),Camera.cam.screenYPos(yPos));
		transform.translate(Camera.cam.screenXPos(xPos-image.getWidth()/2),Camera.cam.screenYPos(yPos-image.getHeight()/2));
		transform.scale(Camera.cam.screenXScale(),Camera.cam.screenYScale());
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image,transform,null);
	}
	public Unit(Map map, double xPos, double yPos){
		this.map=map;
		map.unitsToAdd.add(this);
		this.xPos=xPos;
		this.yPos=yPos;
	}

	public void die(){
		map.unitsToRemove.add(this);
	}

	public void collide(Unit u){}

	public int getScreenXPos(){
		return Camera.cam.screenXPos(xPos);
	}
	public int getScreenYPos(){
		return Camera.cam.screenYPos(yPos);
	}
	public void onClick(){
		System.out.println("Clicked on "+getClass());
	}
	public boolean clickHit(){
		return Math.pow(getScreenXPos()-Engine.mouseX,2)+Math.pow(getScreenYPos()-Engine.mouseY,2)<=Math.pow(size,2);
	}
}
