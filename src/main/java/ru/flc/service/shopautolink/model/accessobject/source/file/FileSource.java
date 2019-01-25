package ru.flc.service.shopautolink.model.accessobject.source.file;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.Source;

import java.util.List;

/**
 * This interface represents a file source of data.
 * Such a source can be used either for reading title links from a file or for writing data that comes out as a result
 * of the processing of title links to a file.
 */
public interface FileSource extends Source
{
	/**
	 * This method skips at least one row. It allows subsequent iteration through remaining rows,
	 * including the row with the index "rowNumber - 1".
	 * This method has to be called right after calling "open" method.
	 *
	 * @param rowNumber The number of the row to come to, starting from 1
	 * @throws Exception if there is now way to come to the desired row
	 */
	void moveToRow(int rowNumber) throws Exception;

	void moveToColumn(int columnNumber) throws Exception;
	TitleLink getNextLink() throws Exception;
	void putResultLine(List<Element> line) throws Exception;
	String getAbsolutePath();
}
