package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.model.settings.*;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterAlt;
import ru.flc.service.shopautolink.view.table.SettingsTable;
import ru.flc.service.shopautolink.view.table.SettingsTableModel;

import javax.swing.*;
import java.awt.*;
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
    
    private SettingsTableModel tableModel;
    private SettingsTable table;

    private JButton okButton;
    private JButton cancelButton;

    public SettingsDialog(MainFrame parent, ResourceManager resourceManager) throws Exception
    {
        super(parent, "", true);
        
        this.parent = parent;
        
        this.resourceManager = resourceManager;
        this.titleAdjuster = new TitleAdjuster();

        dbSettings = new DatabaseSettingsAlt(this.resourceManager);
        fileSettings = new FileSettingsAlt(this.resourceManager);

        settingsList = new LinkedList<>();
        settingsList.add(dbSettings);
        settingsList.add(fileSettings);

        initComponents();

        setResizable(false);
    }

    private void initComponents()
    {
    	add(initSettingsPanel(), BorderLayout.CENTER);
    	add(initCommandPanel(), BorderLayout.SOUTH);

        titleAdjuster.registerComponent(this, new Title(resourceManager, Constants.KEY_SETTINGS_DIALOG));
    }
    
    private JPanel initSettingsPanel()
	{
		tableModel = new SettingsTableModel(resourceManager, ParameterAlt.getTitleKeys(), null);
		
		table = new SettingsTable(tableModel);
		
		JScrollPane tablePane = new JScrollPane(table);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(tablePane, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel initCommandPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		
		initButtons();
		
		panel.add(okButton);
		panel.add(cancelButton);
		
		return panel;
	}

    private void initButtons()
    {
        okButton = new JButton();
        titleAdjuster.registerComponent(okButton, new Title(resourceManager, Constants.KEY_BUTTON_OK));
        okButton.setIcon(resourceManager.getImageIcon("button-ok.png"));
        okButton.addActionListener(event -> saveAndExit());

        cancelButton = new JButton();
        titleAdjuster.registerComponent(cancelButton, new Title(resourceManager, Constants.KEY_BUTTON_CANCEL));
        cancelButton.setIcon(resourceManager.getImageIcon("button-cancel.png"));
        cancelButton.addActionListener(event -> exit());
    }

    @Override
    public void setVisible(boolean b)
    {
        if (b)
		{
			List<ParameterAlt> allSettingsList = new LinkedList<>();
			tableModel.clear();
			
			for (TransmissiveSettings settings : settingsList)
			{
				try
				{
					settings.load();
				}
				catch (Exception e)
				{
					parent.log(e);
				}
				
				allSettingsList.addAll(settings.getParameterList());
			}
			
			if (!allSettingsList.isEmpty())
				tableModel.addAllRows(allSettingsList);

			titleAdjuster.resetComponents();
			pack();
			setLocationRelativeTo(parent);
		}

        super.setVisible(b);
    }

    public void saveAndExit()
    {
        for (TransmissiveSettings settings : settingsList)
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
