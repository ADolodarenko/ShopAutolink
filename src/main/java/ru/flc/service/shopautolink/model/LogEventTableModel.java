package ru.flc.service.shopautolink.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LogEventTableModel extends AbstractTableModel
{
    private List<LogEvent> eventList;

    public LogEventTableModel()
    {
        eventList = new ArrayList<>();
    }

    public LogEventTableModel(List<LogEvent> eventList)
    {
        if (eventList == null)
            throw new IllegalArgumentException("List of events is empty.");

        this.eventList = eventList;
    }

    @Override
    public int getRowCount()
    {
        return eventList.size();
    }

    @Override
    public int getColumnCount()
    {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return null;
    }
}
