package ru.flc.service.shopautolink.view.table;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.editor.*;
import ru.flc.service.shopautolink.view.table.renderer.LocaleValueCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class SettingsTable extends JTable
{
	private ResourceManager resourceManager;
	
	private TableCellRenderer baseHeaderRenderer;

	public SettingsTable(TableModel model, ResourceManager resourceManager)
	{
		super(model);
		
		if (resourceManager == null)
			throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);

		this.resourceManager = resourceManager;

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
			case "Locale":
				return new LocaleValueCellRenderer(resourceManager);
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
			case "Locale":
				return new LocaleCellEditor(resourceManager);
		}

		return editor;
	}
}
