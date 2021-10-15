import Commands.Register;
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


		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		while(!command.equals("exit")) {

			if (command.equals("register")) {
				cmd = parser.parse(Register.registerOptions(), arguments);
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
