package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.editor.*;
import ru.flc.service.shopautolink.view.table.renderer.TableCellRendererFactory;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class SettingsTable extends JTable
{
	private TableCellEditorFactory editorFactory;
	private TableCellRendererFactory rendererFactory;
	
	private TableCellRenderer baseHeaderRenderer;

	public SettingsTable(TableModel model,
						 TableCellEditorFactory editorFactory,
						 TableCellRendererFactory rendererFactory)
	{
		super(model);
		
		if (editorFactory == null)
			throw new IllegalArgumentException(Constants.EXCPT_TABLE_EDITOR_FACTORY_EMPTY);

		if (rendererFactory == null)
			throw new IllegalArgumentException(Constants.EXCPT_TABLE_RENDERER_FACTORY_EMPTY);

		this.editorFactory = editorFactory;
		this.rendererFactory = rendererFactory;

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
			TableCellRenderer renderer = rendererFactory.getRenderer(rowData.getType());

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
			TableCellEditor editor = editorFactory.getEditor(rowData.getType());

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

			if (Constants.CLASS_NAME_SETTINGSTABLEMODEL.equals(modelClassName))
			{
				SettingsTableModel thisModel = (SettingsTableModel) model;

				if (thisModel.getRowCount() > modelRowIndex)
					return thisModel.getRow(modelRowIndex);
			}
		}

		return null;
	}
}
