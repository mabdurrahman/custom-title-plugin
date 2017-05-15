package com.mabdurrahman.intellij.customtitle.ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.mabdurrahman.intellij.customtitle.TitleComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by mabdurrahman on 5/3/17.
 */
public class Settings implements Configurable {

    public static final String TEMPLATE_PATTERN_PROJECT = "CustomProjectTitleTemplate";
    public static final String TEMPLATE_PATTERN_FILE = "CustomFileTitleTemplate";

    private JTextField projectTemplatePattern;
    private JTextField fileTemplatePattern;

    private JPanel rootPanel;
    private JTextPane usageHelp;

    @Nls
    @Override
    public String getDisplayName() {
        return "Custom Title";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return rootPanel;
    }

    @Override
    public boolean isModified() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        String storedProjectTemplate = prop.getValue(TEMPLATE_PATTERN_PROJECT, "");
        String newProjectTemplate = projectTemplatePattern.getText();

        String storedFileTemplate = prop.getValue(TEMPLATE_PATTERN_FILE, "");
        String newFileTemplate = fileTemplatePattern.getText();

        return !storedProjectTemplate.equals(newProjectTemplate) || !storedFileTemplate.equals(newFileTemplate);
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        prop.setValue(TEMPLATE_PATTERN_PROJECT, projectTemplatePattern.getText());
        prop.setValue(TEMPLATE_PATTERN_FILE, fileTemplatePattern.getText());

        TitleComponent.updateSettings();
    }

    @Override
    public void reset() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        projectTemplatePattern.setText(prop.getValue(TEMPLATE_PATTERN_PROJECT));
        fileTemplatePattern.setText(prop.getValue(TEMPLATE_PATTERN_FILE));
    }

    @Override
    public void disposeUIResources() {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
