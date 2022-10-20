import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class TextGenerator {
	private HashMap<String, ArrayList<Storage>> map = new HashMap<String, ArrayList<Storage>> ();
	private int chainlength;
	private String seed;
	
	public TextGenerator (int chainLength, String fileName) throws IOException
	{
		chainlength = chainLength;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String key = "";
		String value = "";
		
		while(reader.ready()) {
			char nextLetter = (char)reader.read();
			value += "" + nextLetter;
			if(key.length() == chainLength && value.length() == 1)
			{
				if(map.containsKey(key))
				{
					ArrayList<Storage> probs = map.get(key);
					boolean found = false;
					for(int i = 0; i < probs.size(); i++)
					{
						if(probs.get(i).getCombo().equals(value))
						{
							probs.get(i).setProbability(probs.get(i).getProbability() + 1);
							found = true;
						}
					}
					if(!found)
					{
						Storage addIn = new Storage(value, 1);
						probs.add(addIn);
					}
					map.put(key, probs);
				}
				else
				{
					Storage addThisIn = new Storage(value, 1);
					ArrayList<Storage> otherProbs = new ArrayList<Storage>();
					otherProbs.add(addThisIn);
					map.put(key, otherProbs);
				}
			}
			System.out.println(key);
			key += "" + nextLetter;
			if(key.length() == chainLength + 1)
				key = key.substring(1);
			System.out.println(value);
			value = "";
			
		}
		reader.close();
	}
	
	public String seedString() {
		String seed = "";
		double currentMax = 0;
		for(String key : map.keySet())
		{
			int total = 0;
			for(int i = 0; i < map.get(key).size(); i++)
			{
				total += map.get(key).get(i).getProbability();
			}
			if(total > currentMax)
			{
				currentMax = total;
				seed = key;
			}
		}
		return seed;
	}
	

	public void write(int length, String outputFileName) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(outputFileName));
		String seedStr = seedString();
		String text = seedStr;
		String lastFour = seedStr;
		
		for (int i = 0; i < length; i++)
		{
			ArrayList<Storage> probs = map.get(lastFour);
			for(int j = 0; j < probs.size(); j++)
			{
				System.out.println(probs.get(j).toString());
			}
			int total = 0;
			for(int j = 0; j < probs.size(); j++)
			{
				total += probs.get(j).getProbability();
			}
			int random = (int)(Math.random() * total);
			for(int j = 0; j < probs.size(); j++)
			{
				if(random > 0)
					random -= probs.get(j).getProbability();
				else
				{
					text += probs.get(j).getCombo();
					lastFour += probs.get(j).getCombo();
					break;
				}
			}
			lastFour = lastFour.substring(lastFour.length() - chainlength);
		}
		out.print(text);
		out.close();
	}
}