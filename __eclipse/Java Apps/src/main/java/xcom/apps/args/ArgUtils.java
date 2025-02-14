

package xcom.apps.args ;



import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.List ;
import java.util.Map ;
import java.util.Properties ;

import org.apache.commons.cli.CommandLine ;
import org.apache.commons.cli.Option ;
import org.apache.commons.cli.Options ;
import org.apache.commons.cli.ParseException ;
import org.apache.commons.lang3.StringUtils ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;


import xcom.utils4j.CLArgs ;
import xcom.utils4j.Enums ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.resources.Files ;


public class ArgUtils extends CLArgs {

	private static final Logger Logger = LoggerFactory.getLogger(ArgUtils.class) ;


	public static final String CLArg_Help = "h" ;
	public static final String CLArgLong_Help = "help" ;
	public static final Option CLOption_Help = Option.builder(CLArg_Help).longOpt(CLArgLong_Help).desc("display this message").build() ;

	public static final String CLArg_AgencyFilter = "agencies" ;
	public static final Option CLOption_AgencyFilter = Option.builder(CLArg_AgencyFilter).hasArg().argName("agency [, ...]").desc("Agency Filter").build() ;

	public static final String CLArg_DatabaseName = "db" ;
	public static final Option CLOption_DatabaseName = Option.builder(CLArg_DatabaseName).hasArg().argName("database").desc("Database Name").build() ;

	public static final String CLArg_DataElementDictionary = "ded" ;
	public static final Option CLOption_DataElementDictionary =
			Option.builder(CLArg_DataElementDictionary).hasArg().argName("fileSpec").desc("Data Element Dictionary (Excel)").build() ;

	public static final String CLArg_DataMigrationsTrackingDocument = "dmtd" ;
	public static final Option CLOption_DataMigrationsTrackingDocument =
			Option.builder(CLArg_DataMigrationsTrackingDocument).hasArg().argName("fileSpec").desc("Data Migrations Tracking Document (Excel)").build() ;

	public static final String CLArg_DocsDirectory = "docs" ;
	public static final Option CLOption_DocsDirectory = Option.builder(CLArg_DocsDirectory).hasArg().argName("dirSpec").desc("Docs Directory").build() ;

	public static final String CLArg_DataValidationRulesDocument = "dvrd" ;
	public static final Option CLOption_DataValidationRulesDocument =
			Option.builder(CLArg_DataValidationRulesDocument).hasArg().argName("fileSpec").desc("Data Validation Rules Document (Excel)").build() ;

	public static final String CLArg_VVEpicFilter = "epics" ;
	public static final Option CLOption_VVEpicFilter =
			Option.builder(CLArg_VVEpicFilter).hasArg().optionalArg(true).argName("epic [, ...]").desc("VisualVault Epic Filter").build() ;

	public static final String CLArg_VVFormFilter = "forms" ;
	public static final Option CLOption_VVFormFilter =
			Option.builder(CLArg_VVFormFilter).hasArg().argName("form [, ...]").desc("VisualVault Form Filter").build() ;

	public static final String CLArg_MappingsDirectory = "mappings" ;
	public static final Option CLOption_MappingsDirectory =
			Option.builder(CLArg_MappingsDirectory).hasArg().argName("dirSpec").desc("Mappings Directory").build() ;

	public static final String CLArg_Markups = "markups" ;
	public static final Option CLOption_Markups = Option.builder(CLArg_Markups).argName("fileSpec [, ...]").hasArg().desc("Markups").build() ;

	public static final String CLArg_MigrationFilter = "migrations" ;
	public static final Option CLOption_MigrationFilter =
			Option.builder(CLArg_MigrationFilter).hasArg().argName("migration [, ...]").desc("Migration Filter").build() ;

	public static final String CLArg_OfficeFilter = "offices" ;
	public static final Option CLOption_OfficeFilter = Option.builder(CLArg_OfficeFilter).hasArg().argName("office [, ...]").desc("Office Filter").build() ;

	public static final String CLArg_ValidationRulesDirectory = "rules" ;
	public static final Option CLOption_ValidationRulesDirectory =
			Option.builder(CLArg_ValidationRulesDirectory).hasArg().argName("dirSpec").desc("Rules Directory").build() ;

	public static final String CLArg_SchemaName = "schema" ;
	public static final Option CLOption_SchemaName = Option.builder(CLArg_SchemaName).hasArg().argName("schema").desc("Schema Name").build() ;

	public static final String CLArg_SchemasDirectory = "schemas" ;
	public static final Option CLOption_SchemasDirectory =
			Option.builder(CLArg_SchemasDirectory).hasArg().argName("dirSpec").desc("Schemas Directory").build() ;

