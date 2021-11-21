package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class RecommendTools {
    public static Options recommendTools (){
        Options recommendToolsOptions = new Options();

        Option barcode = Option.builder("b").
                longOpt("barcode")
                .argName("barcode")
                .hasArg()
                .required()
                .valueSeparator()
                .desc("tool barcode")
                .build();
        recommendToolsOptions.addOption(barcode);
        return recommendToolsOptions;
    }
}
