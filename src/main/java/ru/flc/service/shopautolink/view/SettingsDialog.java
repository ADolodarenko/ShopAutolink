package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.model.settings.*;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class SettingsDialog extends JDialog
{
    private MainFrame parent;

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;

    private DatabaseSettingsAlt dbSettings;
    private FileSettingsAlt fileSettings;
    private List<TransmissiveSettings> settingsList;

    private JButton okButton;
    private JButton cancelButton;

    public SettingsDialog(MainFrame parent)
    {
        super(parent, "", true);

        dbSettings = new DatabaseSettingsAlt(resourceManager);
        fileSettings = new FileSettingsAlt(resourceManager);

        settingsList = new LinkedList<>();
        settingsList.add(dbSettings);
        settingsList.add(fileSettings);

        initComponents();
    }

    private void initComponents()
    {

    }

    private void initButtons()
    {
        okButton = new JButton();
        titleAdjuster.registerComponent(okButton, new Title(resourceManager, "Ok_Button_Title"));
        okButton.setIcon(resourceManager.getImageIcon("button-ok.png"));
        okButton.addActionListener(event -> saveAndExit());

        cancelButton = new JButton();
        titleAdjuster.registerComponent(cancelButton, new Title(resourceManager, "Cancel_Button_Title"));
        cancelButton.setIcon(resourceManager.getImageIcon("button-cancel.png"));
        cancelButton.addActionListener(event -> exit());
    }

    @Override
    public void setVisible(boolean b)
    {
        if (b)
            for (Settings settings : settingsList)
            {
                try
                {
                    settings.load();
                }
                catch (Exception e)
                {
                    parent.log(e);
                }
            }

        super.setVisible(b);
    }

    public void saveAndExit()
    {
        for (Settings settings : settingsList)
        {
            try
            {
                settings.save();
            }
            catch (Exception e)
            {
                parent.log(e);
            }
        }

        exit();
    }

    public void exit()
    {
        setVisible(false);
        parent.setFocus();
    }
}
