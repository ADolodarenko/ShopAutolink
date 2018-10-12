package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import org.dav.service.view.TitleAdjuster;
import org.dav.service.view.UsableGBC;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.LogEventsTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AutolinkFrame extends JFrame
{
    private ResourceManager resourceManager;
    private TitleAdjuster titleAdjuster;

    private JButton loadPositionsButton;
    private JButton linkPositionsButton;
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

        add(initCommandPanel(), BorderLayout.WEST);
        add(initLogPanel());

        titleAdjuster.resetComponents();

        pack();
    }

    private JPanel initCommandPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(120, 400));

        loadPositionsButton = new JButton();
        titleAdjuster.registerComponent(loadPositionsButton, new Title(resourceManager, "Load_Button_Title"));
        //loadPositionsButton.setIcon(resourceManager.getImageIcon("load_file.png"));
        loadPositionsButton.addActionListener((event) ->
        {
            processLabel.setIcon(resourceManager.getImageIcon("loading_mod3.gif"));
        });
        panel.add(loadPositionsButton, new UsableGBC(0, 0).
                setInsets(new Insets(2, 2, 2, 2)).
                setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH));

        linkPositionsButton = new JButton();
        titleAdjuster.registerComponent(linkPositionsButton, new Title(resourceManager, "Link_Button_Title"));
        //linkPositionsButton.setIcon(resourceManager.getImageIcon("link_titles.png"));
        linkPositionsButton.addActionListener((event) ->
        {
            processLabel.setIcon(null);
        });
        panel.add(linkPositionsButton, new UsableGBC(0, 1).
                setInsets(new Insets(2, 2, 2, 2)).
                setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH));

        processLabel = new JLabel();
        panel.add(processLabel, new UsableGBC(0, 2).
                setInsets(new Insets(20, 0, 0, 0)).
                setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.NORTH));

        return panel;
    }

    private JPanel initLogPanel()
    {
        logTableModel = new LogEventsTableModel();
        logTable = new JTable(logTableModel);

        JScrollPane tablePane = new JScrollPane(logTable);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "", TitledBorder.TOP, TitledBorder.CENTER));
        titleAdjuster.registerComponent(panel, new Title(resourceManager, "Log_Panel_Title"));
        panel.add(tablePane, BorderLayout.CENTER);

        return panel;
    }
}
