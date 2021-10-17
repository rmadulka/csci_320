package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Search {
    public static Options searchOptions () {

        Options searchOptions = new Options();

        Option barcode = Option.builder("m")
                .longOpt("method")
                .argName("method")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("method [tool|category]")
                .build();
        searchOptions.addOption(barcode);

        Option sortBy = Option.builder("s")
                .longOpt("sortBy")
                .argName("sortBy")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("sortBy [asc|desc]")
                .build();
        searchOptions.addOption(sortBy);

        return searchOptions;
    }
}
