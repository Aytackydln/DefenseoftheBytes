package Engine;

public class Camera{
	public double xPos, yPos;
	private double scaleX, scaleY;
	private final int firstW, firstH;
	double viewScale=1;
	final Engine eng;
	public static Camera cam;

	Camera(Engine eng,long xPos, long yPos,int firstW, int firstH){
		cam=this;
		this.eng=eng;
		this.firstW=firstW;
		this.firstH=firstH;
		this.xPos=xPos;
		this.yPos=yPos;
	}

	public void moveTo(double x, double y){
		this.xPos=x;
		this.yPos=y;
		updateScales();
	}

	public void move(double x, double y){
		xPos+=x;
		yPos+=y;
		updateScales();
	}

	public void chanceScale(double delta){
		viewScale+=delta;
		updateScales();
	}


	public double screenXScale(){
		return scaleX;
	}

	public double screenYScale(){
		return scaleY;
	}

	public int screenXSize(double a){
		return (int) (a*scaleX);
	}
	public int screenYSize(double a){
		return (int) (a*scaleY);
	}
	public int screenXPos(double a){
		return (int) ((a-xPos)*scaleX);
	}
	public int screenYPos(double a){
		return (int) ((a-yPos)*scaleY);
	}

	public void updateScales(){
		if(eng.menuBar.isVisible()) scaleY=(Engine.height-eng.menuBar.getHeight()+Engine.topInset)/(firstH*viewScale);
		else scaleY=(Engine.height+Engine.topInset)/(firstH*viewScale);

		if(eng.menuBar.isVisible()) scaleX=(Engine.width+Engine.rightInset)/(firstW*viewScale);
		else scaleX=(Engine.width+Engine.rightInset)/(firstW*viewScale);
	}
}
