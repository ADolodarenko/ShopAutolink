package ru.flc.service.shopautolink.view.table.listener;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.logic.LinkRunner;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.LogEventTable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

public class LinkMouseListener extends MouseAdapter
{
	private ResourceManager resourceManager;
	private Cursor customCursor;
	
	public LinkMouseListener(ResourceManager resourceManager,
							 Cursor customCursor)
	{
		this.resourceManager = resourceManager;
		this.customCursor = customCursor;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Object source = e.getSource();
		
		if (Constants.CLASS_NAME_LOGEVENTTABLE.equals(source.getClass().getSimpleName()))
		{
			LogEventTable table = (LogEventTable) source;
			Point point = e.getPoint();
			
			String linkValue = getLinkValueFromTable(table, point);
			
			if (linkValue != null && !linkValue.isEmpty())
				try
				{
					URI uri = new URI(linkValue);
					(new LinkRunner(resourceManager, uri)).execute();
				}
				catch (URISyntaxException e1)
				{}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (customCursor != null && !customCursor.equals(Cursor.getDefaultCursor()))
		{
			Object source = e.getSource();
			
			if (Constants.CLASS_NAME_LOGEVENTTABLE.equals(source.getClass().getSimpleName()))
			{
				LogEventTable table = (LogEventTable) source;
				Point point = e.getPoint();
				
				String linkValue = getLinkValueFromTable(table, point);
				
				if (linkValue != null && !linkValue.isEmpty())
					table.setCursor(customCursor);
				else
					table.setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	private String getLinkValueFromTable(LogEventTable table, Point point)
	{
		String linkValue = null;
		
		int row = table.rowAtPoint(point);
		int col = table.columnAtPoint(point);
			
		LogEvent logEvent = table.getLogEvent(row, col);
			
		if (logEvent != null)
			linkValue = getLinkValue(logEvent.getText());
		
		return linkValue;
	}

	private String getLinkValue(String text)
	{
		if (text == null)
			return null;
		
		String result = null;
		
		int startIndex = text.indexOf(Constants.MESS_AHREF_BEGINNING);
		
		if (startIndex > -1)
		{
			int nextIndex = startIndex + Constants.MESS_AHREF_BEGINNING.length();
			int endIndex = text.indexOf("\">", nextIndex);
			int linkLen = endIndex - nextIndex;
			
			if (linkLen > 0)
				result = Constants.MESS_FILE_REF_BEGINNING +
						text.substring(nextIndex, endIndex).replace('\\', '/');
		}
		
		return result;
	}
}
