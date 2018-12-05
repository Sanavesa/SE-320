package alalim4.sos.model;

public enum GameMode
{
	Normal,
	Extreme,
	Combat;
	
	public static GameMode fromString(String str)
	{
		for(GameMode mode : values)
		{
			if(mode.toString().equals(str))
				return mode;
		}
		
		return null;
	}
	
	private static final GameMode[] values = GameMode.values();
}