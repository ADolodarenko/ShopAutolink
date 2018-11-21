package ru.flc.service.shopautolink.view.table.renderer;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DateFormat;
import java.text.Format;

public class FormattedCellRenderer extends DefaultTableCellRenderer
{
	public static FormattedCellRenderer getDateTimeRenderer()
	{
		return new FormattedCellRenderer(DateFormat.getDateTimeInstance());
	}
	
	public static FormattedCellRenderer getTimeRenderer()
	{
		return new FormattedCellRenderer(DateFormat.getTimeInstance());
	}
	
	private Format formatter;
	
	public FormattedCellRenderer(Format formatter)
	{
		this.formatter = formatter;
	}
	
	@Override
	protected void setValue(Object value)
	{
		try
		{
			if (value != null)
				value = formatter.format(value);
		}
		catch (IllegalArgumentException e)
		{}
		
		super.setValue(value);
	}
}
