package ru.flc.service.shopautolink.view.table.renderer;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class LocaleValueComboRenderer extends JLabel implements ListCellRenderer
{
	private ResourceManager resourceManager;

	public LocaleValueComboRenderer(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();

			if ("Locale".equals(valueClassName))
			{
				Locale locale = (Locale) value;

				setText(locale.getDisplayName(resourceManager.getCurrentLocale()));

				String country = locale.getCountry();

				if (country.equalsIgnoreCase("RU"))
					setIcon(resourceManager.getImageIcon(Constants.ICON_NAME_RUS));
				else if (country.equalsIgnoreCase("US"))
					setIcon(resourceManager.getImageIcon(Constants.ICON_NAME_USA));
			}
		}

		return this;
	}
}
