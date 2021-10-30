package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AcceptRequest {
    public static Options acceptRequestOptions(){

        Options acceptRequestOptions = new Options();

        Option username = Option.builder("u")
                .longOpt("username")
                .argName("username")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("username")
                .build();
        acceptRequestOptions.addOption(username);

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        acceptRequestOptions.addOption(barcode);

        Option dateNeededReturned = Option.builder("r")
                .longOpt("dateNeededReturned")
                .argName("dateNeededReturned")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("dateNeededReturned")
                .build();
        acceptRequestOptions.addOption(dateNeededReturned);

        return acceptRequestOptions;


    }


}
