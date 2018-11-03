package ru.flc.service.shopautolink.view.table;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterAlt;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SettingsTableModel extends AbstractTableModel
{
	private ResourceManager resourceManager;
	private List<ParameterAlt> data;
	private Title[] titles;
	
	public SettingsTableModel(ResourceManager resourceManager, String[] titleKeys, List<ParameterAlt> data)
	{
		if (resourceManager == null)
			throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);
		
		this.resourceManager = resourceManager;
		
		if (data != null)
			this.data = data;
		else
			this.data = new ArrayList<>();
		
		initColumnTitle(titleKeys);
		
		fireTableDataChanged();
	}
	
	private void initColumnTitle(String[] titleKeys)
	{
		if (titleKeys != null)
		{
			titles = new Title[titleKeys.length];
			
			for (int i = 0; i < titleKeys.length; i++)
				titles[i] = new Title(resourceManager, titleKeys[i]);
		}
	}
    
    @Override
    public int getRowCount()
    {
        return data.size();
    }

    @Override
    public int getColumnCount()
    {
    	if (titles != null)
    		return titles.length;
    	else
    		return 0;
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
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex > 0;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if (isCellEditable(rowIndex, columnIndex))
		{
			if (rowIndex < getRowCount())
			{
				ParameterAlt row = data.get(rowIndex);
				
				try
				{
					row.setValue(aValue);
				}
				catch (IllegalArgumentException e)
				{}
			}
		}
	}
	
	@Override
	public String getColumnName(int column)
	{
		if (titles != null && titles.length > column)
			return titles[column].getText();
		else
			return "";
	}
	
	public void addRow(ParameterAlt row)
	{
		int index = data.size();
		data.add(row);
		fireTableRowsInserted(index, index);
	}
	
	public void addAllRows(List<ParameterAlt> rowList)
	{
		if (rowList != null && !rowList.isEmpty())
		{
			int firstIndex = data.size();
			int lastIndex = firstIndex + rowList.size() - 1;
			
			data.addAll(rowList);
			
			fireTableRowsInserted(firstIndex, lastIndex);
		}
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
