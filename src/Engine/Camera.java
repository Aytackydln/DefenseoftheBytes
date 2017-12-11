package Engine;

public class Camera{
	public double xPos, yPos;
	private double scaleX, scaleY;
	public double viewScale=1.0;
	public double viewLength;
	final Engine eng;
	public static Camera cam;

	public static int width, height;

	Camera(Engine eng,long xPos, long yPos,double viewLength){
		cam=this;
		this.viewLength=viewLength;
		this.eng=eng;
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
		if(width<height){
			viewScale=width/viewLength;
		}else {
			viewScale=height/viewLength;
		}
		if(eng.menuBar.isVisible()) scaleY=(height-eng.menuBar.getHeight()+Engine.topInset)/(viewScale);
		else scaleY=(height+Engine.topInset)/(viewScale);

		if(eng.menuBar.isVisible()) scaleX=(width+Engine.rightInset)/(viewScale);
		else scaleX=(width+Engine.rightInset)/(viewScale);

		if(Engine.debug)System.out.println("zoom: "+viewScale);
	}
}
