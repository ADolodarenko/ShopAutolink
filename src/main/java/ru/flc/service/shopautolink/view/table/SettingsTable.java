package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.settings.parameter.ParameterAlt;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class SettingsTable extends JTable
{
	public SettingsTable(TableModel model)
	{
		super(model);
		
		setRowSelectionAllowed(false);
		getColumnModel().setColumnSelectionAllowed(false);
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column)
	{
		int modelColumnIndex = convertColumnIndexToModel(column);
		
		if (modelColumnIndex > 0)
		{
			int modelRowIndex = convertRowIndexToModel(row);
			TableModel model = getModel();
			String modelClassName = model.getClass().getSimpleName();
			
			if ("SettingsTableModel".equals(modelClassName))
			{
				SettingsTableModel thisModel = (SettingsTableModel) model;
				
				if (thisModel.getRowCount() > modelRowIndex)
				{
					ParameterAlt rowData = thisModel.getRow(modelRowIndex);
					TableCellRenderer renderer = getCellRendererByDataClass(rowData.getType());
					
					if (renderer != null)
						return renderer;
				}
			}
		}
		
		return super.getCellRenderer(row, column);
	}
	
	//Only for demonstration. Drop this shit.
	@Override
	public boolean isCellSelected(int row, int column)
	{
		int modelColumnIndex = convertColumnIndexToModel(column);
		
		return modelColumnIndex > 0;
	}
	
	private TableCellRenderer getCellRendererByDataClass(Class<?> dataClass)
	{
		TableCellRenderer renderer = null;
		
		String dataClassName = dataClass.getSimpleName();
		
		switch (dataClassName)
		{
			case "Boolean":
				break;
			case "Integer":
				break;
			case "Double":
				break;
		}
		
		return renderer;
	}
}
