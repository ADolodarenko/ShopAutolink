package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;

import java.util.LinkedList;
import java.util.List;

public class TitleLinkFao implements AccessObject
{
	private static final String FILE_SOURCE_EXCEPTION_STRING = "Empty file source.";
	private static final String PACK_SIZE_EXCEPTION_STRING = "Wrong pack size.";

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

	private FileSource source;
	private int linkPackSize;
	private List<TitleLink> buffer;
	
	public TitleLinkFao(FileSource source, int linkPackSize)
	{
		if (source == null)
			throw new IllegalArgumentException(FILE_SOURCE_EXCEPTION_STRING);
		
		if (linkPackSize < 1)
			throw new IllegalArgumentException(PACK_SIZE_EXCEPTION_STRING);
		
		this.source = source;
		this.linkPackSize = linkPackSize;
		this.buffer = new LinkedList<>();
	}
	
	@Override
	public void open() throws Exception
	{
		source.open();
	}
	
	@Override
	public void close() throws Exception
	{
		source.close();
	}
	
	public boolean hasMoreLinks() throws Exception
	{
		if (buffer.size() > 0)
			return true;

		TitleLink link = source.getNextLink();

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
		while (buffer.size() < linkPackSize)
		{
			TitleLink link = source.getNextLink();

			if (link != null)
				buffer.add(link);
			else
				break;
		}

		List<TitleLink> pack = getShallowBufferClone(buffer);
		buffer.clear();

		return pack;
	}
}
