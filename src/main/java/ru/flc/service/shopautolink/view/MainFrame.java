package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.LogEventTableModel;
import ru.flc.service.shopautolink.model.accessobject.AccessObjectFactory;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkFao;
import ru.flc.service.shopautolink.model.logic.TitleLinkLoader;
import ru.flc.service.shopautolink.model.logic.TitleLinkProcessor;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.ViewSettings;

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
    private static final Dimension COMMAND_PANEL_PREF_SIZE = new Dimension(120, 400);
    private static final Dimension BUTTON_MAX_SIZE = new Dimension(120, 60);

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;
    private ButtonsManager buttonsManager;
    
    private ViewSettings viewSettings;
    private DatabaseSettings dbSettings;
    private FileSettings fileSettings;
    
    private JFileChooser fileChooser;
    
    private TitleLinkFao fileObject;
    private TitleLinkDao dataObject;
    
    private TitleLinkLoader linkLoader;
    private TitleLinkProcessor linkProcessor;

    private JLabel processLabel;
    private LogEventTableModel logTableModel;
    private JTable logTable;

    public MainFrame()
    {
        loadSettings();
        initComponents();
        initFrame();
    }

    private void loadSettings()
    {
        viewSettings = new ViewSettings(WIN_PREF_SIZE);
        dbSettings = new DatabaseSettings();
        fileSettings = new FileSettings();
    }

    private void initComponents()
    {
        resourceManager = SAResourceManager.getInstance();
        titleAdjuster = new TitleAdjuster();
        buttonsManager = new ButtonsManager(COMMAND_PANEL_PREF_SIZE, BUTTON_MAX_SIZE,
                resourceManager, titleAdjuster);
        
        initFileChooser();

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());
	
		setIconImage(resourceManager.getImageIcon("linking32.png").getImage());
		titleAdjuster.registerComponent(this, new Title(resourceManager, "Main_Frame_Title"));
        
        titleAdjuster.resetComponents();

        pack();
    }

    private void initFileChooser()
    {
        fileChooser = new JFileChooser(".");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("XLS(X)", "XLS", "XLSX"));
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
            if (logTableModel != null)
                logTableModel.addRow(new LogEvent(e));
        }

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                //cancelSearch();
                //setWindowAttributes();
                //saveProperties();
            }
        });
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
		panel.add(buttonsManager.getSettingsButton(null));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        processLabel = new JLabel();
        processLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(processLabel);

        return panel;
    }

    private JPanel initLogPanel()
    {
        logTableModel = new LogEventTableModel(resourceManager, null);
        logTable = new JTable(logTableModel);

        JScrollPane tablePane = new JScrollPane(logTable);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "", TitledBorder.TOP, TitledBorder.CENTER));
        titleAdjuster.registerComponent(panel, new Title(resourceManager, "Log_Panel_Title"));
        panel.add(tablePane, BorderLayout.CENTER);

        return panel;
    }

    private void loadTitleLinks()
    {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            fileObject = getFileObject(fileChooser.getSelectedFile());
            if (fileObject == null)
                return;

            dataObject = getDataObject();
            if (dataObject == null)
                return;

            linkLoader = new TitleLinkLoader(fileObject, dataObject, logTableModel);
            linkLoader.getPropertyChangeSupport().addPropertyChangeListener("state", evt ->
            {
                doForWorkerEvent(evt);
            });

            linkLoader.execute();
        }
    }

    private void processTitleLinks()
    {
        dataObject = getDataObject();
        if (dataObject == null)
            return;

        linkProcessor = new TitleLinkProcessor(dataObject, logTableModel);
        linkProcessor.getPropertyChangeSupport().addPropertyChangeListener("state", evt -> {
            doForWorkerEvent(evt);
        });

        linkProcessor.execute();
    }
    
    private TitleLinkFao getFileObject(File selectedFile)
    {
        TitleLinkFao object = null;
    
        try
        {
            fileSettings.load();
            fileSettings.setFile(selectedFile);
            object = AccessObjectFactory.getFileAccessObject(fileSettings);
        }
        catch (Exception e)
        {
            if (logTableModel != null)
                logTableModel.addRow(new LogEvent(e));
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
            if (logTableModel != null)
                logTableModel.addRow(new LogEvent(e));
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
        processLabel.setIcon(resourceManager.getImageIcon("loading_mod_green.gif"));
    }

    private void doForWorkerDone()
    {
        buttonsManager.activateButtons();
    	processLabel.setIcon(null);
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
}
