package ru.flc.service.shopautolink.view.table.renderer;

import com.sybase.jdbc3.jdbc.Const;
import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.listener.LinkMouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StringLinkCellRenderer extends DefaultTableCellRenderer
{
	private static final Cursor linkCursor = new Cursor(Cursor.HAND_CURSOR);
	
	private ResourceManager resourceManager;
	
	public StringLinkCellRenderer(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (value != null && Constants.CLASS_NAME_STRING.equals(value.getClass().getSimpleName()))
		{
			String stringValue = (String) value;
			
			int pos = stringValue.indexOf(Constants.MESS_AHREF_BEGINNING);
			
			if (pos > -1)
			{
				label.setCursor(linkCursor);
				label.addMouseListener(new LinkMouseListener(resourceManager));
			}
		}
		
		return label;
	}
}
