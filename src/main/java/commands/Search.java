package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Search {
    public static Options searchOptions () {

        Options searchOptions = new Options();

        Option method = Option.builder("m")
                .longOpt("method")
                .argName("method")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("method [barcode|name|category]")
                .build();
        searchOptions.addOption(method);

        Option barcode = Option.builder("a")
                .longOpt("methodArgument")
                .argName("methodArgument")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("method argument (i.e. the barcode to search for)")
                .build();
        searchOptions.addOption(barcode);

        Option sortByMethod = Option.builder("b")
                .longOpt("sortByMethod")
                .argName("sortByMethod")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("sortBy [category|name]")
                .build();
        searchOptions.addOption(sortByMethod);


        Option sortBy = Option.builder("s")
                .longOpt("sort")
                .argName("sort")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("sort [asc|desc]")
                .build();
        searchOptions.addOption(sortBy);

        return searchOptions;
    }
}
