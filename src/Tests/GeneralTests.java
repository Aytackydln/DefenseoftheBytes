package Tests;

import Engine.Camera;
import Engine.Map;
import Engine.MenuItems.GameButton;
import Engine.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeneralTests{
	static MockMain main=new MockMain();
	static Map map=new Map(250,250);

	public void tickMap(int times, double delta){
		for(int i=0;i<times;i++)map.tick(delta);

		System.out.println("Engine ran for "+times*delta+" seconds with "+1/delta+" hertz");
	}

	@BeforeClass
	public static void init(){
		main.loadMap(map);
	}

	@Before
	public void clear(){
		map.units.removeAll(map.units);
	}

	@After
	public void endTest(){
		System.out.println();
	}

	@Test
	public void settingTest(){	//check if the determined variable is saved/loaded
		System.out.println("Running setting test...");

		String string="something"+main.rng.nextInt(50);
		main.testVariable=string;
		main.variables.add("testVariable");
		main.saveConf();
		main=new MockMain();
		assertEquals("Saving/loading setting failed",string,main.testVariable);
	}

	@Test
	public void movementTest1(){
		System.out.println("Running x-axis movement test...");

		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);

		tickMap(10,0.1);	//simulate engine for 1 second
		assertEquals("Moving along x-axis failed", speed,unit.xPos,0.001);
	}

	@Test
	public void movementTest2(){
		System.out.println("Running y-axis movement test....");

		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.rotation=Math.PI/2;

		tickMap(10,0.1);	//simulate engine for 1 second
		assertEquals("Moving along y-axis failed", speed,unit.yPos,0.001);
	}

	@Test
	public void movementTest3(){
		System.out.println("Running rotated-axis movement...");

		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.rotation=Math.PI/4;

		tickMap(10,0.1);	//simulate engine for 1 second

		double distance=Math.sqrt(Math.pow(unit.xPos,2)+Math.pow(unit.yPos,2));
		assertEquals("Moving along rotated-axis failed", speed,distance,0.001);
	}

	//check if collision blocks unit from moving out of map
	@Test
	public void collisionTest1(){
		System.out.println("Running collision test against edge of the map...");

		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		tickMap(100,0.1);	//simulate engine for 10 second
		assertEquals("Map edge collision test failed", unit.xPos,125,0.01);
		assertEquals("Map edge collision test failed", unit.yPos,0,0.01);
	}

	//check if collision blocks unit with size from moving out of map
	@Test
	public void collisionTest2(){
		System.out.println("Running collision test against edge of the map with unit size...");

		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.size=5;
		tickMap(100,0.1)	;//simulate engine for 10 second
		assertEquals("Map edge collision with unit size test failed", unit.xPos,125-unit.size/2,0.01);
		assertEquals("Map edge collision with unit size test failed", unit.yPos,0,0.01);
	}

	//check if collide(unit) method is called when two units collide
	@Test
	public void collisionTest3(){
		System.out.println("Running collision method call test with two units...");

		int speed=30;
		final boolean[] pass={false};
		Unit unit=new MockUnit(map,0,0,speed){
			@Override
			public void collide(Unit u){
				pass[0]=true;
			}
		};
		unit.size=10;
		unit.collides=true;
		unit.pierces=false;
		Unit unit2=new Unit(map,50,0);
		unit2.size=20;
		unit2.collides=true;
		unit2.pierces=false;

		tickMap(100,0.1);	//simulate engine for 10 second
		assertTrue("Units did not collide", pass[0]);
	}

	//check if movement is correctly blocked when two units collide
	@Test
	public void collisionTest4(){
		System.out.println("Running collision movement test with two units...");

		int speed=30;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.size=10;
		unit.collides=true;
		unit.pierces=false;
		Unit unit2=new Unit(map,50,0){
			@Override
			public void tick(double delta){
			}
		};
		unit2.size=20;
		unit.collides=true;
		unit.pierces=false;

		tickMap(100,0.1);	//simulate engine for 10 second
		assertEquals("Unit coordinate is unexpected after collision", 50-(unit.size+unit2.size)/2, unit.xPos,0.01);
	}

	//check if piercing unit passes trough non-piercing unit
	@Test
	public void pierceTest(){
		System.out.println("Running pierce movement test with two units...");

		int speed=30;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.size=10;
		unit.collides=true;
		unit.pierces=true;
		Unit unit2=new Unit(map,50,0){
			@Override
			public void tick(double delta){
			}
		};
		unit2.size=20;
		unit2.collides=true;
		unit2.pierces=false;

		tickMap(100,0.1);	//simulate engine for 10 second
		assertEquals("Unit coordinate is unexpected after piercing movement", 125-unit.size/2, unit.xPos,0.01);
	}

	//check if map distance is same with screen distance when viewScale is 1
	@Test
	public void screenPositionTest1(){
		System.out.println("Running screen position test...");

		Unit unit=new Unit(map,30,40);
		main.camera.viewScale=1;
		main.camera.chanePos(0,0);
		double distance=Math.pow(unit.xPos,2)+Math.pow(unit.yPos,2);
		double cameraDistance=main.camera.screenXPos(Math.pow(unit.xPos,2))+main.camera.screenYPos(Math.pow(unit.yPos,2));
		assertEquals(distance,cameraDistance,0.01);
	}

	//check if viewScale correctly applies
	@Test
	public void screenPositionTest2(){
		System.out.println("Running zoomed screen position test...");

		Unit unit=new Unit(map,30,40);
		Camera camera=main.camera;
		camera.viewScale=2;
		camera.chanePos(0,0);
		double distance=Math.pow(unit.xPos,2)+Math.pow(unit.yPos,2);
		double cameraDistance=(camera.screenXPos(Math.pow(unit.xPos,2))+camera.screenYPos(Math.pow(unit.yPos,2)));
		assertEquals(distance,cameraDistance/camera.viewScale,0.01);
	}

	@Test
	public void addingGameButton(){
		System.out.println("Running add game button test...");
		Component button=new GameButton("Test"){
			@Override
			public void buttonAction(){

			}
		};
		boolean pass=false;
		for(Component c:main.menu1.getMenuComponents())if(c==button)pass=true;
		assertTrue("Adding game button failed", pass);
	}
}