	public static final String CLArg_SchemasTargetDirectory = "scripts" ;
	public static final Option CLOption_SchemasTargetDirectory =
			Option.builder(CLArg_SchemasTargetDirectory).hasArg().argName("dirSpec").desc("Schemas Target Directory").build() ;

	public static final String CLArg_InScopeFilter = "scopes" ;
	public static final Option CLOption_InScopeFilter =
			Option.builder(CLArg_InScopeFilter).hasArg().argName("scope [, ...]").desc("Project Scope Filter").build() ;

	public static final String CLArg_TechnicalSpecificaionsTargetDirectory = "specs" ;
	public static final Option CLOption_TechnicalSpecificaionsTargetDirectory =
			Option.builder(CLArg_TechnicalSpecificaionsTargetDirectory).hasArg().argName("dirSpec").desc("Technical Specifications Target Directory").build() ;

	public static final String CLArg_Sniplets = "sniplets" ;
	public static final Option CLOption_Sniplets = Option.builder(CLArg_Sniplets).hasArg().argName("fileSpec [, ...]").desc("Scan Markup Sniplets").build() ;

	public static final String CLArg_StagingTargetDirectory = "staging" ;
	public static final Option CLOption_StagingTargetDirectory =
			Option.builder(CLArg_StagingTargetDirectory).hasArg().argName("pathSpec").desc("Staging Target Directory").build() ;

	public static final String CLArg_SystemFilter = "systems" ;
	public static final Option CLOption_SystemFilter = Option.builder(CLArg_SystemFilter).hasArg().argName("system [, ...]").desc("System Filter").build() ;

	public static final String CLArg_MappingsTargetDirectory = "views" ;
	public static final Option CLOption_MappingsTargetDirectory =
			Option.builder(CLArg_MappingsTargetDirectory).hasArg().optionalArg(true).argName("[dirSpec]").desc("Generated Mappings Target Directory").build() ;

	public static final String CLArg_ValidationRulesTargetDirectory = "violations" ;
	public static final Option CLOption_ValidationRulesTargetDirectory =
			Option.builder(CLArg_ValidationRulesTargetDirectory).hasArg().argName("dirSpec").desc("Validations Target Directory").build() ;

	public static final String CLArg_VVFormData = "vvfd" ;
	public static final Option CLOption_VVFormData =
			Option.builder(CLArg_VVFormData).hasArg().argName("fileSpec").desc("VisualVault Form Data (Excel)").build() ;


	@Log
	public ArgUtils(CommandLine cmd, Properties props) {
		super(cmd, props) ;
	}


