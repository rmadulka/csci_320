package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AddToolToCategory {
    public static Options addToolToCategoryOptions () {

        Options addToolToCategoryOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        addToolToCategoryOptions.addOption(barcode);

        Option category = Option.builder("c")
                .longOpt("category")
                .argName("category")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("category")
                .build();
        addToolToCategoryOptions.addOption(category);


        return addToolToCategoryOptions;
    }
}
