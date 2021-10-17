package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Login {
    public static Options loginOptions () {

        Options loginOptions = new Options();

        Option username = Option.builder("u")
                .longOpt("username")
                .argName("username")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("username")
                .build();
        loginOptions.addOption(username);

        Option password = Option.builder("p")
                .longOpt("password")
                .argName("password")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("password")
                .build();
        loginOptions.addOption(password);

        return loginOptions;
    }
}
