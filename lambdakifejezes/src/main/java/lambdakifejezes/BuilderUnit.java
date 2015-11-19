package lambdakifejezes;

import eu.loxon.centralcontrol.WsBuilderunit;
import eu.loxon.centralcontrol.WsCoordinate;

public class BuilderUnit {
	private int actionPoints = 0;
	private int explosives = 0;
	private WsCoordinate location;
	private int id;
	
	public BuilderUnit (WsBuilderunit unit)
	{
		this(unit.getCord(), unit.getUnitid());
	}
	
	public BuilderUnit(WsCoordinate location, int id)
	{
		this.location = location;
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	public void setExplosives(int explosives)
	{
		this.explosives = explosives;
	}
	public int getExplosives()
	{
		return this.explosives;
	}
	public void setActionPoints(int actionPoints)
	{
		this.actionPoints = actionPoints;
	}
	public int getActionPoints()
	{
		return this.actionPoints;
	}
	public WsCoordinate getLocation()
	{
		return this.location;
	}
	public void setLocation(WsCoordinate location)
	{
		this.location = location;
	}

}
