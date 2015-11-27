package tech.idehub.intellij.jbehave.editor;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;
import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;
import tech.idehub.intellij.jbehave.util.SettingUtil;

import javax.swing.*;

import static tech.idehub.intellij.jbehave.util.PreferenceConstants.*;

public class JBehaveJUnitRunnerPreferencesEditor<E extends JBehaveJUnitConfiguration> extends SettingsEditor<E> {
    private JPanel myPanel;
    private JTextField runnerClass;
    private JTextField storyFileExtention;
    private JTextField storyPathSystemProperty;

    private JRadioButton strategyDefault;
    private JRadioButton strategyModuleRelative;
    private JRadioButton strategyAbsolutePath;

    private LabeledComponent<ComponentWithBrowseButton> runnerClassName;

    public JBehaveJUnitRunnerPreferencesEditor() {
    }

    @Override
    protected void resetEditorFrom(E configuration) {
        JBehaveJUnitConfiguration.Data myData =    SettingUtil.getSettings(configuration);
        setData(myData);
    }

    @Override
    protected void applyEditorTo(E configuration) throws ConfigurationException {

        JBehaveJUnitConfiguration.Data configData = configuration.getData();

        configData.setRunnerClassName(runnerClass.getText());
        configData.setStoryFileExtention(storyFileExtention.getText());
        configData.setStoryPathSystemProperty(storyPathSystemProperty.getText());
        configData.setStoryFilePathResolutionStrategy(selectedStoryFilePathResolutionStrategy());

        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_RUNNER_CLASS, configData.getRunnerClassName());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_FILE_EXTENTION, configData.getStoryFileExtention());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_PATH_SYSTEM_PROPERTY, configData.getStoryPathSystemProperty());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_FILE_PATH_RESOLUTION_STRATEGY, configData.getStoryFilePathResolutionStrategy().name());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        runnerClassName = new LabeledComponent<ComponentWithBrowseButton>();
        runnerClassName.setComponent(new TextFieldWithBrowseButton());
    }

    public void setData(JBehaveJUnitConfiguration.Data data) {
        runnerClass.setText(data.getRunnerClassName());
        storyFileExtention.setText(data.getStoryFileExtention());
        storyPathSystemProperty.setText(data.getStoryPathSystemProperty());
    }

    public void getData(JBehaveJUnitConfiguration.Data data) {
        data.setRunnerClassName(runnerClass.getText());
        data.setStoryFileExtention(storyFileExtention.getText());
        data.setStoryPathSystemProperty(storyPathSystemProperty.getText());
    }

    public boolean isModified(JBehaveJUnitConfiguration.Data data) {
        if (runnerClass.getText() != null ? !runnerClass.getText().equals(data.getRunnerClassName()) : data.getRunnerClassName() != null)
            return true;
        if (storyFileExtention.getText() != null ? !storyFileExtention.getText().equals(data.getStoryFileExtention()) : data.getStoryFileExtention() != null)
            return true;
        if (storyPathSystemProperty.getText() != null ? !storyPathSystemProperty.getText().equals(data.getStoryPathSystemProperty()) : data.getStoryPathSystemProperty() != null)
            return true;
        return false;
    }

    private StoryNameResolverType selectedStoryFilePathResolutionStrategy() {
        if (strategyModuleRelative.isSelected()) {
            return StoryNameResolverType.MODULE_RELATIVE;
        }

        if (strategyAbsolutePath.isSelected()) {
            return StoryNameResolverType.ABSOLUTE_PATH;
        }

        return StoryNameResolverType.DEFAULT;
    }

}

