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

       String runnerClassName =  getInstance(configuration.getProject()).getValue(P_RUNNER_CLASS);
       String storyFileExtention =  getInstance(configuration.getProject()).getValue(P_STORY_FILE_EXTENTION);
       String storyPathSystemProperty =  getInstance(configuration.getProject()).getValue(P_STORY_PATH_SYSTEM_PROPERTY);
        String storyFilePathResolutionStrategy = getInstance(configuration.getProject()).getValue(P_STORY_FILE_PATH_RESOLUTION_STRATEGY);

        String runnerClassName_;
        runnerClassName_ = (runnerClassName == null || runnerClassName.isEmpty()) ? DEFAULT_RUNNER_CLASS : runnerClassName;

        String storyFileExtention_;
        storyFileExtention_ = (storyFileExtention == null || storyFileExtention.isEmpty()) ? DEFAULT_STORY_FILE_EXTENTION : storyFileExtention;

        String storyPathSystemProperty_;
        storyPathSystemProperty_ = (storyPathSystemProperty == null || storyPathSystemProperty.isEmpty()) ? DEFAULT_STORY_PATH_SYSTEM_PROPETY : storyPathSystemProperty;

        StoryNameResolverType storyFilePathResolutionStrategy_;
        storyFilePathResolutionStrategy_ = (storyFilePathResolutionStrategy == null || storyFilePathResolutionStrategy.isEmpty()) ?
                JBehaveJUnitConfiguration.Data.DEFAULT_STORY_PATH_RESOLUTION_TYPE : StoryNameResolverType.valueOf(storyFilePathResolutionStrategy);

        JBehaveJUnitConfiguration.Data storedSetting = new JBehaveJUnitConfiguration.Data();
        storedSetting.setRunnerClassName(runnerClassName_);
        storedSetting.setStoryFileExtention(storyFileExtention_);
        storedSetting.setStoryPathSystemProperty(storyPathSystemProperty_);
        storedSetting.setStoryFilePathResolutionStrategy(storyFilePathResolutionStrategy_);

        return storedSetting;
    }

}
