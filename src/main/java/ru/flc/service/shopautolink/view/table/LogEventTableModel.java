package ru.flc.service.shopautolink.view.table;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.LogEvent;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogEventTableModel extends AbstractTableModel
{
	private static final String RESOURCE_MANAGER_EXCEPTION_STRING = "Resource manager is empty.";
	
    private List<LogEvent> data;
    private ResourceManager resourceManager;
    
    public LogEventTableModel(ResourceManager resourceManager, List<LogEvent> data)
    {
		if (resourceManager == null)
			throw new IllegalArgumentException(RESOURCE_MANAGER_EXCEPTION_STRING);
		
		this.resourceManager = resourceManager;
  
		if (data != null)
			this.data = data;
		else
			this.data = new ArrayList<>();
        
        fireTableDataChanged();
    }

    @Override
    public int getRowCount()
    {
        return data.size();
    }

    @Override
    public int getColumnCount()
    {
        return LogEvent.FIELD_QUANTITY;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
    	Object value = null;
    	
    	if (rowIndex < getRowCount())
		{
			LogEvent row = data.get(rowIndex);
			
			if (row != null)
				switch (columnIndex)
				{
					case 0:
						value = row.getDateTime();
						break;
					case 1:
						value = row.getText();
						break;
				}
		}
    	
        return value;
    }
	
	@Override
	public String getColumnName(int column)
	{
		switch (column)
		{
			case 0:
				return new Title(resourceManager, LogEvent.DATE_TIME_STRING).getText();
			case 1:
				return new Title(resourceManager, LogEvent.TEXT_STRING).getText();
			default:
				return null;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex)
		{
			case 0:
				return LocalDateTime.class;
			default:
				return String.class;
		}
	}
	
	public void addRow(LogEvent row)
	{
		int index = data.size();
		data.add(row);
		fireTableRowsInserted(index, index);
	}
	
	public LogEvent getRow(int rowIndex)
	{
		return data.get(rowIndex);
	}
	
	public void removeRow(LogEvent row)
	{
		int index = data.indexOf(row);
		
		if (index >= 0)
		{
			data.remove(index);
			fireTableRowsDeleted(index, index);
		}
	}
	
	public void clear()
	{
		int index = data.size() - 1;
		data.clear();
		
		if (index >= 0)
			fireTableRowsDeleted(0, index);
	}
}
