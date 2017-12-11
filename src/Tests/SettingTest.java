package Tests;

import Engine.Map;
import Engine.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SettingTest{
	MockMain main;
	Map map;

	public void tickMap(int times, double delta){
		for(int i=0;i<times;i++)map.tick(delta);
	}

	@Before
	public void init(){
		main=new MockMain();
		map=new Map(250,250);
		main.loadMap(map);
	}

	@Test
	public void settingTest(){
		main.testVariable="something";
		main.variables.add("testVariable");
		try{
			main.saveConf();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
		main=new MockMain();
		assertEquals(main.testVariable,"something");
	}

	@Test
	public void movementTest(){
		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);

		tickMap(10,0.1);	//run game for 1 seconds
		assertEquals(unit.xPos,speed,0.01);
	}

	@Test
	public void collisionTest1(){	//collision with map edge
		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		tickMap(100,0.1);
		assertEquals(unit.xPos,125,0.01);
		assertEquals(unit.yPos,0,0.01);
	}

	@Test
	public void collisionTest2(){	//collision with map edge + unit size
		int speed=50;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.size=5;
		tickMap(100,0.1);
		assertEquals(unit.xPos,125-unit.size/2,0.01);
		assertEquals(unit.yPos,0,0.01);
	}
	@Test
	public void collisionTest3(){	//collision within two units
		int speed=30;
		final boolean[] pass={false};
		Unit unit=new MockUnit(map,0,0,speed){
			@Override
			public void collide(Unit u){
				pass[0]=true;
			}
		};
		unit.size=10;
		Unit unit2=new Unit(map,50,0);
		unit2.size=20;
		tickMap(100,0.1);
		assertTrue("Units did not collide", pass[0]);
	}
	@Test
	public void collisionTest4(){	//collision test against non-moving unit
		int speed=30;
		Unit unit=new MockUnit(map,0,0,speed);
		unit.size=10;
		Unit unit2=new Unit(map,50,0){
			@Override
			public void tick(double delta){
			}
		};
		unit2.size=20;
		tickMap(100,0.1);
		assertEquals(50-(unit.size+unit2.size)/2, unit.xPos,0.01);	//check if the unit stuck with other unit
	}
}
