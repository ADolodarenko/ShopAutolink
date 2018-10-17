package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.LogEventsTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame
{
    private static final Dimension WIN_MIN_SIZE = new Dimension(400, 350);
    private static final Dimension COMMAND_PANEL_PREF_SIZE = new Dimension(120, 400);
    private static final Dimension BUTTON_MAX_SIZE = new Dimension(120, 60);

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;
    private ButtonsFactory buttonsFactory;

    private JLabel processLabel;

    private LogEventsTableModel logTableModel;
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
        buttonsFactory = new ButtonsFactory(COMMAND_PANEL_PREF_SIZE, BUTTON_MAX_SIZE,
                resourceManager, titleAdjuster);

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());

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

        setIconImage(resourceManager.getImageIcon("linking32.png").getImage());

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

        panel.add(buttonsFactory.getLoadPositionsButton(new LoadPositionsListener()));
		panel.add(Box.createRigidArea(new Dimension(0, 1)));
        panel.add(buttonsFactory.getLinkPositionsButton(new LinkPositionsListener()));
		panel.add(Box.createRigidArea(new Dimension(0, 1)));
		panel.add(buttonsFactory.getSettingsButton(null));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        processLabel = new JLabel();
        processLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(processLabel);

        return panel;
    }

    private JPanel initLogPanel()
    {
        logTableModel = new LogEventsTableModel();
        logTable = new JTable(logTableModel);

        JScrollPane tablePane = new JScrollPane(logTable);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "", TitledBorder.TOP, TitledBorder.CENTER));
        titleAdjuster.registerComponent(panel, new Title(resourceManager, "Log_Panel_Title"));
        panel.add(tablePane, BorderLayout.CENTER);

        return panel;
    }

    private class LoadPositionsListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            processLabel.setIcon(resourceManager.getImageIcon("loading_mod_green.gif"));
        }
    }

    private class LinkPositionsListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            processLabel.setIcon(null);
        }
    }
}
