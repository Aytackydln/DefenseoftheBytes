package Engine;

public class Camera{
	public double xPos, yPos;
	float viewScale=1f;

	Camera(long xPos, long yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}

	public void moveTo(double x, double y){
		this.xPos=x;
		this.yPos=y;
		Engine.engine.updateScales();
	}

	public void move(double x, double y){
		xPos+=x;
		yPos+=y;
		Engine.engine.updateScales();
	}

	public void chanceScale(float delta){
		viewScale+=delta;
		Engine.engine.updateScales();
	}
}
