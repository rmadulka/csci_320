package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Return {
    public static Options returnOptions () {

        Options returnOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        returnOptions.addOption(barcode);

        return returnOptions;
    }
}
