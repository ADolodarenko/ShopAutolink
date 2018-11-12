package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrameButtonsManager
{
    private Dimension prefButtonsPanelSize;
    private Dimension maxButtonSize;

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;

    private JButton loadPositionsButton;
    private JButton linkPositionsButton;
    private JButton settingsButton;

    public MainFrameButtonsManager(Dimension prefButtonsPanelSize, Dimension maxButtonSize,
                                   ResourceManager resourceManager, TitleAdjuster titleAdjuster)
    {
        this.prefButtonsPanelSize = prefButtonsPanelSize;
        this.maxButtonSize = maxButtonSize;

        this.resourceManager = resourceManager;
        this.titleAdjuster = titleAdjuster;
    }

    public JButton getLoadPositionsButton(ActionListener listener)
    {
        if (loadPositionsButton == null)
        {
            loadPositionsButton = new JButton();
            loadPositionsButton.setPreferredSize(maxButtonSize);
            loadPositionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            loadPositionsButton.setMaximumSize(maxButtonSize);
            titleAdjuster.registerComponent(loadPositionsButton, new Title(resourceManager, "Load_Button_Title"));
            loadPositionsButton.setIcon(resourceManager.getImageIcon("button-upload.png"));
        }

        resetActionListener(loadPositionsButton, listener);

        return loadPositionsButton;
    }

    public JButton getLinkPositionsButton(ActionListener listener)
    {
        if (linkPositionsButton == null)
        {
            linkPositionsButton = new JButton();
            linkPositionsButton.setPreferredSize(maxButtonSize);
            linkPositionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            linkPositionsButton.setMaximumSize(maxButtonSize);
            titleAdjuster.registerComponent(linkPositionsButton, new Title(resourceManager, "Link_Button_Title"));
            linkPositionsButton.setIcon(resourceManager.getImageIcon("button-link.png"));
        }

        resetActionListener(linkPositionsButton, listener);

        return linkPositionsButton;
    }

    public JButton getSettingsButton(ActionListener listener)
    {
        if (settingsButton == null)
        {
            settingsButton = new JButton();
            settingsButton.setPreferredSize(maxButtonSize);
            settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            settingsButton.setMaximumSize(maxButtonSize);
            titleAdjuster.registerComponent(settingsButton, new Title(resourceManager, "Settings_Button_Title"));
            settingsButton.setIcon(resourceManager.getImageIcon("button-settings.png"));
        }

        resetActionListener(settingsButton, listener);

        return settingsButton;
    }

    public void blockButtons()
    {
        setButtonsEnabled(false);
    }

    public void activateButtons()
    {
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean enabled)
    {
        loadPositionsButton.setEnabled(enabled);
        linkPositionsButton.setEnabled(enabled);
        settingsButton.setEnabled(enabled);
    }

    private void clearActionListenersList(JButton button)
    {
        for (ActionListener listener : button.getActionListeners())
            button.removeActionListener(listener);
    }

    private void resetActionListener(JButton button, ActionListener listener)
    {
        clearActionListenersList(button);

        if (listener != null)
            button.addActionListener(listener);
    }
}
