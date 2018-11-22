package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PasswordCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JPasswordField editor;

	public PasswordCellEditor()
	{
		editor = new JPasswordField();
		editor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e)
			{
				editor.selectAll();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
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
		return new Password(editor.getPassword());
	}
}
