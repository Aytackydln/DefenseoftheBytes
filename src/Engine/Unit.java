package Engine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Unit{
	public double xPos,yPos;
	protected long SPEED=0;
	protected double xSpeed,ySpeed;
	protected double rotation;
	protected BufferedImage image;
	protected AffineTransform transform=new AffineTransform();

	public void tick(double delta){

	}

	public void render(Graphics g){

		transform.setToRotation(rotation, Engine.engine.scaleX(xPos),Engine.engine.scaleY(yPos));
		transform.translate(Engine.engine.scaleX(xPos-image.getWidth()/2),Engine.engine.scaleY(yPos-image.getHeight()/2));
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image,transform,null);
	}
}
