import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextBuddy {	
	public static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use\n";
	public static final String MESSAGE_COMMAND = "command:";
	public static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"\n";
	public static final String MESSAGE_DISPLAYED = "%1$s\n";
	public static final String MESSAGE_EMPTY = "%1$s is empty\n";
	public static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"\n";
	public static final String MESSAGE_CLEARED = "all content deleted from %1$s\n";
	public static final String MESSAGE_SORTED = "\n";
	public static final String MESSAGE_SEARCHED = "\n";
	public static final String MESSAGE_INVALID_FORMAT = "invalid command format :%1$s\n";
	
	enum CommandType {
		ADD_TEXT, DELETE_TEXT, CLEAR_TEXT, DISPLAY_TEXT, SORT_TEXT, SEARCH_TEXT, INVALID, EXIT
	};
	
	
	public static File OUTPUT_FILE;
	public static String OUTPUT_FILENAME;
	
	public static ArrayList<String> texts = new ArrayList<String>();
	
	public static BufferedReader bufferReader;
	public static BufferedWriter bufferWriter;
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		initializeTextBuddy(args[0]);
		executeTextBuddy();
	}
	
	public static void showToUser(String text) {
		System.out.print(text);
	}

	public static void initializeTextBuddy(String targetFile) throws IOException {
		setOutputFile(targetFile);
		OUTPUT_FILE = new File(OUTPUT_FILENAME);
			
		if ( !OUTPUT_FILE.createNewFile() ) {
			FileReader fileReader = new FileReader(OUTPUT_FILE);
			bufferReader = new BufferedReader(fileReader);
			String currentLine = null;
			while ( (currentLine = bufferReader.readLine()) != null ) {
				texts.add(currentLine);   
			}
			bufferReader.close();
		}
			
		showToUser(String.format(MESSAGE_WELCOME, OUTPUT_FILENAME));
	}

	public static void setOutputFile(String filename) {
		OUTPUT_FILENAME = filename;
	}

	public static void executeTextBuddy() throws IOException {
		while (true) {
			showToUser(MESSAGE_COMMAND);
			String command = scanner.nextLine();
			String userCommand = command;
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
		}
	}
	
	public static String executeCommand(String userCommand) throws IOException {
		CommandType commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD_TEXT :
			return addText(userCommand);
		case DISPLAY_TEXT :
			return displayText(userCommand);
		case DELETE_TEXT :
			return deleteText(userCommand);
		case CLEAR_TEXT :
			return clearText(userCommand);
		case SORT_TEXT :
			return sortText();
		case SEARCH_TEXT :
			return searchText(userCommand);
		case INVALID :
			return promptError(userCommand);
		case EXIT :
			System.exit(0);
		default :
			//throw an error if the command is not recognized
			throw new Error("Unrecognized command type");
		}
	}

	public static CommandType determineCommandType(String userCommand) throws IOException {
		String commandTypeString = getFirstWord(userCommand);
		
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandType.DELETE_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
		 	return CommandType.SORT_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
		 	return CommandType.SEARCH_TEXT;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
		 	return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}

	public static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	public static String getParameter(String userCommand) {
		String parameter = userCommand.trim().split("\\s+", 2)[1];
		return parameter;
	}
	
	public static String addText(String userCommand) throws IOException {
		String inputLine = getParameter(userCommand);
		texts.add(inputLine);
		String message = String.format(MESSAGE_ADDED, OUTPUT_FILENAME, inputLine);
		saveText();
		return message;
	}
	
	public static String deleteText(String userCommand) throws IOException {
		Integer index = Integer.parseInt(getParameter(userCommand));			
		String text = texts.get(index-1);
		texts.remove(index-1);
		String message = String.format(MESSAGE_DELETED, OUTPUT_FILENAME, text);
		saveText();
		return message;
	}
	
	public static String displayText(String userCommand) {
		if (texts.isEmpty()) {
			return String.format(MESSAGE_EMPTY, OUTPUT_FILENAME);
		} else {
			String message = "";
			for (int i=0; i < texts.size()-1; i++) {
				message += (i+1) + ". " + texts.get(i) + "\n";
			}
			message += texts.size() + ". " + texts.get(texts.size()-1);
			
			return String.format(MESSAGE_DISPLAYED, message);
		}
	}
	
	public static String clearText(String userCommand) throws IOException {
		texts.clear();
		String message = String.format(MESSAGE_CLEARED, OUTPUT_FILENAME);
		saveText();
		return message;
	}
	
	public static String sortText() throws IOException {
		Collections.sort(texts, String.CASE_INSENSITIVE_ORDER);
		saveText();
		return MESSAGE_SORTED;
	}
	
	public static String searchText(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String promptError(String userCommand) {
		return String.format(MESSAGE_INVALID_FORMAT, userCommand);
	}

	public static void saveText() throws IOException {
		FileWriter fileWriter = new FileWriter(OUTPUT_FILE.getAbsoluteFile());
		bufferWriter = new BufferedWriter(fileWriter);
		for (int i=0; i < texts.size(); i++) {
			bufferWriter.write(texts.get(i));
			bufferWriter.newLine();
		}
		bufferWriter.close();
	}
}
