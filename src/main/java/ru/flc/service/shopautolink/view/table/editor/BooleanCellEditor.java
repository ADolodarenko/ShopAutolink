package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.ViewUtils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class BooleanCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JCheckBox editor;

	private boolean confirmationRequired;
	private Object oldValue;

	public BooleanCellEditor(boolean confirmationRequired)
	{
		editor = new JCheckBox();

		this.confirmationRequired = confirmationRequired;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (confirmationRequired)
			oldValue = value;

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
		Object newValue = editor.isSelected();

		if (confirmationRequired && !newValue.equals(oldValue))
			newValue = ViewUtils.confirmedValue(oldValue, newValue);

		return newValue;
	}
}
