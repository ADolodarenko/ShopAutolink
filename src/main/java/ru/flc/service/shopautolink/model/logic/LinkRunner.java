package ru.flc.service.shopautolink.model.logic;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class LinkRunner extends SwingWorker<Void, Void>
{
	private static void handleException(ResourceManager resourceManager)
	{
		String title = new Title(resourceManager, Constants.KEY_LINK_TROUBLE_TITLE).getText();
		String message = new Title(resourceManager, Constants.KEY_LINK_TROUBLE_TEXT).getText();
		
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	private final URI uri;
	private ResourceManager resourceManager;
	
	public LinkRunner(ResourceManager resourceManager, URI uri)
	{
		if (resourceManager == null)
			throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);
		
		if (uri == null)
			throw new IllegalArgumentException(Constants.EXCPT_URI_EMPTY);
		
		this.resourceManager = resourceManager;
		this.uri = uri;
	}
	
	@Override
	protected Void doInBackground() throws Exception
	{
		Desktop desktop = Desktop.getDesktop();
		desktop.browse(uri);
		return null;
	}
	
	@Override
	protected void done()
	{
		try
		{
			get();
		}
		catch (InterruptedException e)
		{
			handleException(resourceManager);
		}
		catch (ExecutionException e)
		{
			handleException(resourceManager);
		}
	}
}
