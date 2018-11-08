package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.view.table.editor.BooleanCellEditor;
import ru.flc.service.shopautolink.view.table.editor.DoubleCellEditor;
import ru.flc.service.shopautolink.view.table.editor.IntegerCellEditor;
import ru.flc.service.shopautolink.view.table.editor.StringCellEditor;
import ru.flc.service.shopautolink.view.table.renderer.ParameterNameCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class SettingsTable extends JTable
{
	private TableCellRenderer baseHeaderRenderer;

	public SettingsTable(TableModel model)
	{
		super(model);

		setHeaderAppearance();
		setColumnAppearance();
		setSelectionStrategy();

		setRowHeight((int) (getRowHeight() * 1.3));
	}

	private void setHeaderAppearance()
	{
		JTableHeader header = getTableHeader();
		baseHeaderRenderer = header.getDefaultRenderer();
		header.setDefaultRenderer(new TableHeaderRenderer());
		header.setReorderingAllowed(false);
	}

	private void setColumnAppearance()
	{
		Enumeration<TableColumn> columns = getColumnModel().getColumns();

		while (columns.hasMoreElements())
		{
			TableColumn column = columns.nextElement();

			if (column.getModelIndex() == 0)
			{
				column.setCellRenderer(baseHeaderRenderer);

				break;
			}
		}
	}

	private void setSelectionStrategy()
	{
		setCellSelectionEnabled(true);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column)
	{
		Parameter rowData = getParameter(row, column);

		if (rowData != null)
		{
			TableCellRenderer renderer = getCellRendererByDataClass(rowData.getType());

			if (renderer != null)
				return renderer;
		}

		return super.getCellRenderer(row, column);
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column)
	{
		Parameter rowData = getParameter(row, column);

		if (rowData != null)
		{
			TableCellEditor editor = getCellEditorByDataClass(rowData.getType());

			if (editor != null)
				return editor;
		}

		return super.getCellEditor(row, column);
	}

	private Parameter getParameter(int row, int column)
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
					return thisModel.getRow(modelRowIndex);
			}
		}

		return null;
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
			case "String":
				break;
		}
		
		return renderer;
	}

	private TableCellEditor getCellEditorByDataClass(Class<?> dataClass)
	{
		TableCellEditor editor = null;

		String dataClassName = dataClass.getSimpleName();

		switch (dataClassName)
		{
			case "Boolean":
				return new BooleanCellEditor();
			case "Integer":
				return new IntegerCellEditor();
			case "Double":
				return new DoubleCellEditor();
			case"String":
				return new StringCellEditor();
		}

		return editor;
	}
}
