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
	private JFileChooser fileChooser;
	private Map<String, TableCellEditor> editors;

	public TableCellEditorFactory(ResourceManager resourceManager, JFileChooser fileChooser)
	{
		this.resourceManager = resourceManager;
		this.fileChooser = fileChooser;

		this.editors = new HashMap<>();
	}

	public TableCellEditor getEditor(Class<?> dataClass)
	{
		TableCellEditor editor;

		String dataClassName = dataClass.getSimpleName();

		if (editors.containsKey(dataClassName))
			editor = editors.get(dataClassName);
		else
			editor = createEditor(dataClassName);

		return editor;
	}

	private TableCellEditor createEditor(String forClassName)
	{
		TableCellEditor editor = null;

		switch (forClassName)
		{
			case Constants.CLASS_NAME_BOOLEAN:
				editor = new BooleanCellEditor();
				break;
			case Constants.CLASS_NAME_INTEGER:
				editor = new IntegerCellEditor();
				break;
			case Constants.CLASS_NAME_DOUBLE:
				editor = new DoubleCellEditor();
				break;
			case Constants.CLASS_NAME_STRING:
				editor = new StringCellEditor();
				break;
			case Constants.CLASS_NAME_LOCALE:
				editor = new LocaleCellEditor(resourceManager);
				break;
			case Constants.CLASS_NAME_FILE:
				editor = new FileCellEditor(fileChooser);
		}

		if (editor != null)
			editors.put(forClassName, editor);

		return editor;
	}
}
