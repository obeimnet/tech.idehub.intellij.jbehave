package tech.idehub.intellij.jbehave.util;

import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;

import static com.intellij.ide.util.PropertiesComponent.getInstance;
import static tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration.Data.*;
import static tech.idehub.intellij.jbehave.util.PreferenceConstants.*;

/**
 * Created by omer on 17/11/2015.
 */
public class SettingUtil {

    public static JBehaveJUnitConfiguration.Data getSettings(JBehaveJUnitConfiguration configuration) {

        String runnerClassName =  getInstance(configuration.getProject()).getValue(P_RUNNER_CLASS, DEFAULT_RUNNER_CLASS);
        String storyFileExtention =  getInstance(configuration.getProject()).getValue(P_STORY_FILE_EXTENTION, DEFAULT_STORY_FILE_EXTENTION);
        String storyPathSystemProperty =  getInstance(configuration.getProject()).getValue(P_STORY_PATH_SYSTEM_PROPERTY, DEFAULT_STORY_PATH_SYSTEM_PROPETY);
        String storyFilePathResolutionStrategy = getInstance(configuration.getProject()).getValue(P_STORY_FILE_PATH_RESOLUTION_STRATEGY, DEFAULT_STORY_PATH_RESOLUTION_TYPE.name());
        String additionalJvmOptions = getInstance(configuration.getProject()).getValue(P_ADDITIONAL_JVM_OPTIONS, "");

        JBehaveJUnitConfiguration.Data storedSetting = new JBehaveJUnitConfiguration.Data();
        storedSetting.setRunnerClassName(runnerClassName);
        storedSetting.setStoryFileExtention(storyFileExtention);
        storedSetting.setStoryPathSystemProperty(storyPathSystemProperty);
        storedSetting.setStoryFilePathResolutionStrategy(StoryNameResolverType.valueOf(storyFilePathResolutionStrategy));
        storedSetting.setAdditionalJvmOptions(additionalJvmOptions);
        return storedSetting;
    }

}
