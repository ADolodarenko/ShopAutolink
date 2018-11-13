package ru.flc.service.shopautolink.view.table.renderer;

import org.dav.service.util.ResourceManager;

import javax.swing.table.TableCellRenderer;
import java.util.HashMap;
import java.util.Map;

public class TableCellRendererFactory
{
	private ResourceManager resourceManager;
	private Map<String, TableCellRenderer> renderers;

	public TableCellRendererFactory(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;

		this.renderers = new HashMap<>();
	}

	public TableCellRenderer getRenderer(Class<?> dataClass)
	{
		TableCellRenderer renderer;

		String dataClassName = dataClass.getSimpleName();

		if (renderers.containsKey(dataClassName))
			renderer = renderers.get(dataClassName);
		else
			renderer = createRenderer(dataClassName);

		return renderer;
	}

	private TableCellRenderer createRenderer(String forClassName)
	{
		TableCellRenderer renderer = null;

		switch (forClassName)
		{
			case "Boolean":
				break;
			case "Integer":
				break;
			case "Double":
				break;
			case "String":
				break;
			case "Locale":
				renderer = new LocaleValueCellRenderer(resourceManager);
		}

		if (renderer != null)
			renderers.put(forClassName, renderer);

		return renderer;
	}
}
