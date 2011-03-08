package org.stringtree.sheet;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.stringtree.util.iterator.LineIterator;
import org.stringtree.util.iterator.QCSVSpliterator;
import org.stringtree.util.iterator.Spliterator;
import org.stringtree.util.iterator.StringIterator;

public class QCSVSheet {
	private SheetModel model;
	
	public QCSVSheet(SheetModel model) {
		this.model = model;
	}

	public void load(String string) {
		load(new StringReader(string));
	}

	public void load(Reader reader) {
		StringIterator lines = new LineIterator(reader);
		List<String> columns = loadColumns(lines);
		for(int i = 0; lines.hasNext(); ++i) {
			String line = lines.nextString();
			Spliterator cols = new QCSVSpliterator(line);
			String label = cols.nextString();
			for (int c=0; cols.hasNext(); ++c) {
				model.put(label, columns.get(c), cols.nextString());
			}
		}
	}

	private List<String> loadColumns(StringIterator lines) {
		List<String> ret = new ArrayList<String>();

		if (lines.hasNext()) {
			String line = lines.nextString();
			Spliterator cols = new QCSVSpliterator(line);
			cols.nextString(); // skip corner
			for (int i = 0; cols.hasNext(); ++i) {
				String label = cols.nextString();
				ret.add(label);
			}
		}
		return ret;
	}

	public String get(String row, String col) {
		return model.get(row, col);
	}
}
