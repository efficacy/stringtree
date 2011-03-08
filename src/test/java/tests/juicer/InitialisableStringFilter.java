package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.string.PassStringFilter;
import org.stringtree.juicer.string.StringSource;

public class InitialisableStringFilter extends PassStringFilter implements Initialisable {
    
	private Fetcher context = null;
	
	public InitialisableStringFilter(StringSource source) {
		super(source);
	}

	public InitialisableStringFilter() {
    	// this method intentionally left blank
	}

	public void init(Fetcher context) {
		this.context = context;
	}
	
	public String filter(String input) {
		if (context == null) return input;
		Object ret = context.getObject(input);
		if (ret == null) return input;
		return (String)ret;
	}
}
