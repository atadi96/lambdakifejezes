package lambdakifejezes.Map;

import eu.loxon.centralcontrol.ObjectType;

public class Cell {
	public Cell(ObjectType type, String team)
	{
		this.type = type;
		this.team = team;
	}
	
	private ObjectType type;
	private String team;
	
	public ObjectType getType()
	{
		return this.type;
	}
	public void setType(ObjectType type)
	{
		this.type = type;
	}
	
	public String getTeam()
	{
		return this.team;
	}
	public void setTeam(String team)
	{
		this.team = team;
	}
	
	public String toString()
	{
		switch (type)
		{
		case BUILDER_UNIT:
			return "U";
		case GRANITE:
			return "G";
		case OBSIDIAN:
			return "O";
		case ROCK:
			return "R";
		case SHUTTLE:
			return "S";
		default:
			return "T";
		}
	}
}
