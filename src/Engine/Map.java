package Engine;

import Engine.ShapedUnits.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Map{
	int width=250, height=250;
	public final ArrayList<Unit> staticUnits=new ArrayList<>();
	public final ArrayList<Unit> unitsToAdd=new ArrayList<>();
	public final ArrayList<Unit> units=new ArrayList<>();
	public final ArrayList<Unit> unitsToRemove=new ArrayList<>();
	public Map(){
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
		double distanceSqrt;
		if(u.collides)for(Unit other:units){
			if(other!=u&&other.collides){
				distanceSqrt=Math.pow(other.xPos-u.xPos, 2)+Math.pow(other.yPos-u.yPos, 2);
				if(distanceSqrt<Math.pow(other.size/2+u.size/2, 2)){
					u.collide(other);
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
