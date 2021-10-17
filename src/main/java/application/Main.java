package application;

import commands.*;
import org.apache.commons.cli.*;
import services.LoginService;
import services.RegisterService;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ParseException, SQLException {
		Scanner scanner = new Scanner(System.in);
		String[] commands = scanner.nextLine().split("\\s");
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

			} else if (command.equals("addTool")) {
				cmd = parser.parse(AddTool.addToolOptions(), arguments);
			} else if (command.equals("deleteTool")) {
				cmd = parser.parse(DeleteTool.deleteToolOptions(), arguments);
			} else if (command.equals("updateTool")) {
				cmd = parser.parse(UpdateTool.updateToolOptions(), arguments);
			} else if (command.equals("viewTools")) {
				//TODO
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
			} else if (command.equals("return")) {
				cmd = parser.parse(Return.returnOptions(), arguments);
			}


			commands = scanner.nextLine().split("\\s");
			command = commands[0];
			arguments = Arrays.copyOfRange(commands, 1, commands.length);
		}
	}
}
