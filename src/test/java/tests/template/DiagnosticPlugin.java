package tests.template;

import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringKeeper;
import org.stringtree.template.plugin.Plugin;
import org.stringtree.template.plugin.PluginManager;

public class DiagnosticPlugin implements Plugin {

	public boolean match(StringFinder context) {
		return "thing".equals(context.getObject(PluginManager.PLUGIN_METHOD_NAME));
	}
	
	public void thing(StringKeeper context) {
		context.put("result", "plugin method 'thing' called");
		context.put(PluginManager.PLUGIN_TEMPLATE, "plugin_thing");
	}

}
