package ru.flc.service.shopautolink.model;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.view.Constants;

import java.time.LocalDateTime;

public class LogEvent
{
	public static final int FIELD_QUANTITY = 2;

	private static ResourceManager resourceManager;

	public static void setResourceManager(ResourceManager manager)
	{
		resourceManager = manager;
	}
	
    private Title title;
    private LocalDateTime dateTime;

	public LogEvent(String textPattern, Object... textParameters)
	{
		this(LogEvent.resourceManager, textPattern, textParameters);
	}

	public LogEvent(Throwable throwable)
	{
		this(LogEvent.resourceManager, throwable.toString());
	}

    public LogEvent(ResourceManager resourceManager, String textPattern, Object... textParameters)
    {
    	if (resourceManager == null)
    		throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);

        if (textPattern == null)
            throw new IllegalArgumentException(Constants.EXCPT_TEXT_PATTERN_EMPTY);

        if (textParameters != null && textParameters.length > 0)
        	this.title = new Title(resourceManager, textPattern, textParameters);
        else
        	this.title = new Title(resourceManager, textPattern);

        this.dateTime = LocalDateTime.now();
    }
    
    public LogEvent(ResourceManager resourceManager, Exception exception)
    {
        this(resourceManager, exception.getMessage());
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public String getText()
    {
    	return title.getText();
    }
}
