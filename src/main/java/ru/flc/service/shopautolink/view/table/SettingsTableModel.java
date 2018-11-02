package ru.flc.service.shopautolink.view.table;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.ParameterAlt;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SettingsTableModel extends AbstractTableModel
{
	private static final String RESOURCE_MANAGER_EXCEPTION_STRING = "Resource manager is empty.";
	
    private List<ParameterAlt> data;
    private ResourceManager resourceManager;
	
	public SettingsTableModel(ResourceManager resourceManager, List<ParameterAlt> data)
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
        return ParameterAlt.FIELD_QUANTITY;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
		Object value = null;
	
		if (rowIndex < getRowCount())
		{
			ParameterAlt row = data.get(rowIndex);
		
			if (row != null)
				switch (columnIndex)
				{
					case 0:
						value = row.getDisplayName();
						break;
					case 1:
						value = row.getValue();
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
				return Title.getTitleString(resourceManager.getBundle(), ParameterAlt.PARAM_NAME_STRING);
			case 1:
				return Title.getTitleString(resourceManager.getBundle(), ParameterAlt.PARAM_VALUE_STRING);
			default:
				return null;
		}
	}
	
	public void addRow(ParameterAlt row)
	{
		int index = data.size();
		data.add(row);
		fireTableRowsInserted(index, index);
	}
	
	public ParameterAlt getRow(int rowIndex)
	{
		return data.get(rowIndex);
	}
	
	public void removeRow(ParameterAlt row)
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
