package tech.idehub.intellij.jbehave.util;

import tech.idehub.intellij.jbehave.config.JBehaveJUnitConfiguration;

/**
 * Created by omer on 16/11/2015.
 */
public class StoryPath {

    private String path;
    private final boolean folder;
    private final JBehaveJUnitConfiguration.Data configData;

    StoryPath(String path,  boolean folder, JBehaveJUnitConfiguration.Data configData) {
        this.path = path;
        this.folder = folder;
        this.configData = configData;
    }


    public String getPath() {
        return this.path;
    }

    public boolean isFolder() {
        return this.folder;
    }

    public String displayName() {
        String path_ = getPath();
        if (path.startsWith("/")) {
            path_ = path_.substring(1);
        }
        return path_.toLowerCase().replace('/', '.').replace(":", "");
    }

    public String jvmArgStoryPath() {
        if (isFolder()) {
            return getPath().concat("/**/*").concat(configData.getStoryFileExtention());
        }

        return getPath();
    }
}