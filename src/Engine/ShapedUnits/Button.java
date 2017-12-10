package Engine.ShapedUnits;

import Engine.Map;

import java.awt.*;

public class Button extends Rectangle{
	String text;
	public Button(Map map, int startX, int startY, int lengthX, int lengthY, String text){
		super(map, startX, startY, lengthX, lengthY);
		this.text=text;
	}

	@Override
	public void render(Graphics g){
		super.render(g);
		g.drawString(text,getScreenXPos()+10,getScreenYPos()+30);
	}
}
