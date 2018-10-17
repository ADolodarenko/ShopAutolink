package ru.flc.service.shopautolink.model.fao;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.LinkedList;
import java.util.List;

public class TitleLinkFao
{
	private FileSource fileSource;
	private int linkPackSize;
	
	private List<TitleLink> buffer;
	
	public TitleLinkFao(FileSource fileSource, int linkPackSize)
	{
		if (fileSource == null)
			throw new IllegalArgumentException("Empty file source.");
		
		this.fileSource = fileSource;
		this.linkPackSize = linkPackSize;
		
		buffer = new LinkedList<>();
	}
	
	public boolean hasMoreLinks()
	{
		return false;
	}
	
	public List<TitleLink> getNextLinkPack()
	{
		return null;
	}
}
