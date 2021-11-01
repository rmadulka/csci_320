package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AcceptRequest {
    public static Options acceptRequestOptions(){

        Options acceptRequestOptions = new Options();

        Option requestID = Option.builder("r")
                .longOpt("requestID")
                .argName("requestID")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("request ID")
                .build();
        acceptRequestOptions.addOption(requestID);


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
