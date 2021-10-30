package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DenyRequest {
    public static Options denyRequestOptions(){

        Options denyRequestOptions = new Options();

        Option username = Option.builder("u")
                .longOpt("username")
                .argName("username")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("username")
                .build();
        denyRequestOptions.addOption(username);

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        denyRequestOptions.addOption(barcode);

        return denyRequestOptions;
    }
}
