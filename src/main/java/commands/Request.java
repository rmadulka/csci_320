package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Request {
    public static Options requestOptions () {

        Options requestOptions = new Options();

        Option barcode = Option.builder("b")
                .longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        requestOptions.addOption(barcode);

        Option dateRequired = Option.builder("r")
                .longOpt("dateRequired")
                .argName("dateRequired")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("dateRequired")
                .build();
        requestOptions.addOption(dateRequired);

        Option duration = Option.builder("d")
                .longOpt("duration")
                .argName("duration")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("duration")
                .build();
        requestOptions.addOption(duration);

        return requestOptions;
    }
}