	/**
	 * Process Agency Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_agencies(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_AgencyFilter)) ;
	}

	/**
	 * Process Data Element Dictionary argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_ded(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_DataElementDictionary), //
				DMT_StagingDirectory //
		) ;
	}

	/**
	 * Process Docs Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_docs(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_DocsDirectory), //
				(String) props.get(Props_DefaultDocsDirectory), //
				DMT_GITDirectory //
		) ;
	}

	/**
	 * Process Data Migrations Tracking Document argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_dmtd(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_DataMigrationsTrackingDocument), //
				(String) props.get(Props_DefaultDataMigrationsTrackingDocument), //
				DMT_StagingDirectory) ;
	}

	/**
	 * Process Data Validation Rules Document argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_dvrd(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_DataValidationRulesDocument), //
				(String) props.get(Props_DefaultDataValidationRulesDocument), //
				DMT_StagingDirectory) ;
	}

	/**
	 * Process VisualVault Epics Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_epics(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_VVEpicFilter)) ;
	}

	/**
	 * Process VisualVault Forms Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_forms(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_VVFormFilter)) ;
	}

	/**
	 * Process Mappings Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_mappings(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_MappingsDirectory), //
				(String) props.get(Props_DefaultMappingsDirectory), //
				DMT_StagingDirectory //
		) ;
	}

	/**
	 * Process Markups argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_markups(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_Markups), //
				DMT_GITDirectory //
		) ;
	}

	/**
	 * Process Migrations Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_migrations(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_MigrationFilter)) ;
	}

	/**
	 * Process Offices Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_offices(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_OfficeFilter)) ;
	}

	/**
	 * Process Data Violation Rules Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_rules(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_ValidationRulesDirectory), //
				(String) props.get(Props_DefaultValidationRulesDirectory), //
				DMT_StagingDirectory //
		) ;
	}

	/**
	 * Process Schema argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_schema(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_SchemaName)) ;
	}

	/**
	 * Process Schemas Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_schemas(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_SchemasDirectory), //
				(String) props.get(Props_DefaultSchemasTargetDirectory), //
				DMT_StagingDirectory //
		) ;
	}

	/**
	 * Process InScope Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_scopes(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_InScopeFilter)) ;
	}

	/**
	 * Process Schemas Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_scripts(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_SchemasTargetDirectory), //
				(String) props.get(Props_DefaultSchemasTargetDirectory), //
				DMT_WorkspaceDirectory //
		) ;
	}

	/**
	 * Process Scan for Sniplets argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_sniplets(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_Sniplets), //
				DMT_GITDirectory //
		) ;
	}

	/**
	 * Process Technical Specifications Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_specs(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLOption_TechnicalSpecificaionsTargetDirectory), //
				(String) props.get(Props_DefaultSpecificationsTargetDirectory), //
				DMT_WorkspaceDirectory //
		) ;
	}

	/**
	 * Process Staging Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_staging(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_StagingTargetDirectory), //
				(String) props.get(Props_DefaultStagingTargetDirectory) //
		) ;
	}

	/**
	 * Process Systems Filter argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_systems(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return StringUtils.trimToEmpty(cmd.getOptionValue(CLArg_SystemFilter)) ;
	}

	/**
	 * Process Generated Mappings Target Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_views(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				(String) props.get(Props_DefaultMappingsTargetDirectory), //
				cmd.getOptionValue(CLArg_MappingsTargetDirectory), //
				DMT_WorkspaceDirectory //
		) ;
	}

	/**
	 * Process Data Violation Rules Target Directory argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_violations(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_ValidationRulesTargetDirectory), //
				(String) props.get(Props_DefaultValidationRulesTargetDirectory), //
				DMT_WorkspaceDirectory //
		) ;
	}

	/**
	 * Process VisualVault Form Data argument ...
	 *
	 * @param  cmd
	 * @param  props
	 * @param  cmdArgs
	 *
	 * @return
	 */
	@Log
	public String decodeCommandLineArg_vvfd(final CommandLine cmd, final Properties props, final Map<String, String> cmdArgs) {
		return Files.coerceFullPathSpec( //
				cmd.getOptionValue(CLArg_VVFormData), //
				(String) props.get(Props_DefaultVVFormsDocument), //
				DMT_StagingDirectory //
		) ;
	}


	@Log
	public static Map<String, List<String>> buildCommandLineFilters(final CommandLine cmd, final Map<String, String> cmdArgs, final List<String> filters,
			final Map<String, List<String>> cmdFilters) {

		for ( final String _filter : filters ) {

			if ( !cmdFilters.containsKey(_filter) && Arrays.asList(new String[] { CLArg_VVEpicFilter, CLArg_VVFormFilter }).contains(_filter) )
				cmdFilters.put(CLArg_VVFormFilter, buildCommandLineVVFormFilter(cmdArgs)) ;

			if ( !cmdFilters.containsKey(_filter) )
				cmdFilters.put(_filter, buildCommandLineFilter(_filter, cmd, cmdArgs)) ;
		}


		return cmdFilters ;
	}

	@Log
	public static List<String> buildCommandLineVVFormFilter(final Map<String, String> cmdArgs) {

		List<String> filteredForms = new ArrayList<>() ;

		Logger.debug("CLArg_VVEpicFilter: " + cmdArgs.get(CLArg_VVEpicFilter)) ;
		Logger.debug("CLArg_VVFormFilter: " + cmdArgs.get(CLArg_VVFormFilter)) ;

		if ( !cmdArgs.get(CLArg_VVFormFilter).isEmpty() )    // -forms has precedence over -epics
			filteredForms = Arrays.asList(cmdArgs.get(CLArg_VVFormFilter).split(",")) ;

		else if ( !cmdArgs.get(CLArg_VVEpicFilter).isEmpty() ) {
			for ( final String _epic : cmdArgs.get(CLArg_VVEpicFilter).split(",") )
				for ( final VVForms _form : VVForms.formsByEpic(Enums.valueOfIgnoreCase(Epics.class, _epic)) )
					if ( _form.type() == DMT )
						filteredForms.add(_form.name()) ;
		}
		else if ( cmdArgs.get(CLArg_VVEpicFilter).isEmpty() )
			for ( final Epics _epic : Epics.values() )
				if ( _epic.isEnabled() )
					for ( final VVForms _form : VVForms.formsByEpic(_epic) )
						if ( _form.type() == DMT )
							filteredForms.add(_form.name()) ;

		return filteredForms ;
	}

	@Log
	public static List<String> buildCommandLineFilter(final String filter, final CommandLine cmd, final Map<String, String> cmdArgs) {
		return (!cmdArgs.get(filter).isEmpty() ? Arrays.asList(cmdArgs.get(filter).split(",")) : new ArrayList<>()) ;
	}
}
