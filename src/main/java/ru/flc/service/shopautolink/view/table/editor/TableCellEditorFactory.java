package ru.flc.service.shopautolink.view.table.editor;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.HashMap;
import java.util.Map;

public class TableCellEditorFactory
{
	private ResourceManager resourceManager;
	private Map<String, TableCellEditor> editors;

	public TableCellEditorFactory(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;

		this.editors = new HashMap<>();
	}

	public TableCellEditor getEditor(Class<?> dataClass, boolean confirmationRequired)
	{
		TableCellEditor editor;

		String dataClassName = dataClass.getSimpleName();

		if (editors.containsKey(dataClassName))
			editor = editors.get(dataClassName);
		else
			editor = createEditor(dataClassName, confirmationRequired);

		return editor;
	}

	private TableCellEditor createEditor(String forClassName, boolean confirmationRequired)
	{
		TableCellEditor editor = null;

		switch (forClassName)
		{
			case Constants.CLASS_NAME_BOOLEAN:
				editor = new BooleanCellEditor(confirmationRequired);
				break;
			case Constants.CLASS_NAME_INTEGER:
				editor = new IntegerCellEditor(confirmationRequired);
				break;
			case Constants.CLASS_NAME_DOUBLE:
				editor = new DoubleCellEditor(confirmationRequired);
				break;
			case Constants.CLASS_NAME_STRING:
				editor = new StringCellEditor(confirmationRequired);
				break;
			case Constants.CLASS_NAME_LOCALE:
				editor = new LocaleCellEditor(resourceManager, confirmationRequired);
				break;
			case Constants.CLASS_NAME_FILE:
				editor = new FileCellEditor(confirmationRequired);
				break;
			case Constants.CLASS_NAME_PASSWORD:
				editor = new PasswordCellEditor(confirmationRequired);
		}

		if (editor != null)
			editors.put(forClassName, editor);

		return editor;
	}
}
