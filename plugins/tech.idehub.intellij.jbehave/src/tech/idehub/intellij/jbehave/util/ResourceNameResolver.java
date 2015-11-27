package tech.idehub.intellij.jbehave.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by omer on 16/11/2015.
 */
public class ResourceNameResolver {

    private static final String SRC_FOLDER = "src";
    private static final String SRC_MAIN_FOLDER = "src/main";
    private static final String SRC_MAIN_JAVA_FOLDER = "src/main/java";
    private static final String SRC_MAIN_RESOURCES_FOLDER = "src/main/resources";

    private static final String SRC_TEST_FOLDER = "src/test";
    private static final String SRC_TEST_JAVA_FOLDER = "src/test/java";
    private static final String SRC_TEST_RESOURCES_FOLDER = "src/test/resources";

    private static final Set<String> MAVEN_RESOURCES;
    static {
        MAVEN_RESOURCES = new HashSet<>();
        MAVEN_RESOURCES.add(SRC_FOLDER);
        MAVEN_RESOURCES.add(SRC_MAIN_FOLDER);
        MAVEN_RESOURCES.add(SRC_MAIN_JAVA_FOLDER);
        MAVEN_RESOURCES.add(SRC_MAIN_RESOURCES_FOLDER);

        MAVEN_RESOURCES.add(SRC_TEST_FOLDER);
        MAVEN_RESOURCES.add(SRC_TEST_JAVA_FOLDER);
        MAVEN_RESOURCES.add(SRC_TEST_RESOURCES_FOLDER);
    }

    public static StoryPath resolve(JBehaveJUnitConfiguration.Data configData, Module module,  PsiElement receiver) {

        if (receiver == null
                ||!receiver.isPhysical()
                || !receiver.isValid()) {
            return null;
        }

        if (receiver instanceof Project
                || receiver instanceof Module
                ) {
            return null;
        }
        if (!(receiver instanceof  PsiFileSystemItem)) {
            return null;
        }

        PsiFileSystemItem psiFileSystemItem = (PsiFileSystemItem) receiver;
        boolean isDirectory = false;
        String fileItemName = "";

        if (receiver instanceof PsiDirectory) {
            isDirectory = true;
        }

        switch (configData.getStoryFilePathResolutionStrategy()) {
            case  DEFAULT :
                fileItemName = defaultStoryFilePathResolutionStrategy(module, psiFileSystemItem);
                break;
            case MODULE_RELATIVE:
                fileItemName = moduleRelativeStoryFilePathResolutionStrategy(module, psiFileSystemItem);
                break;
            case ABSOLUTE_PATH:
                fileItemName = absolutePathStoryFilePathResolutionStrategy(module, psiFileSystemItem);
                break;
        }

        if (fileItemName == null || fileItemName.isEmpty()) {
            return null;
        }
        return new StoryPath(fileItemName, isDirectory, configData);
    }

    public static String findName(PsiElement sourceElement) {
        if (sourceElement instanceof PsiDirectory) {
            PsiDirectory directory = (PsiDirectory) sourceElement;
            return directory.getName();
        } else if (sourceElement instanceof PsiFile) {
            PsiFile file = (PsiFile) sourceElement;
            return  file.getName();
        }
        return "";
    }

    private static String moduleRelativeStoryFilePathResolutionStrategy(Module module, PsiFileSystemItem psiFileSystemItem) {
        String moduleName = module.getModuleFile().getCanonicalPath().replace(module.getName().concat(".iml"), "");
        String itemName =  psiFileSystemItem.getVirtualFile().getCanonicalPath();
        return itemName.replace(moduleName, "");
    }

    private static String defaultStoryFilePathResolutionStrategy(Module module, PsiFileSystemItem psiFileSystemItem) {

        String moduleName = module.getModuleFile().getCanonicalPath().replace(module.getName().concat(".iml"), "");
        String itemName =   psiFileSystemItem.getVirtualFile().getCanonicalPath();

        Set<String> excludedResoures = getExcludedFolders(moduleName);
        if (excludedResoures.contains(itemName)) {
            return "";
        }

        itemName = itemName.replace(moduleName, "");
        for (String mavenResource : MAVEN_RESOURCES) {
            itemName = itemName.replace(mavenResource, "");
        }

        return itemName;
    }

    private static String absolutePathStoryFilePathResolutionStrategy(Module module, PsiFileSystemItem psiFileSystemItem) {
        return psiFileSystemItem.getVirtualFile().getCanonicalPath();
    }

    private static Set<String> getExcludedFolders(String moduleName) {
        String moduleName_ = moduleName;
        if (moduleName.endsWith("/")) {
            moduleName_ = moduleName.substring(0, moduleName.length() - 1);
        }
        Set<String> set = new HashSet<>();
        for (String mavenResource : MAVEN_RESOURCES) {
            set.add(moduleName_.concat("/").concat(mavenResource));
        }
        return  set;
    }

}
