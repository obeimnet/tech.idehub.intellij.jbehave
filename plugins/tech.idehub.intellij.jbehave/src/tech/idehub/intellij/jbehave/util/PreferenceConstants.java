package tech.idehub.intellij.jbehave.util;

public class PreferenceConstants {
	
	public enum StoryNameResolverType {
		DEFAULT, MODULE_RELATIVE, ABSOLUTE_PATH;
	}
	
	public static final String P_RUNNER_CLASS = PluginConstants.PLUGIN_ID.concat("runnerClass");
	
	public static final String P_STORY_FILE_EXTENTION = PluginConstants.PLUGIN_ID.concat("storyFileExtention");
	
	public static final String P_STORY_PATH_SYSTEM_PROPERTY = PluginConstants.PLUGIN_ID.concat("storyPathSystemProperty");

	public static final String P_STORY_FILE_PATH_RESOLUTION_STRATEGY = PluginConstants.PLUGIN_ID.concat("storyFilePathResolutionStrategy");

	public static final String P_ADDITIONAL_JVM_OPTIONS=  PluginConstants.PLUGIN_ID.concat("additionalJvmOptions");
	
}
