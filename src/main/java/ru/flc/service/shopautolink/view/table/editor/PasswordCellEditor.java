package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.ViewUtils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PasswordCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JPasswordField editor;

	private boolean confirmationRequired;
	private Object oldValue;

	public PasswordCellEditor(boolean confirmationRequired)
	{
		editor = new JPasswordField();
		editor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e)
			{
				editor.selectAll();
			}
		});

		this.confirmationRequired = confirmationRequired;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (confirmationRequired)
			oldValue = value;

		editor.setText("");

		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();

			if (Constants.CLASS_NAME_PASSWORD.equals(valueClassName))
				editor.setText( new String( ((Password) value).getKey() ) );
		}

		return editor;
	}

	@Override
	public Object getCellEditorValue()
	{
		Object newValue = new Password(editor.getPassword());

		if (confirmationRequired && !newValue.equals(oldValue))
			newValue = ViewUtils.confirmedValue(oldValue, newValue);

		return newValue;
	}
}
