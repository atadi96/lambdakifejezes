package lambdakifejezes.Map;

import java.util.List;

import eu.loxon.centralcontrol.ObjectType;
import eu.loxon.centralcontrol.Scouting;
import eu.loxon.centralcontrol.WsCoordinate;

public class Map {
	public Map(WsCoordinate size)
	{
		this.sizeX = size.getX();
		this.sizeY = size.getY();
		this.map = new Cell[sizeX][sizeY];
	}
	private Cell[][] map;
	private int sizeX, sizeY;
	private WsCoordinate hqLocation, hqExitLocation;

	public void setHqLocation(WsCoordinate location)
	{
		hqLocation = location;
	}
	public WsCoordinate getHqLocation()
	{
		return hqLocation;
	}
	
	public void setHqExitLocation(WsCoordinate location)
	{
		hqExitLocation = location;
		//map[location.getX() - 1][location.getY() - 1] = new Cell(ObjectType.ROCK, "");
	}
	public WsCoordinate getHqExitLocation()
	{
		return hqExitLocation;
	}
	
	//starting from zero
	public Cell getMapField(int xIndex, int yIndex) throws Exception
	{
		if (xIndex < 0 || xIndex >= sizeX)
			throw new Exception("xIndex parameter is out of range.");
		if (yIndex < 0 || yIndex >= sizeY)
			throw new Exception("yIndex parameter is out of range.");
		return map[xIndex][yIndex];
	}
	
	public void UpdateMap(List<Scouting> multiScouting)
	{
		for(Scouting s : multiScouting)
		{
			//if we add a new map field
			if (map[s.getCord().getX()][s.getCord().getY()] == null)
				map[s.getCord().getX()][s.getCord().getY()] = new Cell(s.getObject(), s.getTeam());
			else
			{
				//if we update an existing map field
				map[s.getCord().getX()][s.getCord().getY()].setType(s.getObject());
				map[s.getCord().getX()][s.getCord().getY()].setTeam(s.getTeam());
			}
		}
	}
	
	public void PrintMap()
	{
		System.out.println("Current map is:");
		for(int y = 0; y < sizeY; y++)
		{
			for(int x = 0; x < sizeX; x++)
			{
				if (map[x][y] == null)
					System.out.print(" ");
				else
					System.out.print(map[x][y]);
			}
			System.out.println();
		}
		
	}
}
