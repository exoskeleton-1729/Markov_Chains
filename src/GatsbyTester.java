import java.io.IOException;


public class GatsbyTester {
	public static void main(String[] args) throws IOException
	{
		TextGenerator gatsby = new TextGenerator(8, "thegreatgatsby.txt");
		gatsby.write(10000, "thegaygatsby.txt");
	}
}