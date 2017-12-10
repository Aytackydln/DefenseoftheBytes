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
		int x2=Camera.cam.screenXSize(lengthX);
		int y2=Camera.cam.screenYSize(lengthY);
		g.drawRect(getScreenXPos(),getScreenYPos(),x2,y2);
	}

	@Override
	public boolean clickHit(){
		if(Engine.engine.mouseX>=Camera.cam.screenXPos(xPos)&&Engine.engine.mouseX<=Camera.cam.screenXPos(xPos+lengthX)
				&&Engine.engine.mouseY>=Camera.cam.screenYPos(yPos)&&(Engine.engine.mouseY<=Camera.cam.screenYPos(yPos+lengthY)))return true;
		else return false;
	}
}
