

package xcom.apps ;


import static xcom.utils4j.format.Templator.InjectFromArray ;
import static xcom.utils4j.format.Templator.UnixDelimiters ;
import static xcom.utils4j.logging.Loggers.ConsoleLoggerName ;

import java.io.File ;
import java.io.IOException ;
import java.net.URISyntaxException ;
import java.time.LocalDateTime ;
import java.time.format.DateTimeFormatter ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;
import java.util.Properties ;

import org.apache.commons.cli.CommandLine ;
import org.apache.commons.cli.DefaultParser ;
import org.apache.commons.cli.ParseException ;
import org.apache.commons.io.FileUtils ;
import org.apache.commons.io.filefilter.WildcardFileFilter ;
import org.apache.commons.lang3.StringUtils ;
import org.apache.poi.EncryptedDocumentException ;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import xcom.apps.args.DeleteDirectory_CLArgs ;
import xcom.utils4j.CLArgs ;
import xcom.utils4j.Enums ;
import xcom.utils4j.Nuts ;
import xcom.utils4j.format.Templator ;
import xcom.utils4j.format.Templator.Templator$TemplatedAsString ;
import xcom.utils4j.logging.aspects.api.annotations.Log ;
import xcom.utils4j.resources.Props ;
import xcom.utils4j.resources.Resources ;


public class DeleteDirectory {

	private static final Logger Logger = LoggerFactory.getLogger(DeleteDirectory.class) ;
	private static final Logger Console = LoggerFactory.getLogger(ConsoleLoggerName) ;

	static final String AppName = DeleteDirectory.class.getSimpleName() ;
	static final String AppClassname = DeleteDirectory.class.getName() ;
	static final String AppDesc = "Delete files and directory structure." ;
	static final String AppSee =
			"See https://github.com/???" + AppName + ".md" ;

	DeleteDirectory_CLArgs clArgs ;
	Map<String, List<String>> cmdFilters ;
	Properties props ;


