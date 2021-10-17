package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CreateCategory {
    public static Options createCategoryOptions () {

        Options createCategoryOptions = new Options();

        Option name = Option.builder("n")
                .longOpt("name")
                .argName("name")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("name")
                .build();
        createCategoryOptions.addOption(name);

        return createCategoryOptions;
    }
}
