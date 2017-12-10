package Engine.ShapedUnits;

import Engine.*;

import java.awt.*;

public class Rectangle extends Unit{
	int lengthX,lengthY;

	public Rectangle(Map map, int startX, int startY, int lengthX, int lengthY){
		super(map,startX,startY);
		this.lengthX=lengthX;
		this.lengthY=lengthY;
	}

	@Override
	public void render(Graphics g){
		int x1=Camera.cam.screenXPos(xPos);
		int y1=Camera.cam.screenYPos(yPos);
		int x2=Camera.cam.screenXSize(lengthX);
		int y2=Camera.cam.screenYSize(lengthY);
		g.drawRect(x1,y1,x2,y2);
	}
}
