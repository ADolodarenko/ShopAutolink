package ru.flc.service.shopautolink.view;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.SAResourceManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ViewUtils
{
	private static ResourceManager resourceManager = SAResourceManager.getInstance();
	private static List<UITextParameter> uiTextParameters;
	private static Component dialogOwner;
	private static JFileChooser fileChooser;

	static
	{
		uiTextParameters = new LinkedList<>();
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_OPTION_PANE_BUTTON_YES,
				new Title(resourceManager, Constants.KEY_BUTTON_YES)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_OPTION_PANE_BUTTON_NO,
				new Title(resourceManager, Constants.KEY_BUTTON_NO)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TITLE_OPEN,
				new Title(resourceManager, Constants.KEY_CAPTION_OPEN)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TITLE_SAVE,
				new Title(resourceManager, Constants.KEY_CAPTION_SAVE)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_LOOK_IN,
				new Title(resourceManager, Constants.KEY_LABEL_LOOK_IN)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_SAVE_IN,
				new Title(resourceManager, Constants.KEY_LABEL_SAVE_IN)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_UP_FOLDER,
				new Title(resourceManager, Constants.KEY_TOOLTIP_UP_FOLDER)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_NEW_FOLDER,
				new Title(resourceManager, Constants.KEY_TOOLTIP_NEW_FOLDER)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_VIEW_MENU,
				new Title(resourceManager, Constants.KEY_TOOLTIP_VIEW_MENU)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_LIST_VIEW,
				new Title(resourceManager, Constants.KEY_TOOLTIP_LIST_VIEW)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_DETAILS_VIEW,
				new Title(resourceManager, Constants.KEY_TOOLTIP_DETAILS_VIEW)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_FILE_NAME,
				new Title(resourceManager, Constants.KEY_LABEL_FILE_NAME)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_LABEL_FILE_TYPES,
				new Title(resourceManager, Constants.KEY_LABEL_FILE_TYPES)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_BUTTON_OPEN,
				new Title(resourceManager, Constants.KEY_CAPTION_OPEN)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_BUTTON_DIRECTORY_OPEN,
				new Title(resourceManager, Constants.KEY_CAPTION_OPEN)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_BUTTON_SAVE,
				new Title(resourceManager, Constants.KEY_CAPTION_SAVE)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_BUTTON_CANCEL,
				new Title(resourceManager, Constants.KEY_BUTTON_CANCEL)));

		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_OPEN,
				new Title(resourceManager, Constants.KEY_TOOLTIP_OPEN_FILE)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_DIRECTORY_OPEN,
				new Title(resourceManager, Constants.KEY_TOOLTIP_OPEN_DIRECTORY)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_SAVE,
				new Title(resourceManager, Constants.KEY_TOOLTIP_SAVE_FILE)));
		uiTextParameters.add(new UITextParameter(Constants.KEY_UI_FILE_CHOOSER_TOOLTIP_CANCEL,
				new Title(resourceManager, Constants.KEY_TOOLTIP_CANCEL)));
	}

	public static void setDialogOwner(Component owner)
	{
		dialogOwner = owner;
	}

	public static Component getDialogOwner()
	{
		return dialogOwner;
	}

	private static void initFileChooser(File initialDirectory)
	{
		File currentDirectory = initialDirectory;

		if (currentDirectory == null)
			if (fileChooser != null)
				currentDirectory = fileChooser.getCurrentDirectory();
			else
				currentDirectory = new File(".");

		fileChooser = new JFileChooser(currentDirectory);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
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

		initFileChooser(null);
	}

	public static JFileChooser getFileChooser(File initialDirectory)
	{
		if (fileChooser == null)
			initFileChooser(initialDirectory);

		return fileChooser;
	}
}
