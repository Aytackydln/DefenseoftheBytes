package Game.Units;

import Engine.Map;
import Engine.Unit;

import java.util.ArrayList;

public class Projectile extends Unit{
	public Unit owner;
	public double life;
	public ArrayList<Unit> hits=new ArrayList<>();

	public Projectile(Map map,Unit owner, double xPos, double yPos){
		super(map,xPos,yPos);
		this.owner=owner;
		pierces=true;
	}

	@Override
	public void tick(double delta){
		life-=delta;
		if(life<0)die();
		map.moveUnit(this);
	}
	public void collide(Unit u){
		if(!hits.contains(u)){
			hits.add(u);{
				System.out.println("Projectile hit "+u.getClass());
			}
		}else return;
	}
}
