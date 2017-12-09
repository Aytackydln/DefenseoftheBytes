package Engine;
/*0.1.1

 */

import Engine.MenuItems.Fps;
import Engine.MenuItems.Resolution;
import Engine.MenuItems.Ups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public abstract class Engine extends JPanel implements KeyListener, ActionListener, ItemListener, MouseMotionListener{
	protected static boolean run=true;
	protected boolean showStats=true;
	public static Engine engine;
	protected final JFrame frame;

	public final static Set<Integer> pressed=new TreeSet<>();
	protected static ArrayList<String> variables=new ArrayList<>();
	public int mouseX, mouseY;

	private final int firstW, firstH;
	public static int width, height;
	public int fps, ups;
	private double scaleX, scaleY;
	private int topInset, rightInset;

	private static int gameHertz=128;
	static private int target_fps=64;
	long tımeBetweenUpdates=1000000000/gameHertz;
	long targetTımeBetweenRenders=1000000000/target_fps;
	static final double maxDelta=0.02091;    //as seconds
	public static double delta=0;    		//as seconds
	static int sleepTime=0;    				//as miliseconds, dynamically change depending on code intensity
	private int frameCount, updateCount;

	private JMenuBar menuBar;
	protected JMenu menu1; //Game
	public JMenu menu2;	//Engine
	private JMenuItem m11; //reset
	private JMenuItem m21; //Show stats
	public ArrayList<Resolution> resolutionObjs=new ArrayList<>();

	public static Random rng=new Random();
	private ArrayList<Text> texts=new ArrayList<>();

	protected Map map=new Map();
	public Camera camera=new Camera(0, 0);

	public Engine(){
		System.setProperty("sun.java2d.opengl", "true");

		engine=this;
		variables.addAll(new ArrayList<>(Arrays.asList("fps", "ups")));

		frame=new JFrame("Football");
		frame.setSize(width+5, height+30);
		frame.setLocation(200, 20);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				run=false;
				System.exit(0);
			}
		});
		frame.setResizable(false);
		JPanel contPane=new JPanel(new BorderLayout());

		//determine sizes of window borders
		frame.pack();
		topInset=frame.getInsets().top+frame.getInsets().bottom;
		rightInset=frame.getInsets().right+frame.getInsets().left;

		resolutions();  //abstract
		if(resolutionObjs.size()==0){
			System.out.println("no resolutions specified!");
			firstW=20;
			firstH=20;
		}
		else{
			firstW=resolutionObjs.get(0).width;
			firstH=resolutionObjs.get(0).height;
		}
		System.out.println("first Width: "+firstW+" first height: "+firstH);

		frame.setJMenuBar(menuBarimiz());
		frame.setContentPane(contPane);
		menuBar.setVisible(false);

		setFrame(firstW,firstH);

		setLayout(null);
		setSize(width, height);
		setLocation(0, 0);
		addKeyListener(this);
		addMouseMotionListener(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();
	}

	private static double scaleX(){
		return engine.scaleX;
	}

	private void readSett(){
		System.out.println("reading settings");
		try{
			List<String> readSettings=Files.readAllLines(Paths.get("settings.txt"));
			String[] change;
			for(String s : readSettings){
				change=s.split("=");
				String var=change[0];
				String val=change[1];
				System.out.println("set "+var+" to "+val);
				try{
					this.getClass().getDeclaredField(var).set(this, val);
				}catch(Throwable e){
					try{
						this.getClass().getField(var).get(this);
					}catch(Throwable e1){
						System.out.println("Could not set saved variable ("+var+")");
					}
				}
			}
		}catch(IOException e){
			try{
				new Formatter("settings.txt");
				System.out.println("settings.txt created");
			}catch(FileNotFoundException ignored){
				System.out.println("Could not create settings.txt");
			}
		}
	}

	public void saveConf() throws IllegalAccessException{
		String s="";
		for(String a:variables)
			try{
				s+=a+"="+this.getClass().getDeclaredField(a).get(this)+"\n";
			}catch(Exception e){
				try{
					s+=a+"="+this.getClass().getField(a).get(this)+"\n";
				}catch(Exception e1){
					System.out.println("There was an error saving "+a+e1);
					e1.printStackTrace();
				}
			}
		Formatter f=null;
		try{
			f=new Formatter(new File("settings.txt"));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		f.format(s);
		f.close();
		System.out.println("saved configs");
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(showStats){
			g.drawString("FPS: "+fps+"    UPS:"+ups, 5, 10);
		}
		for(Text t : texts) t.render(g);
		for(Unit u:map.staticUnits)u.render(g);
		draw(g);
	}

	public void run(){
		readSett();

		long lastUpdateTime=System.nanoTime();
		long previousUpdateTime;
		long lastRenderTime=0;

		frame.add(this);
		frame.setVisible(true);

		new TimedEvent(500){
			@Override
			public void run(){
				while(run){
					super.run();
					ups=updateCount*2;
					updateCount=0;
					fps=frameCount*2;
					frameCount=0;
					getGraphics().dispose();
				}
			}
		}.start(); //fps and ups updater
		// Game loop
		while(run){
			if(System.nanoTime()-lastUpdateTime>=tımeBetweenUpdates){
				if(delta>maxDelta){
					System.out.println("latency detected "+delta);
					delta=maxDelta;
				}
				previousUpdateTime=lastUpdateTime;
				lastUpdateTime=System.nanoTime();
				gameCodes();
				//delta=(System.nanoTime()-(double) previousUpdateTime)/1000000000;
				updateCount++;
				if(System.nanoTime()-lastRenderTime>=targetTımeBetweenRenders){
					lastRenderTime=lastUpdateTime;
					frame.repaint();
					frameCount++;
				}
				delta=(System.nanoTime()-(double) previousUpdateTime)/1000000000;

				//sleepTime=(int)tımeBetweenUpdates/1000000;
				sleepTime=(int) (tımeBetweenUpdates*2/1000000-delta*1000);
				if(sleepTime>20) sleepTime=19;
				try{
					Thread.sleep(sleepTime, 0);
				}catch(Exception ignored){
				}
			}
		}
		try{
			saveConf();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
	}

	public static void main(){
		System.out.println("you must ivoke run() from implemented class");
	}

	private JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		menu1=new JMenu("Game");
		menu2=new JMenu("Engine");

		m11=new JMenuItem("Reset");
		m11.addActionListener(this);
		m21=new JMenuItem("Show Stats");
		m21.addActionListener(this);


		menu1.add(m11);

		menu2.add(m21);
		menu2.addSeparator();
		menu2.add("Resolution:");
		for(JMenuItem i : resolutionObjs){	//resolutions need to be at seperate list to be set by Game class.
			i.addActionListener(this);
			menu2.add(i);
		}
		menu2.addSeparator();
		menu2.add("Game speed:");
		new Ups(128);
		new Ups(64);
		new Ups(52);
		menu2.add("FPS:");
		new Fps(128);
		new Fps(64);
		new Fps(52);

		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar();
		return menuBar;
	}

	private double scaleY(){
		return scaleY;
	}

	public double scaleSize(){
		return scaleX;
	}

	public int scaleSizeX(double a){
		return (int) (a*scaleX);
	}
	public int scaleSizeY(double a){
		return (int) (a*scaleY);
	}
	public int scaleX(double a){
		return (int) ((a-camera.xPos)*scaleX);
	}
	public int scaleY(double a){
		return (int) ((a-camera.yPos)*scaleY);
	}

	public void updateScales(){
		if(menuBar.isVisible()) scaleY=(height-menuBar.getHeight()-topInset)/(firstH*camera.viewScale);
		else scaleY=height/(firstH*camera.viewScale);

		if(menuBar.isVisible()) scaleX=(width-1)/(firstW*camera.viewScale);
		else scaleX=width/(firstW*camera.viewScale);
	}

	public void setFps(int fps){
		target_fps=fps;
		targetTımeBetweenRenders=1000000000/target_fps;
		System.out.println(fps+" fps");
	}

	public void setUps(int ups){
		gameHertz=ups;
		tımeBetweenUpdates=1000000000/gameHertz;
		System.out.println(ups+" ups");
		System.out.println("wait time: "+tımeBetweenUpdates);
	}

	public void setFrame(int x, int y){
		width=x;
		height=y;
		frame.setSize(width+rightInset, height+topInset);
		updateScales();

	}

	public void addText(String text, int xpos, int ypos){
		texts.add(new Text(text, xpos, ypos));
	}
	public void removeText(String identity){
		for(Text t:texts)if(t.identity.equals(identity))texts.remove(t);
	}

	@Override
	public void keyPressed(KeyEvent e){
		Integer a=e.getKeyCode();
		pressed.add(a);
		if(a=='K'){
			if(!menuBar.isVisible()){
				menuBar.setVisible(true);
				setFrame(width+1, height+menuBar.getHeight());
			}
		}
		else if(a=='L'){
			if(menuBar.isVisible()){
				menuBar.setVisible(false);
				setFrame(width-1, height-menuBar.getHeight());
			}
		}else if(a==KeyEvent.VK_NUMPAD6) camera.move(2, 0);
		else if(a==KeyEvent.VK_NUMPAD4) camera.move(-2, 0);
		else if(a==KeyEvent.VK_NUMPAD8) camera.move(0, -2);
		else if(a==KeyEvent.VK_NUMPAD2) camera.move(0, 2);
		else if(a==KeyEvent.VK_NUMPAD9) camera.chanceScale(0.04f);
		else if(a==KeyEvent.VK_NUMPAD3) camera.chanceScale(-0.04f);
	}

	@Override
	public void keyReleased(KeyEvent e){
		pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem) (e.getSource());
	}

	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println("pressed something");
		if((e.getSource())==m21){
			System.out.println("show stats");
			showStats=!showStats;
		}else if((e.getSource())==m11){
			reset();
		}else actions(e);
	}

	@Override
	public void mouseMoved(MouseEvent e){
		mouseX=e.getX();
		mouseY=e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e){
	}


	protected abstract void gameCodes();
	protected abstract void reset();

	/**
	 * add resolutions as:
	 * new Resolution(int width,int height)
	 * each resolution should be at same ratio
	 */
	public abstract void resolutions();

	/**
	 * ?ref=new JMenuItem("?buttonName");
	 * ?ref.addActionListener(this);
	 * menu1.add(?ref);
	 */
	protected abstract void menuBar();
	protected abstract void actions(ActionEvent e);
	protected abstract void draw(Graphics g);
}