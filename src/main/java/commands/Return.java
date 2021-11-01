package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Return {
    public static Options returnOptions () {

        Options returnOptions = new Options();

        Option barcode = Option.builder("r")
                .longOpt("requestID")
                .argName("requestID")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("request ID")
                .build();
        returnOptions.addOption(barcode);

        return returnOptions;
    }
}
