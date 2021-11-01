package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DenyRequest {
    public static Options denyRequestOptions(){

        Options denyRequestOptions = new Options();

        Option requestID = Option.builder("r")
                .longOpt("requestID")
                .argName("requestID")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("request ID")
                .build();
        denyRequestOptions.addOption(requestID);

        return denyRequestOptions;
    }
}
