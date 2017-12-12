package Engine;

import java.awt.*;

public class Camera{
	public double xPos, yPos;
	public double viewScale=1.0;
	public double viewLength;
	public static Camera cam;
	private final Frame frame;

	public static int width, height;

	Camera(Frame frame,long xPos, long yPos, double viewLength){
		this.frame=frame;
		cam=this;
		this.viewLength=viewLength;
		this.xPos=xPos;
		this.yPos=yPos;
	}

	public void moveTo(double x, double y){
		this.xPos=x;
		this.yPos=y;
	}

	public void move(double x, double y){
		xPos+=x;
		yPos+=y;
	}

	public void chanceScale(double delta){
		viewLength+=delta;
		updateScales();
	}


	public double screenXScale(){
		return viewScale;
	}

	public double screenYScale(){
		return viewScale;
	}

	public int screenXSize(double a){
		return (int) (a*viewScale);
	}
	public int screenYSize(double a){
		return (int) (a*viewScale);
	}
	public int screenXPos(double a){
		return (int) ((a-xPos)*viewScale);
	}
	public int screenYPos(double a){
		return (int) ((a-yPos)*viewScale);
	}

	public void updateScales(){
		if(getFrameWidth()<getFrameHeight()){
			viewScale=getFrameWidth()/viewLength;
		}else{
			viewScale=getFrameHeight()/viewLength;
		}
		if(Engine.debug) System.out.println("Zoom is now: "+viewScale);
	}

	public int getFrameWidth(){
		return frame.getWidth();
	}
	public int getFrameHeight(){
		return frame.getHeight();
	}
}
