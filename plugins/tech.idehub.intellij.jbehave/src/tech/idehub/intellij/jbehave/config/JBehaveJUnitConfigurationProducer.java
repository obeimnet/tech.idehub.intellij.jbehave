package tech.idehub.intellij.jbehave.config;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.ConfigurationFromContext;
import com.intellij.execution.junit.JavaRunConfigurationProducerBase;
import com.intellij.execution.junit.PatternConfigurationProducer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.impl.scopes.ModulesScope;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import tech.idehub.intellij.jbehave.util.ResourceNameResolver;
import tech.idehub.intellij.jbehave.util.SettingUtil;
import tech.idehub.intellij.jbehave.util.StoryPath;

import java.util.HashSet;
import java.util.Set;


public class JBehaveJUnitConfigurationProducer<T extends  JBehaveJUnitConfiguration> extends JavaRunConfigurationProducerBase<T> {

    public JBehaveJUnitConfigurationProducer() {
        super(JBehaveJUnitConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(T configuration, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        Location contextLocation = context.getLocation();

        assert contextLocation != null;

        JBehaveJUnitConfiguration.Data configData = SettingUtil.getSettings(configuration);

        boolean canLaunch = JBehaveJUnitLaunchableTester.canLaunchJBehave(configData, context.getModule(), contextLocation);
        if (!canLaunch) {
            return false;
        }

            try {
            Module contextModule = context.getModule();
            Project project = contextModule.getProject();
            Set<Module> modules = new HashSet<>();
            modules.add(contextModule);

            String runnerClass = configData.getRunnerClassName();
            if (runnerClass == null || runnerClass.trim().isEmpty()) {
                return false;
            }

            StoryPath storyPath = ResourceNameResolver.resolve(configData, contextModule, contextLocation);

            ModulesScope  modulesScope = new ModulesScope(modules, project);
            PsiClass testClass = JavaPsiFacade.getInstance(configuration.getProject()).findClass(runnerClass, modulesScope);

            String storyPathSystemProperty = configData.getStoryPathSystemProperty();

            configuration.setModule(contextModule);
            configuration.setName(storyPath.displayName());
            configuration.setMainClass(testClass);
            configuration.setWorkingDirectory("$MODULE_DIR$");
                String jvmoptions = "-ea -D" + storyPathSystemProperty + "=" + storyPath.jvmArgStoryPath() + " " + configData.getAdditionalJvmOptions();
            configuration.setVMParameters(jvmoptions);

            this.setupConfigurationModule(context, configuration);
            return true;
          } catch (Exception exc) {
                exc.printStackTrace();
                return false;
            }
    }

    @Override
    public boolean isConfigurationFromContext(T configuration, ConfigurationContext context) {
        Location contextLocation = context.getLocation();
        assert contextLocation != null;

        if(PatternConfigurationProducer.isMultipleElementsSelected(context)) {
            return false;
        } else {
            JBehaveJUnitConfiguration.Data configData = SettingUtil.getSettings(configuration);
            StoryPath storyPath = ResourceNameResolver.resolve(configData, context.getModule(), contextLocation);
            boolean sameModule = configuration.getConfigurationModule().getModule().getName().equals(context.getModule().getName());
            boolean sameStoryPath =  (storyPath != null && storyPath.displayName().equals(configuration.getName()));
            return  sameStoryPath && sameModule;
        }
    }

    public void onFirstRun(ConfigurationFromContext configuration, ConfigurationContext context, Runnable startRunnable) {
        startRunnable.run();
    }
}
