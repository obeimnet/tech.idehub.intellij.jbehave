package tech.idehub.intellij.jbehave.editor;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.NotNull;
import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;
import tech.idehub.intellij.jbehave.util.SettingUtil;

import javax.swing.*;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static tech.idehub.intellij.jbehave.util.PreferenceConstants.*;

public class JBehaveJUnitRunnerPreferencesEditor<E extends JBehaveJUnitConfiguration> extends SettingsEditor<E> {
    private JPanel myPanel;
    private JTextField runnerClass;
    private JTextField storyFileExtention;
    private JTextField storyPathSystemProperty;

    private JRadioButton strategyDefault;
    private JRadioButton strategyModuleRelative;
    private JRadioButton strategyAbsolutePath;
    private JTextField additionalJvmOptions;

    private LabeledComponent<ComponentWithBrowseButton> runnerClassName;

    public JBehaveJUnitRunnerPreferencesEditor() {
    }

    @Override
    protected void resetEditorFrom(E configuration) {
        JBehaveJUnitConfiguration.Data myData = SettingUtil.getSettings(configuration);
        resetEditorWith(myData);
    }

    @Override
    protected void applyEditorTo(E configuration) throws ConfigurationException {

        JBehaveJUnitConfiguration.Data configData = configuration.getData();

        configData.setRunnerClassName(trimToEmpty(runnerClass.getText()));
        configData.setStoryFileExtention(trimToEmpty(storyFileExtention.getText()));
        configData.setStoryPathSystemProperty(trimToEmpty(storyPathSystemProperty.getText()));
        configData.setStoryFilePathResolutionStrategy(selectedStoryFilePathResolutionStrategy());
        configData.setAdditionalJvmOptions(trimToEmpty(additionalJvmOptions.getText()));

        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_RUNNER_CLASS, configData.getRunnerClassName());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_FILE_EXTENTION, configData.getStoryFileExtention());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_PATH_SYSTEM_PROPERTY, configData.getStoryPathSystemProperty());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_STORY_FILE_PATH_RESOLUTION_STRATEGY, configData.getStoryFilePathResolutionStrategy().name());
        PropertiesComponent.getInstance(configuration.getProject()).setValue(P_ADDITIONAL_JVM_OPTIONS, configData.getAdditionalJvmOptions());

    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        runnerClassName = new LabeledComponent<>();
        runnerClassName.setComponent(new TextFieldWithBrowseButton());
    }

    public void resetEditorWith(JBehaveJUnitConfiguration.Data data) {
        runnerClass.setText(data.getRunnerClassName());
        storyFileExtention.setText(data.getStoryFileExtention());
        storyPathSystemProperty.setText(data.getStoryPathSystemProperty());
        additionalJvmOptions.setText(data.getAdditionalJvmOptions());

        switch (data.getStoryFilePathResolutionStrategy()) {
            case DEFAULT:
                strategyDefault.setSelected(true);
                break;
            case MODULE_RELATIVE:
                strategyModuleRelative.setSelected(true);
                break;
            case ABSOLUTE_PATH:
                strategyAbsolutePath.setSelected(true);
                break;
        }

    }

   /* public void getData(JBehaveJUnitConfiguration.Data data) {
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
    }*/

    private StoryNameResolverType selectedStoryFilePathResolutionStrategy() {
        if (strategyModuleRelative.isSelected()) {
            return StoryNameResolverType.MODULE_RELATIVE;
        }

        if (strategyAbsolutePath.isSelected()) {
            return StoryNameResolverType.ABSOLUTE_PATH;
        }

        return StoryNameResolverType.DEFAULT;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        myPanel = new JPanel();
        myPanel.setLayout(new FormLayout("fill:d:noGrow,left:4dlu:noGrow,fill:d:grow", "center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        final JLabel label1 = new JLabel();
        label1.setText("JBehave JUnit Story Runner Class:");
        CellConstraints cc = new CellConstraints();
        myPanel.add(label1, cc.xy(1, 1));
        runnerClass = new JTextField();
        runnerClass.setText("");
        runnerClass.setToolTipText("Enter your custom JBehave JUnit runner class.");
        myPanel.add(runnerClass, cc.xy(3, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label2 = new JLabel();
        label2.setText("JBehave Story File Extention (default = .story)");
        myPanel.add(label2, cc.xy(1, 3));
        storyFileExtention = new JTextField();
        storyFileExtention.setText("");
        myPanel.add(storyFileExtention, cc.xy(3, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label3 = new JLabel();
        label3.setText("Story Path System Property (default = jbehave.story.path):");
        myPanel.add(label3, cc.xy(1, 5));
        storyPathSystemProperty = new JTextField();
        myPanel.add(storyPathSystemProperty, cc.xy(3, 5, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label4 = new JLabel();
        label4.setText("Story Path Resolution Strategy");
        myPanel.add(label4, cc.xy(1, 7));
        strategyDefault = new JRadioButton();
        strategyDefault.setSelected(true);
        strategyDefault.setText("Default");
        myPanel.add(strategyDefault, cc.xy(3, 9));
        strategyAbsolutePath = new JRadioButton();
        strategyAbsolutePath.setText("Absolute Path");
        myPanel.add(strategyAbsolutePath, cc.xy(3, 13));
        strategyModuleRelative = new JRadioButton();
        strategyModuleRelative.setText("Relative to Module Location");
        myPanel.add(strategyModuleRelative, cc.xy(3, 11));
        additionalJvmOptions = new JTextField();
        additionalJvmOptions.setText("");
        myPanel.add(additionalJvmOptions, cc.xy(3, 15, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label5 = new JLabel();
        label5.setText("Additional jvm options");
        myPanel.add(label5, cc.xy(1, 15));
        label1.setLabelFor(runnerClass);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(strategyDefault);
        buttonGroup.add(strategyModuleRelative);
        buttonGroup.add(strategyAbsolutePath);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return myPanel;
    }
}

