import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.Test;

public class testSort {

	@Test
	public void test() throws Exception {
		TextBuddy.initializeTextBuddy("output.txt");
		TextBuddy.sortText();
		assertTrue(compareFile("output.txt", "expected.txt"));
	}
	
	public boolean compareFile(String file1, String file2) throws Exception {

		BufferedReader reader1 = new BufferedReader(new FileReader(new File(file1)));
		BufferedReader reader2 = new BufferedReader(new FileReader(new File(file2)));

		String line1 = reader1.readLine();
		String line2 = reader2.readLine();
		boolean isSame = true;

		while (isSame && (line1 != null) && (line2 != null)) {
			if (!line1.equalsIgnoreCase(line2))
				isSame = false;
			else
				isSame = true;
			line1 = reader1.readLine();
			line2 = reader2.readLine();
		}
		reader1.close();
		reader2.close();
		return isSame;

	}

}
