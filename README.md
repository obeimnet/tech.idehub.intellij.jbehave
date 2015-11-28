This plugin allows running JBehave story files as JUnit tests using context menu from explorer view or an editor.

![](https://github.com/obeimnet/tech.idehub.intellij.jbehave/blob/master/docs/images/run-from-context.png)

# Installation #

TODO

----------

# How it Works #

The plugin passes the selected story file (or a folder containing story files) as a system property (default: jbehave.story.path) to a custom JBehave runner class.

- You will need to provide a custom JBehave JUnit runner class as in the following example:

**Example:** 
  <pre>
  @RunWith(......)
  public class MyStoryRunner extends JUnitStories {
   @Override
   protected List<String> storyPaths() {
	List<String> stories = new ArrayList<>();
    stories.add(System.getProperty("jbehave.story.path"));
    return stories;
   }
 } </pre>

----------

# Configuring the Plugin #

- After installation, go to Run/Debug Edit Configurations.

![](https://github.com/obeimnet/tech.idehub.intellij.jbehave/blob/master/docs/images/run-debug-edit-configuration.png) 

- On the Run/Debug configuration screen, you will see a new item labelled 'JBehae Junit' as in the following screen.

![](https://github.com/obeimnet/tech.idehub.intellij.jbehave/blob/master/docs/images/jbehave-junit-run-debug-configuration.png)

- Now you will need to enter yuor custom JBehave JUnit runner class.

![](https://github.com/obeimnet/tech.idehub.intellij.jbehave/blob/master/docs/images/jbehave-junit-run-debug-configuration-2.png)

There are three options for Story Path Resolution Strategy.

- Default
- <pre>
  Passes story file name relative to moudle. 
  If the module has default maven settings, maven resource folders will be ignored.
  Example: For story file located in c:/myProject/myModule1/src/test/resources/myStories/group1/blah.story,
           the plugin will set system property jbehave.story.path to "myStories/group1/blah.story" 
           
</pre>

- The other two options are self explanatory.
