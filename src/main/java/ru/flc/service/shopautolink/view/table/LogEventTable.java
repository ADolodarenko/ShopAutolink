package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.renderer.DateTimeCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class LogEventTable extends JTable
{
	public LogEventTable(TableModel model)
	{
		super(model);

		setHeaderAppearance();
		setColumnAppearance();
		setSelectionStrategy();
		
		setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

		setRowHeight((int) (getRowHeight() * 1.3));
	}
	
	private void setHeaderAppearance()
	{
		JTableHeader header = getTableHeader();
		header.setReorderingAllowed(false);
	}
	
	public void setColumnAppearance()
	{
		Enumeration<TableColumn> columns = getColumnModel().getColumns();
		
		while (columns.hasMoreElements())
		{
			TableColumn column = columns.nextElement();
			
			if (column.getModelIndex() == 0)
			{
				column.setCellRenderer(new DateTimeCellRenderer());
				column.setMinWidth(100);
				column.setMaxWidth(130);
				
				break;
			}
		}
	}

	private void setSelectionStrategy()
	{
		setCellSelectionEnabled(false);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public LogEvent getLogEvent(int row, int column)
	{
		int modelColumnIndex = convertColumnIndexToModel(column);

		if (modelColumnIndex > 0)
		{
			int modelRowIndex = convertRowIndexToModel(row);
			TableModel model = getModel();
			String modelClassName = model.getClass().getSimpleName();

			if (Constants.CLASS_NAME_LOGEVENTTABLEMODEL.equals(modelClassName))
			{
				LogEventTableModel thisModel = (LogEventTableModel) model;

				if (thisModel.getRowCount() > modelRowIndex)
					return thisModel.getRow(modelRowIndex);
			}
		}

		return null;
	}
}

