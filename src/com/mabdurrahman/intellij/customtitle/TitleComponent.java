package com.mabdurrahman.intellij.customtitle;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.wm.impl.FrameTitleBuilder;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabdurrahman on 5/3/17.
 */
public class TitleComponent implements ApplicationComponent {

    public static final String FRAME_TITLE_BUILDER = "com.intellij.openapi.wm.impl.FrameTitleBuilder";

    private static List<SettingChangeListener> listenerList = new ArrayList<>();

    @Override
    public void initComponent() {
        // use https://github.com/Steve-Murphy/mavenversion-plugin as a template.
        MutablePicoContainer picoContainer = (MutablePicoContainer) ApplicationManager.getApplication().getPicoContainer();
        CustomFrameTitleBuilder.setDefaultBuilder((FrameTitleBuilder) picoContainer.getComponentInstance(FRAME_TITLE_BUILDER));
        picoContainer.unregisterComponent(FRAME_TITLE_BUILDER);
        picoContainer.registerComponentImplementation(FRAME_TITLE_BUILDER, CustomFrameTitleBuilder.class);
    }

    @Override
    public void disposeComponent() {}

    @NotNull
    @Override
    public String getComponentName() {
        return getClass().getSimpleName();
    }

    public static void addSettingChangeListener(SettingChangeListener listener) {
        listenerList.add(listener);
    }

    public static void updateSettings() {
        for (SettingChangeListener listener : listenerList) {
            listener.onSettingsChange();
        }
    }

    public interface SettingChangeListener {
        void onSettingsChange();
    }
}
