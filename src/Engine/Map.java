package Engine;

import Engine.ShapedUnits.Rectangle;
import Game.Main;

import java.awt.*;
import java.util.ArrayList;

public class Map{
	public int width=250, height=250;
	public final ArrayList<Unit> staticUnits=new ArrayList<>();
	public final ArrayList<Unit> unitsToAdd=new ArrayList<>();
	public final ArrayList<Unit> units=new ArrayList<>();
	public final ArrayList<Unit> unitsToRemove=new ArrayList<>();
	public Map(int width, int height){
		this.width=width;
		this.height=height;
		staticUnits.add(new Rectangle(this,-width/2,-height/2,width,height));
		units.removeAll(staticUnits);
	}
	public void moveUnit(Unit u){
		double y=u.yPos+u.ySpeed;
		double x=u.xPos+u.xSpeed;

		//Boundary checks
		if(x>width/2-u.size/2)x=width/2-u.size/2;
		else if(x-u.size/2<-width/2)x=-width/2+u.size/2;

		if(y+u.size/2>height/2)y=height/2-u.size/2;
		else if(y-u.size/2<-height/2)y=-height/2+u.size/2;

		//Collision checks
		double distanceSq;
		if(u.collides)for(Unit other:units){
			if(other!=u&&other.collides){
				distanceSq=Math.pow(other.xPos-x, 2)+Math.pow(other.yPos-y, 2);
				if(distanceSq<Math.pow(other.size/2+u.size/2, 2)){
					u.collide(other);
					if(!(u.pierces||other.pierces)){
						double distancetoMove=Math.sqrt(distanceSq)-(u.size+other.size)/2;
						double angle=Math.atan2(y-other.yPos,x-other.xPos);
						x-=Math.cos(angle)*distancetoMove;
						y-=Math.sin(angle)*distancetoMove;
					}
				}
			}
		}

		u.xPos=x;
		u.yPos=y;
	}

	public void tick(double delta){
		units.addAll(unitsToAdd);
		unitsToAdd.clear();
		for(Unit u:units)u.tick(delta);
		units.removeAll(unitsToRemove);
		unitsToRemove.clear();
	}

	public void render(Graphics g){
		for(Unit u:staticUnits)u.render(g);
		try{
			for(Unit u:units)u.render(g);
		}catch(Exception e){
		}
	}
}
