package application;

import commands.*;
import org.apache.commons.cli.*;
import services.AddToolService;
import services.LoginService;
import services.RegisterService;
import services.ViewCatalogService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) throws ParseException, SQLException {
		Scanner scanner = new Scanner(System.in);

		String[] commands = getInput(scanner);
		String command = commands[0];
		String[] arguments = Arrays.copyOfRange(commands, 1, commands.length);


		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		LoginSession loginSession = null;

		while(!command.equals("exit")) {

			if (command.equals("register")) {
				cmd = parser.parse(Register.registerOptions(), arguments);

				loginSession = RegisterService.register(cmd.getOptionValue("username"),
						cmd.getOptionValue("password"),
						cmd.getOptionValue("email"),
						cmd.getOptionValue("firstName"),
						cmd.getOptionValue("lastName"));

			} else if (command.equals("login")) {
				cmd = parser.parse(Login.loginOptions(), arguments);

				loginSession = LoginService.login(cmd.getOptionValue("username"),
						cmd.getOptionValue("password"));

			} else if (command.equals("viewCatalog")) {
				if (loginSession == null) {
					System.out.println("Please Login");
				} else {
					ViewCatalogService.viewCatalog(loginSession.getUsername());
				}
			} else if (command.equals("addTool")) {
				if (loginSession == null) {
					System.out.println("Please Login");
				} else {

					cmd = parser.parse(AddTool.addToolOptions(), arguments);
					AddToolService.addTool(cmd.getOptionValue("barcode"),
							cmd.getOptionValue("name"),
							cmd.getOptionValue("description"),
							cmd.getOptionValue("purchaseDate"),
							cmd.getOptionValue("purchasePrice"),
							cmd.getOptionValue("shareable"),
							loginSession.getUsername());
				}

			} else if (command.equals("deleteTool")) {
				cmd = parser.parse(DeleteTool.deleteToolOptions(), arguments);
			} else if (command.equals("updateTool")) {
				cmd = parser.parse(UpdateTool.updateToolOptions(), arguments);
			} else if (command.equals("createCategory")) {
				cmd = parser.parse(CreateCategory.createCategoryOptions(), arguments);
			} else if (command.equals("addToolToCategory")) {
				cmd = parser.parse(AddToolToCategory.addToolToCategoryOptions(), arguments);
			} else if (command.equals("search")) {
				cmd = parser.parse(Search.searchOptions(), arguments);
			} else if (command.equals("request")) {
				cmd = parser.parse(Request.requestOptions(), arguments);
			} else if (command.equals("viewRequest")) {
				//Options Not needed
			} else if (command.equals("acceptRequest")) {
				//TODO front end
			} else if (command.equals("denyRequest")) {
				//TODO front end
			} else if (command.equals("viewAvailable")) {
				//TODO
			} else if (command.equals("viewLent")) {
				//TODO
			} else if (command.equals("viewBorrowed")) {
				//TODO
			} else if (command.equals("return")) {
				cmd = parser.parse(Return.returnOptions(), arguments);
			}

			commands = getInput(scanner);
			System.out.println(Arrays.toString(commands));
			command = commands[0];
			arguments = Arrays.copyOfRange(commands, 1, commands.length);
		}
	}

	//Regex magic to allow spaces
	public static String[] getInput(Scanner scanner) {
		List<String> commandList = new ArrayList<>();
		String input = scanner.nextLine();
		Matcher m = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'").matcher(input);
		while (m.find()) {
			if (m.group(1) != null) {
				commandList.add(m.group(1));
			} else if (m.group(2) != null) {
				commandList.add(m.group(2));
			} else {
				commandList.add(m.group());
			}
		}

		return commandList.toArray(new String[0]);
	}
}
