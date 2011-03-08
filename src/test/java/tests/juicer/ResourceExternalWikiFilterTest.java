package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.juicer.formatter.ExternalFormatter;
import org.stringtree.util.ResourceUtils;

public class ResourceExternalWikiFilterTest extends WikiFilterTest {
    
	private String filename = "wiki.transform";

	protected StringFinder createFormatter(Fetcher context) {
		type = "ResourceExternalWikiFilter";
		String spec = ResourceUtils.readResource(this, filename);
        return new ExternalFormatter(spec, context);
	}
}

