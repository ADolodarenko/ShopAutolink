package ru.flc.service.shopautolink.view.table.renderer;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.view.Constants;

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
			case Constants.CLASS_NAME_BOOLEAN:
				break;
			case Constants.CLASS_NAME_INTEGER:
				break;
			case Constants.CLASS_NAME_DOUBLE:
				break;
			case Constants.CLASS_NAME_STRING:
				break;
			case Constants.CLASS_NAME_LOCALE:
				renderer = new LocaleValueCellRenderer(resourceManager);
				break;
			case Constants.CLASS_NAME_PASSWORD:
				renderer = new PasswordCellRenderer();
		}

		if (renderer != null)
			renderers.put(forClassName, renderer);

		return renderer;
	}
}
