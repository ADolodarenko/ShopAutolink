package ru.flc.service.shopautolink.view.table.editor;

import ru.flc.service.shopautolink.model.Utils;
import ru.flc.service.shopautolink.view.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class FileCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JTextField editor;

	public FileCellEditor(JFileChooser fileChooser)
	{
		editor = new JTextField();
		editor.setEditable(false);
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				fileChooser.resetChoosableFileFilters();

				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT", "TXT"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV", "CSV"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XLS", "XLS"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XLSX", "XLSX"));

				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					editor.setText(Utils.getSelectedFileWithExtension(fileChooser).getAbsolutePath());
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setText("");

		if (value != null)
		{
			String valueClassName = value.getClass().getSimpleName();

			if (Constants.CLASS_NAME_FILE.equals(valueClassName))
				editor.setText( ((File) value).getAbsolutePath() );
		}

		return editor;
	}

	@Override
	public Object getCellEditorValue()
	{
		return editor.getText();
	}
}
