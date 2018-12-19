package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.SAResourceManager;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class ViewUtils
{
	private static ResourceManager resourceManager = SAResourceManager.getInstance();
	private static List<UITextParameter> uiTextParameters;

	static
	{
		uiTextParameters = new LinkedList<>();
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_OPTION_PANE_BUTTON_YES,
				new Title(resourceManager, Constants.KEY_BUTTON_YES)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_OPTION_PANE_BUTTON_NO,
				new Title(resourceManager, Constants.KEY_BUTTON_NO)));
	}

	public static Object confirmedValue(Object oldValue, Object newValue)
	{
		String title = new Title(resourceManager, Constants.KEY_CONFIRMATION_TITLE).getText();
		String message = new Title(resourceManager, Constants.KEY_CONFIRMATION_MESSAGE).getText();

		if (JOptionPane.showConfirmDialog(null, message, title,
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			return newValue;
		else
			return oldValue;
	}

	public static void adjustDialogs()
	{
		for (UITextParameter parameter : uiTextParameters)
			UIManager.put(parameter.getKey(), parameter.getValue().getText());
	}
}
