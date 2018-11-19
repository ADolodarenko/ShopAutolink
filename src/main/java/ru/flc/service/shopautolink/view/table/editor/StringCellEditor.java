package ru.flc.service.shopautolink.view.table.editor;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.listener.LinkMouseListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StringCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final Cursor linkCursor = new Cursor(Cursor.HAND_CURSOR);

	private JTextField plainEditor;
	private JTextField linkEditor;
	private JTextField editor;
	
	public StringCellEditor(ResourceManager resourceManager)
	{
		initPlainEditor();
		initLinkEditor(resourceManager);
	}

	private void initPlainEditor()
	{
		plainEditor = new JTextField();

		plainEditor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e)
			{
				plainEditor.selectAll();
			}
		});
	}

	private void initLinkEditor(ResourceManager resourceManager)
	{
		linkEditor = new JTextField();

		linkEditor.setEditable(false);
		linkEditor.addMouseListener(new LinkMouseListener(resourceManager));
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		plainEditor.setText("");
		editor = plainEditor;

		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_STRING.equals(valueClassName))
			{
				String stringValue = value.toString();
				int pos = stringValue.indexOf(Constants.MESS_AHREF_BEGINNING);

				if (pos > -1)
				{
					linkEditor.setText(stringValue);
					editor = linkEditor;
				}
				else
					plainEditor.setText(stringValue);
			}
		}

		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return editor.getText();
	}
}
