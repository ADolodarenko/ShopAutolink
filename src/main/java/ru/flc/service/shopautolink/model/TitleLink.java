package ru.flc.service.shopautolink.model;

public class TitleLink
{
	private int titleId;
	private String productCode;
	
	public TitleLink(int titleId, String productCode)
	{
		if (titleId < 0)
			throw new IllegalArgumentException("Illegal titleId = " + titleId + ".");
		
		if (productCode == null || productCode.isEmpty())
			throw new IllegalArgumentException("Empty productCode.");
		
		this.titleId = titleId;
		this.productCode = productCode;
	}
	
	public int getTitleId()
	{
		return titleId;
	}
	
	public String getProductCode()
	{
		return productCode;
	}
}
