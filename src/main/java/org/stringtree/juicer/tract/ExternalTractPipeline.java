package org.stringtree.juicer.tract;

import java.util.ArrayList;
import java.util.List;

import org.stringtree.Fetcher;
import org.stringtree.juicer.formatter.Transformation;
import org.stringtree.juicer.string.IgnoreCommentLineStringFilter;
import org.stringtree.juicer.string.SplitLinesStringFilter;
import org.stringtree.juicer.string.SplitStringFilter;
import org.stringtree.juicer.string.StaticRegexReplaceStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringPipeline;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.util.ClassUtils;

public class ExternalTractPipeline extends InitialisingTractPipeline {
    
    protected ClassUtils creator = new ClassUtils();
    
	public ExternalTractPipeline(StringSource spec, Fetcher context) {
		setList(getFilters(spec));
		init(context);
	}
	
	public ExternalTractPipeline(String spec, Fetcher context) {
		this(createStringSource(spec), context);
	}

	public ExternalTractPipeline(StringSource spec) {
		setList(getFilters(spec));
	}
	
	public ExternalTractPipeline(String spec) {
		this(createStringSource(spec));
	}

	private static StringSource createStringSource(String spec) {
		StringSource raw = new StringStringSource(spec);
		StringFilter cooked = new StringPipeline(new StringFilter[] {
			new SplitLinesStringFilter(),
			new IgnoreCommentLineStringFilter(),
			new StaticRegexReplaceStringFilter("\\\\t", "\t"),
			new StaticRegexReplaceStringFilter("\\\\n", "\n"),
			new StaticRegexReplaceStringFilter("\\\\r", "\r")
		});
		cooked.connectSource(raw);
		return cooked;
	}
	
	private List<TractFilter> getFilters(StringSource spec) {
		List<TractFilter> ret = new ArrayList<TractFilter>();
		if (spec != null) {
			for (String line = spec.nextString(); line != null; line = spec.nextString()) {
				TractFilter filter = createFilter(line.trim());
				if (filter != null) {
					ret.add(filter);
				}
			}
	
			ret.add(new JoinTractFilter());
		}
		return ret;
	}

	private TractFilter createFilter(String line) {
		SplitStringFilter split = new SplitStringFilter();
		split.connectSource(new StringStringSource(line));
		
		split.setSeparator(' ');
		String filterClass = split.nextString();

		split.setSeparator('\0');
		String tail = split.nextString();

		TractFilter ret = (TractFilter)ClassUtils.createObject(filterClass);
		if (ret != null && ret instanceof Transformation) {
			((Transformation)ret).init(tail);
		}
		
		return ret;
	}
}
