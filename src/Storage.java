public class Storage {
	private String combo;
	private double probability;
	
	public Storage (String Combo, double Probability)
	{
		combo = Combo;
		probability = Probability;
	}
	
	public String setCombo(String set)
	{
		combo = set;
		return combo;
	}
	
	public double setProbability(double set)
	{
		probability = set;
		return probability;
	}
	
	public String getCombo()
	{
		return combo;
	}
	
	public double getProbability()
	{
		return probability;
	}
	
	public String toString()
	{
		return("(" + combo + ", " + probability + ")");
	}
}
