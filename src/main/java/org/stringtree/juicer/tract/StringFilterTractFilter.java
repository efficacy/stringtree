package org.stringtree.juicer.tract;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.JuicerLockHelper;
import org.stringtree.juicer.string.PassStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringStringSource;

public class StringFilterTractFilter extends BasicTractFilter implements Initialisable {
    
	protected StringFilter filter;
	protected boolean polite;

	public StringFilterTractFilter(StringFilter filter, boolean polite) {
		setFilter(filter);
		setPolite(polite);
	}

	public StringFilterTractFilter(StringFilter filter) {
		this(filter, true);
	}

	public StringFilterTractFilter() {
		this(PassStringFilter.it, true);
	}

	public void setFilter(StringFilter filter) {
		this.filter = filter;
	}

	public void setPolite(boolean polite) {
		this.polite = polite;
	}

	public Tract filter(Tract input) {
		filter.connectSource(new StringStringSource(input.getContent()));
		if (polite == false || !JuicerLockHelper.isLocked(input)) {
			input.setContent(filter.nextString());
		}
		return input;
	}

	public void init(Fetcher context) {
		if (filter instanceof Initialisable) {
			((Initialisable)filter).init(context);
		}
	}
}
