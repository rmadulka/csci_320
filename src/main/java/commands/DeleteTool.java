package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DeleteTool {
    public static Options deleteToolOptions () {

        Options deleteToolOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        deleteToolOptions.addOption(barcode);

        
        return deleteToolOptions;
    }
}
