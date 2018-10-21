package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.LogEventTableModel;
import ru.flc.service.shopautolink.model.logic.TitleLinkLoader;
import ru.flc.service.shopautolink.model.logic.TitleLinkProcessor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;

public class MainFrame extends JFrame
{
    private static final Dimension WIN_MIN_SIZE = new Dimension(400, 350);
    private static final Dimension COMMAND_PANEL_PREF_SIZE = new Dimension(120, 400);
    private static final Dimension BUTTON_MAX_SIZE = new Dimension(120, 60);

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;
    private ButtonsManager buttonsManager;

    private TitleLinkLoader linkLoader;
    private TitleLinkProcessor linkProcessor;

    private JLabel processLabel;
    private LogEventTableModel logTableModel;
    private JTable logTable;

    public MainFrame()
    {
        loadProperties();
        initComponents();
        initFrame();
    }

    private void loadProperties()
    {

    }

    private void initComponents()
    {
        resourceManager = SAResourceManager.getInstance();
        titleAdjuster = new TitleAdjuster();
        buttonsManager = new ButtonsManager(COMMAND_PANEL_PREF_SIZE, BUTTON_MAX_SIZE,
                resourceManager, titleAdjuster);

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());
	
		setIconImage(resourceManager.getImageIcon("linking32.png").getImage());
		titleAdjuster.registerComponent(this, new Title(resourceManager, "Main_Frame_Title"));
        
        titleAdjuster.resetComponents();

        pack();
    }

    private void initFrame()
    {
        setMinimumSize(WIN_MIN_SIZE);

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

        /*if (windowAttributes != null)
        {
            if (windowAttributes.isMaximized())
                setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            else
                setBounds(windowAttributes.getLeftTopCorner().x,
                        windowAttributes.getLeftTopCorner().y,
                        windowAttributes.getMeasurements().width,
                        windowAttributes.getMeasurements().height);
        }

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                cancelSearch();
                setWindowAttributes();
                saveProperties();

                if (mustRestart)
                    restart();
            }
        });*/
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
        linkLoader = new TitleLinkLoader(null, null, null);

        linkLoader.addPropertyChangeListener(evt -> {
            doForWorkerEvent(evt);
        });

        linkLoader.execute();
    }

    private void processTitleLinks()
    {
        linkProcessor = new TitleLinkProcessor();

        /*linkProcessor.addPropertyChangeListener(evt -> {
            doForWorkerEvent(evt);
        });*/

        linkProcessor.getPropertyChangeSupport().addPropertyChangeListener("state", evt -> {
            doForWorkerEvent(evt);
        });

        linkProcessor.execute();
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
