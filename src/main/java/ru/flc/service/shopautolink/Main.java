package ru.flc.service.shopautolink;

import org.dav.service.filesystem.FileSystem;
import org.dav.service.log.LogUtil;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main
{
	public static void main(String[] args)
	{
		new Main();
	}

	private static void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public Main()
	{
		LogUtil.setLogger(FileSystem.getCurrentDir(Main.class), Constants.MESS_LOGGING_PROPERTIES_FILE_NAME);

		EventQueue.invokeLater(() ->
		{
			setLookAndFeel();

			JFrame mainFrame = new MainFrame();
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setVisible(true);

		});
	}
}

