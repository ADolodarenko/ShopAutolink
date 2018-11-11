package ru.flc.service.shopautolink.view.table.editor;

import org.dav.service.util.ResourceManager;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Locale;

public class LocaleCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final String LOCALE_CLASS_NAME = "Locale";
	
	private ResourceManager resourceManager;
	private JComboBox<Locale> editor;
	
	public LocaleCellEditor(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;
		
		editor = new JComboBox<>();
		
		for (Locale locale : resourceManager.getAvailableLocales())
			editor.addItem(locale);
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setSelectedIndex(0);
		
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (LOCALE_CLASS_NAME.equals(valueClassName))
				editor.setSelectedItem(value);
		}
		
		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return editor.getSelectedItem();
	}
}
