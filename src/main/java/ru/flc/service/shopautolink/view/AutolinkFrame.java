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

public class AutolinkFrame extends JFrame
{
    private static final Dimension PREF_COMMAND_PANEL_SIZE = new Dimension(120, 400);
    private static final Dimension MAX_BUTTON_SIZE = new Dimension(120, 40);

    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;
    private ButtonsFactory buttonsFactory;

    private JLabel processLabel;

    private LogEventsTableModel logTableModel;
    private JTable logTable;



    public AutolinkFrame()
    {
        loadProperties();
        initComponents();
    }

    private void loadProperties()
    {

    }

    private void initComponents()
    {
        resourceManager = SAResourceManager.getInstance();
        titleAdjuster = new TitleAdjuster();
        buttonsFactory = new ButtonsFactory(PREF_COMMAND_PANEL_SIZE, MAX_BUTTON_SIZE,
                resourceManager, titleAdjuster);

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());

        titleAdjuster.resetComponents();

        pack();
    }

    public JPanel initCommandPanel()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(buttonsFactory.getLoadPositionsButton(new LoadPositionsListener()));
        panel.add(Box.createRigidArea(new Dimension(0, 2)));
        panel.add(buttonsFactory.getLinkPositionsButton(new LinkPositionsListener()));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        processLabel = new JLabel();
        processLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(processLabel);

        panel.add(Box.createGlue());

        panel.add(buttonsFactory.getSettingsButton(null));

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
            processLabel.setIcon(resourceManager.getImageIcon("loading_mod3.gif"));
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
