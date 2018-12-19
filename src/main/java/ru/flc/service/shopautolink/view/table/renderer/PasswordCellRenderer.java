package ru.flc.service.shopautolink.view.table.renderer;

import ru.flc.service.shopautolink.model.DataUtils;
import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PasswordCellRenderer extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_PASSWORD.equals(valueClassName))
			{
				Password password = (Password) value;
				
				label.setText(DataUtils.toAsterisks(password.getSecret()));
			}
		}
		
		return label;
	}
}
