package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;

import java.util.LinkedList;
import java.util.List;

public class TitleLinkFao extends AccessObject
{
	private static final String FILE_SOURCE_EXCEPTION_STRING = "Empty file source.";
	private static final String PACK_SIZE_EXCEPTION_STRING = "Wrong pack size.";

	public static TitleLinkFao getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private static List<TitleLink> getShallowBufferClone(List<TitleLink> buffer)
	{
		if (buffer != null)
		{
			List<TitleLink> clone = new LinkedList<>();

			for (TitleLink link : buffer)
				clone.add(link);

			return clone;
		}
		else
			return null;
	}

	private int linkPackSize;
	
	private List<TitleLink> buffer;
	
	private TitleLinkFao()
	{
		buffer = new LinkedList<>();
		linkPackSize = 100;
	}

	public void setParameters(FileSource fileSource, int linkPackSize)
	{
		if (linkPackSize < 1)
			throw new IllegalArgumentException(PACK_SIZE_EXCEPTION_STRING);

		buffer.clear();

		this.source = fileSource;
		this.linkPackSize = linkPackSize;
	}
	
	public boolean hasMoreLinks() throws Exception
	{
		checkSource(FILE_SOURCE_EXCEPTION_STRING);

		if (buffer.size() > 0)
			return true;

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
				buffer.add(link);
			else
				break;
		}

		List<TitleLink> pack = getShallowBufferClone(buffer);
		buffer.clear();

		return pack;
	}

	private static class SingletonHelper
	{
		private static final TitleLinkFao INSTANCE = new TitleLinkFao();
	}
}
