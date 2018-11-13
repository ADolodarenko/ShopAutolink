package ru.flc.service.shopautolink.view.table.editor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class DoubleCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final String DOUBLE_CLASS_NAME = "Double";
	
	private JSpinner editor;
	
	public DoubleCellEditor()
	{
		SpinnerNumberModel model = new SpinnerNumberModel(0.0, Double.MIN_VALUE, Double.MAX_VALUE, 0.01);

		editor = new JSpinner(model);
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setValue(0.0);
		
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (DOUBLE_CLASS_NAME.equals(valueClassName))
				editor.setValue(value);
		}
		
		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return editor.getValue();
	}
}
