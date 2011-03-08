package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.juicer.Initialisable;
import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.tract.PassTractFilter;

public class InitialisableTractFilter extends PassTractFilter implements Initialisable {
    
	private Fetcher context = null;
	
	public void init(Fetcher context) {
		this.context = context;
	}
	
	public Tract filter(Tract input) {
		Tract ret = input;
		
		if (context != null && input != null) {
			Object content = context.getObject(input.getContent());
			if (content != null) {
				ret = JuicerConvertHelper.expand((String)content, input);
			}
		}
		
		return ret;
	}
}
