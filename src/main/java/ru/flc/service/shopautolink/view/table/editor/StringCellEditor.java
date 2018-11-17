package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StringCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JTextField editor;
	
	public StringCellEditor()
	{
		editor = new JTextField();

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
			
			if (Constants.CLASS_NAME_STRING.equals(valueClassName))
				editor.setText(value.toString());
		}
		
		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return editor.getText();
	}
}
