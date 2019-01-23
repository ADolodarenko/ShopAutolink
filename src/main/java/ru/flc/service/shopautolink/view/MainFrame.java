package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.model.LogEventWriter;
import ru.flc.service.shopautolink.model.settings.*;
import ru.flc.service.shopautolink.view.dialog.SettingsDialog;
import ru.flc.service.shopautolink.view.table.LogEventTable;
import ru.flc.service.shopautolink.view.table.LogEventTableModel;
import ru.flc.service.shopautolink.model.accessobject.AccessObjectFactory;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkFao;
import ru.flc.service.shopautolink.model.logic.TitleLinkLoader;
import ru.flc.service.shopautolink.model.logic.TitleLinkProcessor;
import ru.flc.service.shopautolink.view.table.listener.LinkMouseListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.io.File;

public class MainFrame extends JFrame
{
    private static final Dimension WIN_MIN_SIZE = new Dimension(400, 350);
    private static final Dimension WIN_PREF_SIZE = new Dimension(600, 400);
    private static final Dimension COMMAND_PANEL_PREF_SIZE = new Dimension(130, 400);
    private static final Dimension BUTTON_MAX_SIZE = new Dimension(130, 60);

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;
    private MainFrameButtonsManager buttonsManager;
    
    private ViewSettings viewSettings;
    private DatabaseSettings dbSettings;
    private FileSettings fileSettings;

    private SettingsDialog settingsDialog;
    
    private TitleLinkFao fileObject;
    private TitleLinkDao dataObject;
    
    private TitleLinkLoader linkLoader;
    private TitleLinkProcessor linkProcessor;

    private JLabel processLabel;
    private LogEventTableModel logTableModel;
    private LogEventTable logTable;

    public MainFrame()
    {
		resourceManager = SAResourceManager.getInstance();

		loadAllSettings();
        initComponents();
        initFrame();
    }

    private void loadAllSettings()
    {
        loadViewSettings();
        loadDatabaseSettings();
        loadFileSettings();
    }

    private void loadViewSettings()
	{
		try
		{
			viewSettings = new ViewSettings(resourceManager, WIN_PREF_SIZE);
			viewSettings.load();
		}
		catch (Exception e)
		{
			log(e);
		}
	}

	private void loadDatabaseSettings()
	{
		try
		{
			dbSettings = new DatabaseSettings(resourceManager);
		}
		catch (Exception e)
		{
			log(e);
		}
	}

	private void loadFileSettings()
	{
		try
		{
			fileSettings = new FileSettings(resourceManager);
			fileSettings.load();
		}
		catch (Exception e)
		{
			log(e);
		}
	}

    private void initComponents()
    {
    	resourceManager.setCurrentLocale(viewSettings.getAppLocale());

        titleAdjuster = new TitleAdjuster();
        buttonsManager = new MainFrameButtonsManager(COMMAND_PANEL_PREF_SIZE, BUTTON_MAX_SIZE,
                resourceManager, titleAdjuster);

        ViewUtils.setDialogOwner(this);
		ViewUtils.adjustDialogs();

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());
	
		setIconImage(resourceManager.getImageIcon(Constants.ICON_NAME_LINKING).getImage());
		titleAdjuster.registerComponent(this, new Title(resourceManager, Constants.KEY_MAIN_FRAME));
        
        titleAdjuster.resetComponents();

