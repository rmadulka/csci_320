import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		String[] commands = scanner.nextLine().split("\\s");
		String command = commands[0];
		String[] arguments = Arrays.copyOfRange(commands, 1, commands.length);

		Options registerOptions = new Options();

		Option username = Option.builder("u")
			.longOpt("username")
			.argName("username")
			.hasArg()
			.required()
			.valueSeparator()
			.desc("username")
			.build();
		registerOptions.addOption(username);

		Option password = Option.builder("p")
			.longOpt("password")
			.argName("password")
			.hasArg()
			.required()
			.valueSeparator()
			.desc("password")
			.build();
		registerOptions.addOption(password);

		Option email = Option.builder("e")
			.longOpt("email")
			.argName("email")
			.hasArg()
			.required()
			.valueSeparator()
			.desc("email")
			.build();
		registerOptions.addOption(email);

		Option firstName = Option.builder("f")
			.longOpt("firstName")
			.argName("firstName")
			.hasArg()
			.required()
			.valueSeparator()
			.desc("firstName")
			.build();
		registerOptions.addOption(firstName);

		Option lastName = Option.builder("l")
			.longOpt("lastName")
			.argName("lastName")
			.hasArg()
			.required()
			.valueSeparator()
			.desc("lastName")
			.build();
		registerOptions.addOption(lastName);



		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		while(!command.equals("exit")) {

			if (command.equals("register")) {
				cmd = parser.parse(registerOptions, arguments);
				System.out.println(cmd.getOptionValue("username"));
				System.out.println(cmd.getOptionValue("password"));
				System.out.println(cmd.getOptionValue("email"));
				System.out.println(cmd.getOptionValue("firstName"));
				System.out.println(cmd.getOptionValue("lastName"));
			}


			commands = scanner.nextLine().split("\\s");
			command = commands[0];
			arguments = Arrays.copyOfRange(commands, 1, commands.length);
		}
	}
}
