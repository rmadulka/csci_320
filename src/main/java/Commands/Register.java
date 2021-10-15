package Commands;

import org.apache.commons.cli.*;

public class Register {
    public static Options registerOptions () {

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

        return registerOptions;
    }
}
