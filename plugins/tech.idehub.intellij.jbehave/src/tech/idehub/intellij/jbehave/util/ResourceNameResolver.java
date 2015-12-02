package tech.idehub.intellij.jbehave.util;

import com.intellij.execution.Location;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private static final List<String> MAVEN_RESOURCES;
    static {
        MAVEN_RESOURCES = new ArrayList<>();
        //keep insertion order
        MAVEN_RESOURCES.add(SRC_MAIN_RESOURCES_FOLDER);
        MAVEN_RESOURCES.add(SRC_MAIN_JAVA_FOLDER);
        MAVEN_RESOURCES.add(SRC_MAIN_FOLDER);
        MAVEN_RESOURCES.add(SRC_TEST_RESOURCES_FOLDER);
        MAVEN_RESOURCES.add(SRC_TEST_JAVA_FOLDER);
        MAVEN_RESOURCES.add(SRC_TEST_FOLDER);
        MAVEN_RESOURCES.add(SRC_FOLDER);

    }

    public static StoryPath resolve(JBehaveJUnitConfiguration.Data configData, Module module,  Location contextLocation) {

        PsiElement receiver = contextLocation.getPsiElement();
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

        String canonicalPath = "";
        boolean isDirectory = false;

        VirtualFile virtualFile = contextLocation.getVirtualFile();
        if (virtualFile == null && !(receiver instanceof  PsiFileSystemItem)) {
            return  null;
        }

        if (virtualFile != null) {
            isDirectory = virtualFile.isDirectory();
            canonicalPath = virtualFile.getCanonicalPath();
        } else {
            if (receiver instanceof PsiDirectory) {
                PsiDirectory directory = (PsiDirectory) receiver;
                isDirectory = true;
                canonicalPath = directory.getVirtualFile().getCanonicalPath();
            }
        }
        String fileItemName = "";

        switch (configData.getStoryFilePathResolutionStrategy()) {
            case  DEFAULT :
                fileItemName = defaultStoryFilePathResolutionStrategy(module, canonicalPath);
                break;
            case MODULE_RELATIVE:
                fileItemName = moduleRelativeStoryFilePathResolutionStrategy(module, canonicalPath);
                break;
            case ABSOLUTE_PATH:
                fileItemName = absolutePathStoryFilePathResolutionStrategy(module, canonicalPath);
                break;
        }

        if (fileItemName == null || fileItemName.isEmpty()) {
            return null;
        }
        return new StoryPath(fileItemName, isDirectory, configData);
    }

    private static String moduleRelativeStoryFilePathResolutionStrategy(Module module, String canonicalPath) {
        String moduleName = module.getModuleFile().getCanonicalPath().replace(module.getName().concat(".iml"), "");
        return canonicalPath.replace(moduleName, "");
    }

    private static String defaultStoryFilePathResolutionStrategy(Module module, String canonicalPath) {

        String moduleName = module.getModuleFile().getCanonicalPath().replace(module.getName().concat(".iml"), "");
        String itemName =   canonicalPath;

        Set<String> excludedFolders = getExcludedFolders(moduleName);
        if (excludedFolders.contains(itemName)) {
            return "";
        }

        itemName = itemName.replace(moduleName, "");
        for (String mavenResource : MAVEN_RESOURCES) {
            itemName = itemName.replace(mavenResource, "");
        }

        if (itemName.startsWith("/"))  {
            return itemName.substring(1);
        }
        return itemName;
    }

    private static String absolutePathStoryFilePathResolutionStrategy(Module module, String canonicalPath) {
        return canonicalPath;
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
