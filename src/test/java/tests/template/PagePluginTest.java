package tests.template;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.TractURLFetcher;
import org.stringtree.finder.MapStringKeeper;
import org.stringtree.finder.StringKeeper;
import org.stringtree.template.DirectFetcherTemplater;
import org.stringtree.template.Templater;
import org.stringtree.template.TemplaterHelper;
import org.stringtree.template.plugin.PluginManager;

public class PagePluginTest extends TestCase {
	StringKeeper context;
	Templater templater;
	List<Object> plugins;
	
	public void setUp() {
		context = new MapStringKeeper();
		Fetcher fetcher = new TractURLFetcher("testfiles/plugins/templates/");
        templater = new DirectFetcherTemplater(fetcher);
		plugins = new ArrayList<Object>();
		
		context.put(Templater.TEMPLATER, templater);
		context.put(Templater.TEMPLATE, fetcher);
		context.put("plugins", plugins);
		context.put("plugin", new PluginManager());
	}
	
	public void testPluginCall() {
		plugins.add(new DiagnosticPlugin());
		String page = TemplaterHelper.expand(templater, context, "plugin_test");
		assertEquals("before[hello from thing=plugin method 'thing' called]after", page);
	}
}
