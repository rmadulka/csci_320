package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ViewCatalog {
    public static Options viewOptions () {

        Options viewOptions = new Options();

        Option barcode = Option.builder("t")
                .longOpt("type")
                .argName("type")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("type [available|borrowed|lent]")
                .build();
        viewOptions.addOption(barcode);

        return viewOptions;
    }
}
