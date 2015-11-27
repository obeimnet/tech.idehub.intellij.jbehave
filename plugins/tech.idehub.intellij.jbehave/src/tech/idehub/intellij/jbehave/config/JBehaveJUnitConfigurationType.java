package tech.idehub.intellij.jbehave.config;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class JBehaveJUnitConfigurationType implements ConfigurationType {

    @Override
    public String getDisplayName() {
        return "JBehave JUnit";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "JBehave JUnit";
    }

    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/icons/jbehave-junit.png");
    }

    @NotNull
    @Override
    public String getId() {
        return "JBehave-JUnit";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new JBehaveJUnitConfigurationFactory(this)};
    }

    @NotNull
    public static JBehaveJUnitConfigurationType getInstance() {
        return  ConfigurationTypeUtil.findConfigurationType(JBehaveJUnitConfigurationType.class);
    }
}
