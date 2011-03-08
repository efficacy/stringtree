package org.stringtree.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.TractDirectoryFetcher;
import org.stringtree.fetcher.TractURLFetcher;
import org.stringtree.finder.FetcherTractFinder;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class TreeTemplater extends RecursiveTemplater {

    private Fetcher templates;

	public TreeTemplater(Fetcher templates) {
		setTemplates(templates);
	}

	private void setTemplates(Fetcher templates) {
		this.templates = new FetcherTractFinder(templates);
	}

	public TreeTemplater(File root) {
		if (!root.isDirectory()) {
			throw new IllegalArgumentException("TreeTemplater root is not a directory");
		}
		setTemplates(new TractDirectoryFetcher(root, null));
	}

	
	public TreeTemplater(String root) {
		TractURLFetcher templates = new TractURLFetcher(root);
		setTemplates(templates);
	}

    protected Object getTemplate(String templateName, Fetcher context) {
    	String path = templateName;
    	if (templateName.contains(".")) {
    		path = templateName.replace('.', '/');
    	}
    	
    	Object ret = templates.getObject(path);
    	if (null == ret) return null;
    	
    	if (path.contains("/")) {
    		StringBuffer parent = new StringBuffer();
    		List<Object> prologues = new ArrayList<Object>();
    		List<Object> epilogues = new ArrayList<Object>();
    		for (String dir : path.split("/")) {
    			parent.append(dir);
    			parent.append("/");
        		Object prologue = templates.getObject(parent + "/" + "prologue");
        		if (null != prologue) prologues.add(prologue);
        		Object epilogue = templates.getObject(parent + "/" + "epilogue");
        		if (null != epilogue) epilogues.add(epilogue);
    		}
    		if (!epilogues.isEmpty() || !prologues.isEmpty()) {
    			List<Object> combined = new ArrayList<Object>();
    			combined.addAll(prologues);
    			combined.add(ret);
    			Collections.reverse(epilogues);
    			combined.addAll(epilogues);
    			ret = combined;
    		}
    	}
        return ret;
    }

    /**
     * expand a directly-supplied template, which may be a String or a Tract,
     * filling in substitutions from the supplied context.
     */
    @SuppressWarnings("unchecked")
	public void expandTemplate(StringFinder context, Object tpl, StringCollector collector) {
    	if (tpl instanceof Tract) {
            expandTract(context, (Tract) tpl, collector);
        } else if (tpl instanceof Collection) {
    		for (Object entry : ((Collection<Object>)tpl)) {
    			expandTemplate(context, entry, collector);
    		}
    	} else if (tpl != null) {
            expandString(context, StringUtils.stringValue(tpl), collector);
        }
    }
}
