package lambdakifejezes.Connection;

import eu.loxon.centralcontrol.*;

/* It handles the communication with the server.*/
public class ConnectionManager implements ResponseCodeListener{
	public ConnectionManager(CentralControl centralControl)
	{
		this.centralControl = centralControl;
	}
	
	private CentralControl centralControl;
	private boolean isGameStarted = false;
	private boolean hasGetSpaceShuttlePosition = false;
	private boolean hasGetSpaceShuttleExitPosition = false;
	private static final int REACTION_TIMEOUT = 2000; //milliseconds
	
	
	public boolean canExplodeCell(ExplodeCellRequest req)
	{
		//TODO implement checking
		return false;
	}
	public ExplodeCellResponse explodeCell(ExplodeCellRequest req)
	{
		return centralControl.explodeCell(req);
	}
	
	public ActionCostResponse getActionCost(ActionCostRequest req)
	{
		return centralControl.getActionCost(req);
	}
	
	public boolean canGetSpaceShuttlePos (GetSpaceShuttlePosRequest req)
	{
		return !this.hasGetSpaceShuttlePosition;
	}
	public GetSpaceShuttlePosResponse getSpaceShuttlePos(GetSpaceShuttlePosRequest req)
	{
		GetSpaceShuttlePosResponse resp = centralControl.getSpaceShuttlePos(req);
		
		if (resp.getResult().getCode().equals(ResultType.DONE))
			this.hasGetSpaceShuttlePosition = true;
		
		return resp;
	}
	
	public boolean canGetSpaceShuttleExitPos (GetSpaceShuttleExitPosRequest req)
	{
		return !this.hasGetSpaceShuttleExitPosition;
	}
	public GetSpaceShuttleExitPosResponse getSpaceShuttleExitPos(GetSpaceShuttleExitPosRequest req)
	{
		GetSpaceShuttleExitPosResponse resp = centralControl.getSpaceShuttleExitPos(req);
		
		if (resp.getResult().getCode().equals(ResultType.DONE))
			this.hasGetSpaceShuttleExitPosition = true;
		
		return resp;
	}
	
	public boolean canGetIsMyTurn(IsMyTurnRequest req)
	{
		//TODO implement timer logic, to avoid penalty (too frequently polled server)
		
		
		return false;
	}
	public IsMyTurnResponse getIsMyTurn(IsMyTurnRequest req)
	{
		return centralControl.isMyTurn(req);
	}
	
	public boolean canMoveBuilderUnit(MoveBuilderUnitRequest req)
	{
		//TODO implement navigation check
		return false;
	}
	public MoveBuilderUnitResponse moveBuilderUnit(MoveBuilderUnitRequest req)
	{
		return centralControl.moveBuilderUnit(req);
	}
	
	public boolean canRadar(RadarRequest req)
	{
		//TODO implement checking
		return false;
	}
	public RadarResponse radar(RadarRequest req)
	{
		return centralControl.radar(req);
	}
	
	public boolean canStartGame(StartGameRequest req)
	{
		return !this.isGameStarted;
	}
	public StartGameResponse startGame(StartGameRequest req)
	{
		StartGameResponse resp = centralControl.startGame(req);
		
		if (resp.getResult().getCode().equals(ResultType.DONE))
			this.isGameStarted = true;
		return resp;
	}
	
	public boolean canStructureTunnel(StructureTunnelRequest req)
	{
		//TODO implement checking logic
		return false;
	}
	public StructureTunnelResponse structureTunnel(StructureTunnelRequest req)
	{
		return centralControl.structureTunnel(req);
	}
	
	
	@Override
	public void onError() {
		System.out.println("Error message received.");
	}
	@Override
	public void onSuccess() {
		System.out.println("Done message received.");
	}
	@Override
	public void onInvalid() {
		System.out.println("Invalid message received.");
	}
	
}
