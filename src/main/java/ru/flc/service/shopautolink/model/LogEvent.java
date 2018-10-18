package ru.flc.service.shopautolink.model;

import java.time.LocalDateTime;

public class LogEvent
{
    private String textPattern;
    private Object[] textParameters;
    private LocalDateTime dateTime;

    public LogEvent(String textPattern, Object... textParameters)
    {
        if (textPattern == null)
            throw new IllegalArgumentException("Text pattern is empty.");

        this.textPattern = textPattern;
        this.textParameters = textParameters;

        this.dateTime = LocalDateTime.now();
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
