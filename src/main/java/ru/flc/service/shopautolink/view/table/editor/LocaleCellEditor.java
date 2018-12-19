package ru.flc.service.shopautolink.view.table.editor;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.ViewUtils;
import ru.flc.service.shopautolink.view.table.renderer.LocaleValueComboRenderer;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Locale;

public class LocaleCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private ResourceManager resourceManager;
	private JComboBox<Locale> editor;

	private boolean confirmationRequired;
	private Object oldValue;
	
	public LocaleCellEditor(ResourceManager resourceManager, boolean confirmationRequired)
	{
		this.resourceManager = resourceManager;

		DefaultComboBoxModel<Locale> model = new DefaultComboBoxModel<>();

		for (Locale locale : resourceManager.getAvailableLocales())
			model.addElement(locale);

		editor = new JComboBox<>(model);
		editor.setEditable(false);
		editor.setRenderer(new LocaleValueComboRenderer(this.resourceManager));

		this.confirmationRequired = confirmationRequired;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (confirmationRequired)
			oldValue = value;

		editor.setSelectedIndex(0);
		
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_LOCALE.equals(valueClassName))
				editor.setSelectedItem(value);
		}
		
		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		Object newValue = editor.getSelectedItem();

		if (confirmationRequired && !newValue.equals(oldValue))
			newValue = ViewUtils.confirmedValue(oldValue, newValue);

		return newValue;
	}
}
