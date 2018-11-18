package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.renderer.TableCellRendererFactory;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class LogEventTable extends JTable
{
	private TableCellRendererFactory rendererFactory;
	
	public LogEventTable(TableModel model, TableCellRendererFactory rendererFactory)
	{
		super(model);
		
		if (rendererFactory == null)
			throw new IllegalArgumentException(Constants.EXCPT_TABLE_RENDERER_FACTORY_EMPTY);
		
		this.rendererFactory = rendererFactory;
		
		setSelectionStrategy();
	}
	
	private void setSelectionStrategy()
	{
		setCellSelectionEnabled(false);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column)
	{
		LogEvent rowData = getEvent(row, column);
		
		if (rowData != null)
		{
			TableCellRenderer renderer = rendererFactory.getRenderer(rowData.getText().getClass());
			
			if (renderer != null)
				return renderer;
		}
		
		return super.getCellRenderer(row, column);
	}
	
	private LogEvent getEvent(int row, int column)
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
