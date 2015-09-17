import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.Test;

public class testSearch {

	@Test
	public void test() throws Exception {
		TextBuddy.initializeTextBuddy("input.txt");
		String expectedOutcome = "searching result of \"cool\":\n1. cool\n2. super cool\n3. coolll\n";
		String outcome = TextBuddy.searchText("search cool");
		assertEquals(outcome, expectedOutcome);
	}
}
