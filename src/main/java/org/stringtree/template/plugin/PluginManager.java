package org.stringtree.template.plugin;

import java.util.Collection;
import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringKeeper;
import org.stringtree.template.ByteArrayStringCollector;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;
import org.stringtree.util.MethodCallUtils;

public class PluginManager {
	public static final String PLUGINS = "plugins";
    public static final String PLUGIN_MANAGER = "plugin";
    public static final String PLUGIN_TEMPLATE = "plugin_template";
    public static final String PLUGIN_METHOD_NAME = "plugin_method_name";

    @SuppressWarnings("rawtypes")
	public String get(StringKeeper context, String name) {
        Collection plugins = (Collection) context.getObject(PLUGINS);
        Templater templater = (Templater) context.getObject(Templater.TEMPLATER);
        Fetcher templates = (Fetcher) context.getObject(Templater.TEMPLATE);
        if (null == templater || null == templates || null == plugins || plugins.isEmpty()) return "";
        
        context.put(PLUGIN_METHOD_NAME, name);
		StringCollector collector = new ByteArrayStringCollector();
		Iterator it = plugins.iterator();

		while (it.hasNext()) {
			Plugin plugin = (Plugin)it.next();
		    if (plugin.match(context)) {
				MethodCallUtils.callOptionalContext(plugin, name, context);
				Object obj = context.getObject(PLUGIN_TEMPLATE);
				if (null != obj) {
				    String template = (String)templates.getObject((String)obj);
				    if (null != template) {
				        templater.expandTemplate(context, template, collector);
				    }
	                context.remove(PLUGIN_TEMPLATE);
				}
			}
		}
		
		return collector.toString();
	}
}
