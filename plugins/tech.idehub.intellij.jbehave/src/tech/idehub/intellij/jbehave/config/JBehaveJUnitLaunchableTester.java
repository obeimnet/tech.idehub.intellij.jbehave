package tech.idehub.intellij.jbehave.config;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import tech.idehub.intellij.jbehave.util.ResourceNameResolver;
import tech.idehub.intellij.jbehave.util.StoryPath;

import java.util.ArrayList;
import java.util.List;

import static tech.idehub.intellij.jbehave.util.ResourceNameResolver.*;

/**
 * Created by omer on 19/11/2015.
 */
public class JBehaveJUnitLaunchableTester {

    static protected boolean canLaunchJBehave(JBehaveJUnitConfiguration.Data configData, Module module, PsiElement receiver)  {


        StoryPath storyPath = resolve(configData, module, receiver);
        if (storyPath == null || storyPath.getPath() == null || storyPath.getPath().isEmpty()) {
            return  false;
        }

        if (receiver instanceof PsiDirectory) {
            PsiDirectory directory = (PsiDirectory) receiver;
            List<String> stories = new ArrayList<>();
            containsStoryFile(directory, configData.getStoryFileExtention(), stories);
            return !stories.isEmpty();

        } else if (receiver instanceof  PsiFile) {
            PsiFile psiFile = (PsiFile) receiver;
            return  psiFile.getName().endsWith(configData.getStoryFileExtention());
        }

        return false;
    }


    static private void containsStoryFile(PsiElement element, String storyFileExtention, List<String> stories)  {

        PsiElement[] members = element.getChildren();
        if (members == null || members.length == 0 || !stories.isEmpty()) {
            return;
        }

        for (PsiElement member : members) {
            if (member instanceof PsiDirectory) {
                containsStoryFile(member, storyFileExtention, stories);
            } else  {
                if (member.getContainingFile().getName().endsWith(storyFileExtention)) {
                    stories.add(member.getContainingFile().getName());
                    break;
                }
            }
        }
    }
}
