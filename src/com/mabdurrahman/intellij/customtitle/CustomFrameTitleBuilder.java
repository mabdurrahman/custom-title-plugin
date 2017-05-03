package com.mabdurrahman.intellij.customtitle;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.FrameTitleBuilder;
import com.mabdurrahman.intellij.customtitle.ui.Settings;
import org.jetbrains.annotations.NotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStreamReader;

/**
 * Created by mabdurrahman on 5/3/17.
 */
public class CustomFrameTitleBuilder extends FrameTitleBuilder {

    private static final String DEFAULT_TEMPLATE_PATTERN_PROJECT = "<% if (projectDefaultTitle) { %><%= projectDefaultTitle %><% } %>";
    private static final String DEFAULT_TEMPLATE_PATTERN_FILE = "<% if (fileDefaultTitle) { %><%= fileDefaultTitle %><% } %>";

    private static FrameTitleBuilder defaultBuilder;

    private String projectPattern;
    private String filePattern;

    private ScriptEngine engine;

    public CustomFrameTitleBuilder() {
        PropertiesComponent prop = PropertiesComponent.getInstance();

        projectPattern = prop.getValue(Settings.TEMPLATE_PATTERN_PROJECT, DEFAULT_TEMPLATE_PATTERN_PROJECT);
        filePattern = prop.getValue(Settings.TEMPLATE_PATTERN_FILE, DEFAULT_TEMPLATE_PATTERN_FILE);

        engine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            // evaluate JavaScript Underscore library
            engine.eval(new InputStreamReader(getClass().getResourceAsStream("/underscore-min.js")));

            // create new JavaScript methods of required templates
            engine.eval("var projectTemplate = _.template('" + projectPattern + "')");
            engine.eval("var fileTemplate = _.template('" + filePattern + "')");
        } catch (Exception e) {
            // we took precaution below
        }
    }

    public static void setDefaultBuilder(FrameTitleBuilder defaultBuilder) {
        CustomFrameTitleBuilder.defaultBuilder = defaultBuilder;
    }

    @Override
    public String getProjectTitle(@NotNull Project project) {

        String projectCustomTitle;
        try {
            String projectDefaultTitle = defaultBuilder.getProjectTitle(project);
            String projectName = project.getName();
            String projectPath = project.getBasePath();

            projectCustomTitle = engine.eval("projectTemplate({" +
                    "projectDefaultTitle: '" + projectDefaultTitle + "'," +
                    "projectName: '" + projectName + "'," +
                    "projectPath: '" + projectPath + "'" +
                    "})").toString();

        } catch (Exception e) {
            projectCustomTitle = defaultBuilder.getProjectTitle(project);
        }

        return projectCustomTitle;
    }

    @Override
    public String getFileTitle(@NotNull Project project, @NotNull VirtualFile virtualFile) {

        String fileCustomTitle;
        try {
            String projectDefaultTitle = defaultBuilder.getProjectTitle(project);
            String projectName = project.getName();
            String projectPath = project.getBasePath();

            String fileDefaultTitle = defaultBuilder.getFileTitle(project, virtualFile);
            String fileName = virtualFile.getName();
            String filePath = virtualFile.getPath();
            String fileExt = virtualFile.getExtension();

            fileCustomTitle = engine.eval("fileTemplate({" +
                    "projectDefaultTitle: '" + projectDefaultTitle + "'," +
                    "projectName: '" + projectName + "'," +
                    "projectPath: '" + projectPath + "'," +
                    "fileDefaultTitle: '" + fileDefaultTitle + "'," +
                    "fileName: '" + fileName + "'," +
                    "filePath: '" + filePath + "'," +
                    "fileExt: '" + fileExt + "'" +
                    "})").toString();

        } catch (Exception e) {
            fileCustomTitle = defaultBuilder.getFileTitle(project, virtualFile);
        }

        return fileCustomTitle;
    }
}
