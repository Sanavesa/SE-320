package alalim4.sos.model;

public enum GameSymbol
{
	Empty(" "),
	S("S"),
	O("O");

	private GameSymbol(String symbol)
	{
		representation = symbol;
	}

	@Override
	public String toString()
	{
		return representation;
	}
	
	public static GameSymbol fromString(String s)
	{
		for(GameSymbol symbol : values)
		{
			if(symbol.representation.equalsIgnoreCase(s))
				return symbol;
		}
		
		return null;
	}
	
	public static GameSymbol fromCharacter(char c)
	{
		for(GameSymbol symbol : values)
		{
			if(symbol.representation.charAt(0) == c)
				return symbol;
		}
		
		return null;
	}

	private final String representation;
	private static final GameSymbol[] values = GameSymbol.values();
}
