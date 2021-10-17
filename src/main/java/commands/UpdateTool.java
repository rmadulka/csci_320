package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class UpdateTool {
    public static Options updateToolOptions () {

        Options updateToolOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        updateToolOptions.addOption(barcode);

        Option name = Option.builder("n")
                .longOpt("name")
                .argName("name")
                .hasArg()
                .valueSeparator()
                .desc("name")
                .build();
        updateToolOptions.addOption(name);

        Option description = Option.builder("d")
                .longOpt("description")
                .argName("description")
                .hasArg()
                .valueSeparator()
                .desc("description")
                .build();
        updateToolOptions.addOption(description);

        Option purchaseDate = Option.builder("d")
                .longOpt("purchaseDate")
                .argName("purchaseDate")
                .hasArg()
                .valueSeparator()
                .desc("purchaseDate YYYY-MM-DD")
                .build();
        updateToolOptions.addOption(purchaseDate);

        Option purchasePrice = Option.builder("p")
                .longOpt("purchasePrice")
                .argName("purchasePrice")
                .hasArg()
                .valueSeparator()
                .desc("purchasePrice")
                .build();
        updateToolOptions.addOption(purchasePrice);

        Option shareable = Option.builder("s")
                .longOpt("shareable")
                .argName("shareable")
                .hasArg()
                .valueSeparator()
                .desc("shareable")
                .build();
        updateToolOptions.addOption(shareable);


        return updateToolOptions;
    }
}
