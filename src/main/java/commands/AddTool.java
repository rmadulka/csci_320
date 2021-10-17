package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AddTool {
    public static Options addToolOptions () {

        Options addToolOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        addToolOptions.addOption(barcode);

        Option name = Option.builder("n")
                .longOpt("name")
                .argName("name")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("name")
                .build();
        addToolOptions.addOption(name);

        Option description = Option.builder("d")
                .longOpt("description")
                .argName("description")
                .hasArg()
                .valueSeparator()
                .desc("description")
                .build();
        addToolOptions.addOption(description);

        Option purchaseDate = Option.builder("d")
                .longOpt("purchaseDate")
                .argName("purchaseDate")
                .hasArg()
                .valueSeparator()
                .desc("purchaseDate YYYY-MM-DD")
                .build();
        addToolOptions.addOption(purchaseDate);

        Option purchasePrice = Option.builder("p")
                .longOpt("purchasePrice")
                .argName("purchasePrice")
                .hasArg()
                .valueSeparator()
                .desc("purchasePrice")
                .build();
        addToolOptions.addOption(purchasePrice);

        Option shareable = Option.builder("s")
                .longOpt("shareable")
                .argName("shareable")
                .hasArg()
                .valueSeparator()
                .desc("shareable")
                .build();
        addToolOptions.addOption(shareable);


        return addToolOptions;
    }
}
