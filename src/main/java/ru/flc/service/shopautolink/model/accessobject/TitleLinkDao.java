package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.database.DataSource;
import ru.flc.service.shopautolink.view.Constants;

import java.util.List;

public class TitleLinkDao implements AccessObject
{
	private DataSource source;
	
	public TitleLinkDao(DataSource source)
	{
		if (source == null)
			throw new IllegalArgumentException(Constants.EXCPT_DATA_SOURCE_EMPTY);
		
		this.source = source;
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
	
	public void uploadLinkPack(List<TitleLink> pack) throws Exception
	{
		source.uploadTitleLinkPack(pack);
	}
	
	public void applyUploadedLinks() throws Exception
	{
		source.applyUploadedTitleLinks();
	}
	
	public List<List<Element>> processLinks() throws Exception
	{
		return source.processTitleLinks();
	}
}
