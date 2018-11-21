package ru.flc.service.shopautolink.model;

import org.apache.commons.io.FilenameUtils;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils
{
	public static File getSelectedFileWithExtension(JFileChooser chooser)
	{
		File file = chooser.getSelectedFile();
		String fileName = file.getAbsolutePath();
		String oldExtension = FilenameUtils.getExtension(fileName);

		FileFilter fileFilter = chooser.getFileFilter();
		if (Constants.CLASS_NAME_FILENAMEEXTENSIONFILTER.equals(fileFilter.getClass().getSimpleName()))
		{
			String newExtension = ((FileNameExtensionFilter) fileFilter).getExtensions()[0];

			boolean mustAddExtension = (newExtension != null && !newExtension.isEmpty());
			mustAddExtension = mustAddExtension && (oldExtension == null || oldExtension.isEmpty()
					|| !oldExtension.equalsIgnoreCase(newExtension));

			if (mustAddExtension)
				file = new File(fileName + "." + newExtension.toLowerCase());
		}

		return file;
	}

	public static File getFileWithCurrentTimeInName(File file)
	{
		if (file == null)
			return null;

		String fileName = file.getAbsolutePath();

		String filePath = FilenameUtils.getFullPath(fileName);
		String fileBaseName = FilenameUtils.getBaseName(fileName);
		String fileExtension = FilenameUtils.getExtension(fileName);

		Path path = Paths.get(filePath, fileBaseName);

		LocalDateTime time = LocalDateTime.now();
		String timeString = time.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

		StringBuilder builder = new StringBuilder(path.toString());
		builder.append('_');
		builder.append(timeString);

		if (fileExtension != null && !fileExtension.isEmpty())
		{
			builder.append('.');
			builder.append(fileExtension);
		}

		return new File(builder.toString());
	}
}
