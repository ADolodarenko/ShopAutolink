package ru.flc.service.shopautolink.model;

import ru.flc.service.shopautolink.view.Constants;

public class TitleLink
{
	private int titleId;
	private String productCode;
	private int forSale;
	
	public TitleLink(int titleId, String productCode, int forSale)
	{
		if (titleId < 0)
			throw new IllegalArgumentException(String.format(Constants.EXCPT_TITLE_ID_WRONG, titleId));
		
		if (productCode == null || productCode.isEmpty())
			throw new IllegalArgumentException(Constants.EXCPT_PRODUCT_CODE_EMPTY);
		
		this.titleId = titleId;
		this.productCode = productCode;
		this.forSale = forSale;
	}
	
	public int getTitleId()
	{
		return titleId;
	}
	
	public String getProductCode()
	{
		return productCode;
	}

	public int getForSale()
	{
		return forSale;
	}
}
