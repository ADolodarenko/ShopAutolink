package ru.flc.service.shopautolink.view.table.renderer;

import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCellRenderer extends DefaultTableCellRenderer
{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_LOCALDATETIME.equals(valueClassName))
			{
				LocalDateTime time = (LocalDateTime) value;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
				
				setText(time.format(formatter));
			}
		}
		
		return this;
	}
}
