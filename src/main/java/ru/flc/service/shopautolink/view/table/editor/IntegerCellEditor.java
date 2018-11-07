package ru.flc.service.shopautolink.view.table.editor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class IntegerCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final String INTEGER_CLASS_NAME = "Integer";

	private JSpinner editor;

	public IntegerCellEditor()
	{
		SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		editor = new JSpinner(model);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setValue(0);

		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();

			if (INTEGER_CLASS_NAME.equals(valueClassName))
				editor.setValue(((Integer) value).intValue());
		}

		return editor;
	}

	@Override
	public Object getCellEditorValue()
	{
		return editor.getValue();
	}
}
