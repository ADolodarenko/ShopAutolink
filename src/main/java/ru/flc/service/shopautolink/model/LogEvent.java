package ru.flc.service.shopautolink.model;

import ru.flc.service.shopautolink.view.Constants;

import java.time.LocalDateTime;

public class LogEvent
{
	public static final int FIELD_QUANTITY = 2;
	
    private String textPattern;
    private Object[] textParameters;
    private LocalDateTime dateTime;

    public LogEvent(String textPattern, Object... textParameters)
    {
        if (textPattern == null)
            throw new IllegalArgumentException(Constants.EXCPT_TEXT_PATTERN_EMPTY);

        this.textPattern = textPattern;
        this.textParameters = textParameters;

        this.dateTime = LocalDateTime.now();
    }
    
    public LogEvent(Exception exception)
    {
        this(exception.getMessage());
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public String getText()
    {
        if (textParameters != null && textParameters.length > 0)
            return String.format(textPattern, textParameters);
        else
            return textPattern;
    }
}
