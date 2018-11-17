package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class BooleanCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JCheckBox editor;

	public BooleanCellEditor()
	{
		editor = new JCheckBox();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setSelected(false);

		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();

			if (Constants.CLASS_NAME_BOOLEAN.equals(valueClassName))
				editor.setSelected(((Boolean) value).booleanValue());
		}

		return editor;
	}

	@Override
	public Object getCellEditorValue()
	{
		return editor.isSelected();
	}
}