        pack();
    }

    private void initFrame()
    {
        setMinimumSize(WIN_MIN_SIZE);
        setPreferredSize(WIN_PREF_SIZE);

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                Dimension currentDim = MainFrame.this.getSize();
                Dimension minimumDim = MainFrame.this.getMinimumSize();

                if (currentDim.width < minimumDim.width)
                    currentDim.width = minimumDim.width;
                if (currentDim.height < minimumDim.height)
                    currentDim.height = minimumDim.height;

                MainFrame.this.setSize(currentDim);
            }
        });
        
        try
        {
            viewSettings.load();
            
            if (viewSettings.isMainWindowMaximized())
                setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            else
                setBounds(viewSettings.getMainWindowPosition().x,
                        viewSettings.getMainWindowPosition().y,
                        viewSettings.getMainWindowSize().width,
                        viewSettings.getMainWindowSize().height);
        }
        catch (Exception e)
        {
            log(e);
        }

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                cancelProcesses();
                updateViewSettings();
            }
        });
    }

    private void cancelProcesses()
	{
		cancelProcess(linkLoader);
		cancelProcess(linkProcessor);
	}

	private void cancelProcess(SwingWorker<?, ?> worker)
	{
		if (worker != null && !worker.isDone() && !worker.isCancelled())
			worker.cancel(false);
	}

    public void reloadSettings()
	{
		reloadViewSettings();
		reloadFileSettings();
	}

    public void reloadViewSettings()
	{
		if (viewSettings != null)
		{
			reloadSpecificSettings(viewSettings);
			
			resourceManager.setCurrentLocale(viewSettings.getAppLocale());
			
			repaintFrame();

			ViewUtils.adjustDialogs();
		}
	}
	
	public void repaintFrame()
	{
		titleAdjuster.resetComponents();
		
		if (logTableModel != null)
			logTableModel.fireTableStructureChanged();
			
		if (logTable != null)
			logTable.setColumnAppearance();
		
		validate();
	}

	public void reloadFileSettings()
	{
		if (fileSettings != null)
			reloadSpecificSettings(fileSettings);
	}

	private void reloadSpecificSettings(Settings settings)
	{
		try
		{
			settings.load();
		}
		catch (Exception e)
		{
			log(e);
		}
	}

    private void updateViewSettings()
    {
        viewSettings.setMainWindowMaximized(getExtendedState() == JFrame.MAXIMIZED_BOTH);
        viewSettings.setMainWindowPosition(getBounds().getLocation());
        viewSettings.setMainWindowSize(getSize());

        updateSpecificSettings(viewSettings);
    }

    private void updateSpecificSettings(Settings settings)
	{
		try
		{
			settings.save();
		}
		catch (Exception e)
		{
			log(e);
		}
	}

    public JPanel initCommandPanel()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(buttonsManager.getLoadPositionsButton(new LoadTitleLinksListener()));
		panel.add(Box.createRigidArea(new Dimension(0, 1)));
        panel.add(buttonsManager.getLinkPositionsButton(new ProcessTitleLinksListener()));
		panel.add(Box.createRigidArea(new Dimension(0, 1)));
		panel.add(buttonsManager.getSettingsButton(new ShowSettingsListener()));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        processLabel = new JLabel();
        processLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(processLabel);

        return panel;
    }

    private JPanel initLogPanel()
    {
    	LogEvent.setResourceManager(resourceManager);

        logTableModel = new LogEventTableModel(resourceManager, null);
        logTable = new LogEventTable(logTableModel);

        if (isDesktopAvailable())
        	logTable.addMouseListener(new LinkMouseListener(resourceManager, new Cursor(Cursor.HAND_CURSOR)));


        JScrollPane tablePane = new JScrollPane(logTable);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "", TitledBorder.TOP, TitledBorder.CENTER));
        titleAdjuster.registerComponent(panel, new Title(resourceManager, Constants.KEY_LOG_PANEL));
        panel.add(tablePane, BorderLayout.CENTER);

        return panel;
    }

    private boolean isDesktopAvailable()
	{
		if (!Desktop.isDesktopSupported())
			return false;

		Desktop desktop = Desktop.getDesktop();

		return desktop.isSupported(Desktop.Action.BROWSE);
	}

    private void loadTitleLinks()
    {
    	JFileChooser fileChooser = ViewUtils.getFileChooser(fileSettings.getSourceFilePath());

    	fileChooser.resetChoosableFileFilters();

		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV/TXT", "CSV", "TXT"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XLS/XLSX", "XLS", "XLSX"));

        if (fileChooser.showOpenDialog(ViewUtils.getDialogOwner()) == JFileChooser.APPROVE_OPTION)
        {
        	File currentDirectory = fileChooser.getCurrentDirectory();
        	fileSettings.setSourceFilePath(currentDirectory);
        	updateSpecificSettings(fileSettings);

        	fileObject = getFileObject(fileChooser.getSelectedFile(), false);
            if (fileObject == null)
                return;

            dataObject = getDataObject();
            if (dataObject == null)
                return;

            linkLoader = new TitleLinkLoader(fileObject, dataObject, logTableModel);
            linkLoader.getPropertyChangeSupport().addPropertyChangeListener("state",
					evt -> doForWorkerEvent(evt) );
            linkLoader.execute();
        }
    }

    private void processTitleLinks()
    {
		File outputFile = fileSettings.getStoredProcedureLogNamePattern();
		outputFile = DataUtils.getFileWithCurrentTimeInName(outputFile);

		fileObject = getFileObject(outputFile, true);
		if (fileObject == null)
			return;

		dataObject = getDataObject();
		if (dataObject == null)
			return;

		linkProcessor = new TitleLinkProcessor(dataObject, fileObject, logTableModel);
		linkProcessor.getPropertyChangeSupport().addPropertyChangeListener("state",
				evt -> doForWorkerEvent(evt));
		linkProcessor.execute();
    }

    private void showSettings()
	{
		if (settingsDialog == null)
		{
			try
			{
				settingsDialog = new SettingsDialog(this, resourceManager);
				settingsDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			}
			catch (Exception e)
			{
				log(e);
			}
		}
		
		if (settingsDialog != null)
			settingsDialog.setVisible(true);
	}
    
    private TitleLinkFao getFileObject(File selectedFile, boolean fileWritable)
    {
        TitleLinkFao object = null;
    
        try
        {
            fileSettings.load();
            fileSettings.setFile(selectedFile);
            fileSettings.setFileWritable(fileWritable);
            object = AccessObjectFactory.getFileAccessObject(fileSettings);
        }
        catch (Exception e)
        {
            log(e);
        }
        
        return object;
    }
    
    private TitleLinkDao getDataObject()
    {
        TitleLinkDao object = null;
        
        try
        {
            dbSettings.load();
            object = AccessObjectFactory.getDataAccessObject(dbSettings);
        }
        catch (Exception e)
        {
            log(e);
        }
        
        return object;
    }

    private void doForWorkerEvent(PropertyChangeEvent event)
    {
        if ("state".equals(event.getPropertyName()))
        {
            Object newValue = event.getNewValue();

            if (newValue instanceof SwingWorker.StateValue)
            {
                SwingWorker.StateValue stateValue = (SwingWorker.StateValue) newValue;

                switch (stateValue)
                {
                    case STARTED:
                        doForWorkerStarted();
                        break;
                    case DONE:
                        doForWorkerDone();
                }
            }
        }
    }

    private void doForWorkerStarted()
    {
    	buttonsManager.blockButtons();
        processLabel.setIcon(resourceManager.getImageIcon(Constants.ICON_NAME_LOADING));
    }

    private void doForWorkerDone()
    {
        buttonsManager.activateButtons();
    	processLabel.setIcon(null);
    }

    public void setFocus()
    {
        if (logTable != null)
            logTable.requestFocus();
    }

    public void log(Exception e)
    {
		LogEventWriter.writeThrowable(e, logTableModel);
    }

    private class LoadTitleLinksListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            loadTitleLinks();
        }
    }

    private class ProcessTitleLinksListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            processTitleLinks();
        }
    }
    
    private class ShowSettingsListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			showSettings();
		}
	}
}
