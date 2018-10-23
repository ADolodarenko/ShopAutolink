package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.FileSource;

import java.util.LinkedList;
import java.util.List;

public class TitleLinkFao extends AccessObject
{
	private static final String FILE_SOURCE_EXCEPTION_STRING = "Empty file source.";

	public static TitleLinkFao getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private int linkPackSize;
	
	private List<TitleLink> buffer;
	
	private TitleLinkFao()
	{}

	public void setFileSource(FileSource fileSource, int linkPackSize)
	{
		this.source = fileSource;
		this.linkPackSize = linkPackSize;

		buffer = new LinkedList<>();
	}
	
	public boolean hasMoreLinks() throws Exception
	{
		checkSource(FILE_SOURCE_EXCEPTION_STRING);

		TitleLink link = ((FileSource)source).getNextLink();

		if (link != null)
		{
			buffer.add(link);
			return true;
		}
		else
			return false;
	}
	
	public List<TitleLink> getNextLinkPack() throws Exception
	{
		checkSource(FILE_SOURCE_EXCEPTION_STRING);

		while (buffer.size() < linkPackSize)
		{
			TitleLink link = ((FileSource)source).getNextLink();

			if (link != null)
			{
				buffer.add(link);
			}
			else
				break;
		}

		return buffer; //TODO: clone and clean buffer here
	}

	private static class SingletonHelper
	{
		private static final TitleLinkFao INSTANCE = new TitleLinkFao();
	}
}
