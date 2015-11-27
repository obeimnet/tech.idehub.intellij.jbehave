package tech.idehub.intellij.jbehave.config;

import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.JavaRunConfigurationExtensionManager;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.junit.JUnitConfiguration;
import com.intellij.execution.junit2.configuration.JUnitConfigurable;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import tech.idehub.intellij.jbehave.editor.JBehaveJUnitRunnerPreferencesEditor;

import static tech.idehub.intellij.jbehave.util.PreferenceConstants.StoryNameResolverType;
import static tech.idehub.intellij.jbehave.util.PreferenceConstants.StoryNameResolverType.DEFAULT;

/**
 * Created by omer on 27/10/2015.
 */
public class JBehaveJUnitConfiguration extends JUnitConfiguration {

    private final JBehaveJUnitConfiguration.Data myData;

    public JBehaveJUnitConfiguration(String name, Project project, ConfigurationFactory configurationFactory) {
        super(name, project, configurationFactory);
        this.myData = new Data();
    }

    public JBehaveJUnitConfiguration.Data getData() {
        return myData;
    }

    @Override
    public SettingsEditorGroup<JBehaveJUnitConfiguration> getConfigurationEditor() {
        SettingsEditorGroup group = new SettingsEditorGroup();
        group.addEditor("JBehave JUnit Runner", new JBehaveJUnitRunnerPreferencesEditor());
        group.addEditor(ExecutionBundle.message("run.configuration.configuration.tab.title", new Object[0]), new JUnitConfigurable(this.getProject()));
        JavaRunConfigurationExtensionManager.getInstance().appendEditors(this, group);
        group.addEditor(ExecutionBundle.message("logs.tab.title", new Object[0]), new LogConfigurationPanel());
        return group;
    }

    @Override
    protected ModuleBasedConfiguration createInstance() {
        return new JBehaveJUnitConfiguration(this.getName(), this.getProject(), JBehaveJUnitConfigurationType.getInstance().getConfigurationFactories()[0]);
    }

    public static class Data implements Cloneable {

        public static final String DEFAULT_RUNNER_CLASS = "";
        public static final String DEFAULT_STORY_FILE_EXTENTION = ".story";
        public static final String DEFAULT_STORY_PATH_SYSTEM_PROPETY = "jbehave.story.path";
        public static final StoryNameResolverType DEFAULT_STORY_PATH_RESOLUTION_TYPE = DEFAULT;

        private String runnerClassName;
        private String storyFileExtention;
        private String storyPathSystemProperty;
        private StoryNameResolverType storyFilePathResolutionStrategy;

        public String getRunnerClassName() {
            return runnerClassName;
        }

        public void setRunnerClassName(String runnerClassName) {
            this.runnerClassName = runnerClassName;
        }

        public String getStoryFileExtention() {
            return storyFileExtention;
        }

        public void setStoryFileExtention(String storyFileExtention) {
            this.storyFileExtention = storyFileExtention;
        }

        public String getStoryPathSystemProperty() {
            return storyPathSystemProperty;
        }

        public void setStoryPathSystemProperty(String storyPathSystemProperty) {
            this.storyPathSystemProperty = storyPathSystemProperty;
        }

        public StoryNameResolverType getStoryFilePathResolutionStrategy() {
            return storyFilePathResolutionStrategy;
        }

        public void setStoryFilePathResolutionStrategy(StoryNameResolverType storyFilePathResolutionStrategy) {
            this.storyFilePathResolutionStrategy = storyFilePathResolutionStrategy;
        }
    }
}
