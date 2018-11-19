package ru.flc.service.shopautolink.view.table;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.editor.TableCellEditorFactory;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class LogEventTable extends JTable
{
	private TableCellEditorFactory editorFactory;
	private TableCellRenderer baseHeaderRenderer;

	public LogEventTable(TableModel model, TableCellEditorFactory editorFactory)
	{
		super(model);

		if (editorFactory == null)
			throw new IllegalArgumentException(Constants.EXCPT_TABLE_EDITOR_FACTORY_EMPTY);

		this.editorFactory = editorFactory;

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
	public TableCellEditor getCellEditor(int row, int column)
	{
		LogEvent rowData = getLogEvent(row, column);

		if (rowData != null)
		{
			TableCellEditor editor = editorFactory.getEditor(rowData.getText().getClass());

			if (editor != null)
				return editor;
		}

		return super.getCellEditor(row, column);
	}

	private LogEvent getLogEvent(int row, int column)
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

