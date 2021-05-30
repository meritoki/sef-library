package com.meritoki.library.sef;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Hello world!
 *
 */
public class Library {
	static Logger logger = LogManager.getLogger(Library.class.getName());
	public static String versionNumber = "0.1.202105";
	public static String vendor = "Meritoki";
	public static String about = "Version " + versionNumber + " Copyright " + vendor + " 2020-2021";
	public static Option helpOption = new Option("h", "help", false, "Print usage information");
	public static Option versionOption = new Option("v", "version", false, "Print version information");
	
    public static void main( String[] args ) {
    	
    	Options options = new Options();
		options.addOption(helpOption);
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine commandLine = parser.parse(options, args);
			if (commandLine.hasOption("help")) {
				logger.info("main(args) help");
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("sef", options, true);
			} else if (commandLine.hasOption("version")) {
				System.out.println(about);
			} else {
				
			}
		} catch (org.apache.commons.cli.ParseException ex) {
			logger.error(ex);
		}
    }
}
