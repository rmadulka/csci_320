package commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class UpdateCategoryName {
    public static Options updateCategoryNameOptions () {

        Options updateCategoryNameOptions = new Options();

        Option categoryID = Option.builder("c")
                .longOpt("categoryID")
                .argName("categoryID")
                .hasArg()
                .valueSeparator()
                .desc("category ID")
                .build();
        updateCategoryNameOptions.addOption(categoryID);

        Option categoryName = Option.builder("n")
                .longOpt("categoryName")
                .argName("categoryName")
                .hasArg()
                .valueSeparator()
                .desc("category name")
                .build();
        updateCategoryNameOptions.addOption(categoryName);


        return updateCategoryNameOptions;
    }
}
