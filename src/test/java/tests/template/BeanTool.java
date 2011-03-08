package tests.template;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.BeanFetcher;
import org.stringtree.finder.StringKeeper;

public class BeanTool {
    public boolean get(StringKeeper context, String name) {
        Fetcher fetcher = new BeanFetcher(context.getObject("this"));
        Object value = fetcher.getObject(name);
        if (null != value) {
            context.put("name", name);
            context.put("value", value);
            return true;
        }
        return false;
    }
}
