package org.stringtree.wiki;

import java.util.regex.Pattern;

import org.stringtree.Fetcher;
import org.stringtree.juicer.string.RegexSplitStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.util.StringUtils;

public class TableRow implements Fetcher {
    
	private static final Pattern bar = Pattern.compile("(?<!\\\\)\\|");
	
	private void renderCell(StringBuffer buf, String cell, int blanks) {
		buf.append("<td");
		if (blanks > 0) {
			buf.append(" colspan='");
			buf.append(blanks+1);
			buf.append("'");
		}
		buf.append(">");
		buf.append(cell);
		buf.append("</td>");
	}
	
	public void renderRow(String row, StringBuffer buf) {
		row = row.trim();
		if (row.endsWith("|")) row = row.substring(0,row.length()-1);
		String[] cells = bar.split(row,-1);
		int blanks = 0;
		String lastCell = null;

		buf.append("\n<tr>");
		
		for (int i = 0; i < cells.length; ++i) {
			String cell = cells[i];
			if (StringUtils.isBlank(cell)) 	{
				++blanks;
			} else {
				if(lastCell != null) {
					renderCell(buf, lastCell, blanks);
					blanks = 0;
				}
				lastCell = cell;
			}
		}

		if(lastCell != null) {
			renderCell(buf, lastCell, blanks);
		}

		buf.append("</tr>");
	}
	
	public Object getObject(String content) {
		StringBuffer buf = new StringBuffer();

		StringFilter rows = new RegexSplitStringFilter("(^|\n)\\|");
		rows.connectSource(new StringStringSource(content));
		
		for (String row = rows.nextString(); row != null; row = rows.nextString()) {
			renderRow(row, buf);
		}

		String ret = buf.toString();
		return ret;
	}
}
