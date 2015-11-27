package tech.idehub.intellij.jbehave.config;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

/**
 * Created by omer on 28/10/2015.
 */
public class JBehaveJUnitConfigurationFactory  extends ConfigurationFactory {

    protected JBehaveJUnitConfigurationFactory(ConfigurationType type) {
        super(type);
    }
    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new JBehaveJUnitConfiguration ("JBehave JUnit", project, this);
    }

    @Override
    public String getName() {
        return "JBehave JUnit";
    }
}
