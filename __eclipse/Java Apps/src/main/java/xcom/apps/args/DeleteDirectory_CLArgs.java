

package xcom.apps.args ;


import java.util.ArrayList ;
import java.util.List ;
import java.util.Properties ;

import org.apache.commons.cli.CommandLine ;
import org.apache.commons.cli.Options ;


public class DeleteDirectory_CLArgs extends ArgUtils {

	/**
	 * The valid command line options.
	 */

	public static final Options CommandLineOptions = new Options() ;
	static {
		CommandLineOptions.addOption(CLOption_Help) ;
	}

	public static final List<String> CommandLineFilterArgs = new ArrayList<>() ;
	static {
	}


	public DeleteDirectory_CLArgs(CommandLine cmd, Properties props) {
		super(cmd, props) ;
	}
}
