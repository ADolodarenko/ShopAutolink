package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.ViewUtils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class DoubleCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JSpinner editor;

	private boolean confirmationRequired;
	private Object oldValue;
	
	public DoubleCellEditor(boolean confirmationRequired)
	{
		SpinnerNumberModel model = new SpinnerNumberModel(0.0, Double.MIN_VALUE, Double.MAX_VALUE, 0.01);

		editor = new JSpinner(model);

		this.confirmationRequired = confirmationRequired;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (confirmationRequired)
			oldValue = value;

		editor.setValue(0.0);
		
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_DOUBLE.equals(valueClassName))
				editor.setValue(value);
		}
		
		return editor;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		Object newValue = editor.getValue();

		if (confirmationRequired && !newValue.equals(oldValue))
			newValue = ViewUtils.confirmedValue(oldValue, newValue);

		return newValue;
	}
}
