package ru.flc.service.shopautolink.view.table.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ParameterNameCellRenderer extends DefaultTableCellRenderer
{
	private Color background;
	private Font font;

	public ParameterNameCellRenderer(Color background, Font font)
	{
		this.background = background;
		this.font = font;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		label.setBackground(background);
		label.setFont(font);

		return label;
	}
}