	static final String Today_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()) ;


	Map<String, Map<String, String>> migrations ;
	Map<String, List<Entry<String, String>>> forms ;
	Map<String, String> templates ;


	@Log
	public DeleteDirectory(DeleteDirectory_CLArgs clArgs, Properties props, Map<String, List<String>> cmdFilters) {

		this.clArgs = clArgs ;
		this.props = props ;
		this.cmdFilters = cmdFilters ;

		templates = new LinkedHashMap<>() ;
	}


	@Log
	DeleteDirectory parseDataMigrationsTrackingDocument() throws EncryptedDocumentException, IOException, URISyntaxException {

		Console.info("\nParsing Data Migrations Tracking Document ...") ;

		final String fileSpec = clArgs.cmdArgs().get(CLArg_DataMigrationsTrackingDocument) ;
		Logger.debug("DMTD: {}", fileSpec) ;
		Console.info("    @{}", fileSpec) ;

		migrations = MigrationsUtils.parseDataMigrationsTrackingDocument(fileSpec, clArgs.cmd(), cmdFilters, DMTD_ExcludeHeaders) ;

		Logger.debug("DMTD: {}", migrations) ;


		Logger.debug("    parsed {} migrations.", migrations.size()) ;
		Console.info("        parsed {} migrations.", migrations.size()) ;

		return this ;
	}


	@Log
	DeleteDirectory parseVVFormData() throws EncryptedDocumentException, IOException, URISyntaxException {

		Console.info("\nParsing VisualVault Form Data ...") ;

		final String fileSpec = clArgs.cmdArgs().get(CLArg_VVFormData) ;
		Logger.debug("VVFD: {}", fileSpec) ;
		Console.info("    @{}", fileSpec) ;

		forms = VVFormDataUtils.parseVVFormDataAsFormCentric(fileSpec, clArgs.cmd(), cmdFilters, VVFD_ExcludeHeaders, VVFD_ExcludeDisabledFields) ;

		Logger.debug("forms: {}", forms.keySet()) ;
		Logger.debug("forms: {}", forms.get("License")) ;


		Logger.debug("    parsed {} forms.", forms.size()) ;
		Console.info("        parsed {} forms.", forms.size()) ;

		return this ;
	}


	@Log
	DeleteDirectory generate() throws URISyntaxException, IOException {

		Console.info("\nGenerating View templates ...") ;

		final Templator$TemplatedAsString createViewTmpl =
				Templator.delimiters(UnixDelimiters).template(Resources.readAsString(ScriptUtilsName + "_createViewTemplate.sql.tmpl", ScriptUtils.class)) ;
		final Templator$TemplatedAsString createViewColumnTmpl = Templator.delimiters(UnixDelimiters)
				.template(Resources.readAsString(ScriptUtilsName + "_createViewTemplate_Column.sql.tmpl", ScriptUtils.class)) ;
		final Templator$TemplatedAsString createViewDMTColumnTmpl = Templator.delimiters(UnixDelimiters)
				.template(Resources.readAsString(ScriptUtilsName + "_createViewTemplate_DMTColumn.sql.tmpl", ScriptUtils.class)) ;


		final StringBuilder columns = new StringBuilder() ;
		final Map<String, Object> parms = new HashMap<>() ;
		parms.put("dmtProject", DMT_Project) ;
		parms.put("appName", AppName) ;
		parms.put("yyyy_mm_dd", Today_YYYY_MM_DD) ;


		for ( final Entry<String, Map<String, String>> _eMigration : migrations.entrySet() ) {

			final String database = DMT_Project_ + _eMigration.getValue().get(DMTD_MappingsDatabaseColumn) ;
			final String schema = _eMigration.getValue().get(DMTD_MappingsSchemaColumn) ;
			final String grouping = _eMigration.getValue().get(DMTD_MappingsGroupingColumn) ;

			for ( final Entry<String, List<Entry<String, String>>> _eVVForm : forms.entrySet() ) {

				final VVForms form ;

				if ( (form = Enums.valueOfIgnoreCase(VVForms.class, VVFormDataUtils.normalizeVVName(_eVVForm.getKey()))) == null )
					Nuts.exit("{} is not declared in VVForms", (Object) _eVVForm.getKey()) ;

				columns.setLength(0) ;
				final String viewName = DMT_MappingsViewPrefix + StringUtils.deleteWhitespace(_eVVForm.getKey()) ;


				// Add DMT Form ID fields ...

				if ( !form.ids().isEmpty() ) {
					for ( final VVFormIDs _id : form.ids() ) {
						parms.put("formFieldName", _id.formFieldName()) ;
						columns.append(createViewDMTColumnTmpl.inject(parms)) ;
					}

					columns.append("\n") ;
				}


				// Add VV Form Data fields ...

				for ( final Entry<String, String> formField : _eVVForm.getValue() )

					if ( formField.getKey().equals(VVFD_VVFieldNameColumn) ) {
						parms.put("formFieldName", formField.getValue()) ;
						columns.append(createViewColumnTmpl.inject(parms)) ;
					}

				parms.put("columns", columns.toString()) ;
				parms.put("viewName", viewName) ;
				parms.put("formNameID", StringUtils.deleteWhitespace(_eVVForm.getKey())) ;
				parms.put("database", database) ;
				parms.put("schema", schema) ;

				final String templateID = grouping + ":" + schema + ":" + parms.get("viewName") ;
				templates.put(templateID, createViewTmpl.inject(parms)) ;
			}
		}

		Logger.debug("templates: {}", templates.keySet()) ;


		Logger.debug("    generated {} Templates.", templates.size()) ;
		Console.info("        generated {} Templates.", templates.size()) ;

		return this ;

	}


	@Log
	DeleteDirectory write() throws URISyntaxException, IOException {

		Console.info("\nWriting ...") ;


		final String srcDirSpec = clArgs.cmdArgs().get(CLArg_MappingsDirectory) ;
		final String tarDirSpec = clArgs.cmdArgs().get(CLArg_MappingsTargetDirectory) ;
		final String stgDirSpec = clArgs.cmdArgs().get(CLArg_StagingTargetDirectory) ;

		final Map<String, Object> parms = new HashMap<>() ;
		String[] existing ;
		int writes = 0 ;

		for ( final Entry<String, String> _eTemplate : templates.entrySet() ) {

			final String templateID = _eTemplate.getKey() ;
			final String grouping = templateID.split(":")[0] ;
			final String schema = templateID.split(":")[1] ;
			final String viewName = templateID.split(":")[2] ;

			final String dir = grouping + "\\_createViews\\" ;
			final String fn = "createView " + grouping + " " + viewName ;

			final Object[] p1 = { "dirSpec", tarDirSpec, "grouping", grouping, "schema", schema, "viewName", viewName, } ;
			final String tarFNSpec = CreateViewTemplateFSpec.tmpl().inject(InjectFromArray, p1) ;


			if ( ((existing = new File(srcDirSpec + dir).list(new WildcardFileFilter(fn + ".sql"))) == null) || (existing.length == 0) ) {
				Logger.debug("template: {}", tarFNSpec) ;
				Console.info("    @{}", tarFNSpec) ;

				FileUtils.writeStringToFile(new File(tarFNSpec), _eTemplate.getValue()) ;
				writes++ ;
			}

			final Object[] p2 = { "dirSpec", stgDirSpec, "grouping", grouping, "schema", schema, "viewNamw", viewName, } ;
			parms.put("runScript", CreateViewFSpec.tmpl().inject(InjectFromArray, p2)) ;
		}


		Logger.debug("    wrote {} Templates.", writes) ;
		Console.info("        wrote {} Templates.", writes) ;

		return this ;
	}


	@Log
	public static void main(final String[] args) throws ParseException, EncryptedDocumentException, InvalidFormatException, IOException, URISyntaxException {

		// Parse and process command line args ...

		final CommandLine cmd = new DefaultParser().parse(CommandLineOptions, args) ;

		if ( cmd.hasOption(CLArg_Help) ) {
			CLArgs.printToolHelp(Console, AppClassname, CommandLineOptions, AppDesc, AppSee) ;
			return ;
		}


		// Load properties and decode args ...

		Properties props = Props.load("Apps.properties", "apps/" + AppName + ".properties") ;
		Logger.debug("props: {}", props) ;

		DeleteDirectory_CLArgs clArgs = new DeleteDirectory_CLArgs(cmd, props) ;
		clArgs.decodeCommandLine(cmd, CommandLineOptions, props, clArgs, Logger) ;
		Logger.debug("cmdArgs: {}", Arrays.asList(clArgs.cmdArgs())) ;

		Map<String, List<String>> cmdFilters = ArgUtils.buildCommandLineFilters(cmd, clArgs.cmdArgs(), CommandLineFilterArgs, new HashMap<>()) ;
		Logger.debug("cmdFilters: {}", Arrays.asList(cmdFilters)) ;


		CLArgs.printToolUsage(Console, AppClassname, clArgs, AppDesc) ;


		// Instantiate and process ...

		DeleteDirectory $ = new DeleteDirectory(clArgs, props, cmdFilters) ;

		$.parseDataMigrationsTrackingDocument().parseVVFormData().generate().write() ;


		Console.info("\nDone.") ;
	}
}
