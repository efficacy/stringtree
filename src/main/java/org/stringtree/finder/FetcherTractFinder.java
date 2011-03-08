package org.stringtree.finder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.FetcherTractHelper;
import org.stringtree.fetcher.SuffixDefinition;
import org.stringtree.streams.StringStreamConverter;
import org.stringtree.streams.TractStreamConverter;
import org.stringtree.tract.TractHelper;

public class FetcherTractFinder extends FetcherObjectFinder implements TractFinder {
    static final Collection<SuffixDefinition> dflSuffices = Arrays.asList(new SuffixDefinition[] {
            new SuffixDefinition("tpl", new StringStreamConverter()), 
            new SuffixDefinition("tract", new TractStreamConverter())
        });

    private Collection<SuffixDefinition> suffices;
    
    public FetcherTractFinder(Fetcher fetcher, Collection<SuffixDefinition> suffices) {
        super(fetcher);
        this.suffices = suffices;
    }
    
    public FetcherTractFinder(Fetcher fetcher) {
        this(fetcher, dflSuffices);
    }

    public Tract get(String name) {
        return FetcherTractHelper.getTract(fetcher, name);
    }

    public Object getObject(String name) {
        Object ret = null;
        Iterator<SuffixDefinition> tails = suffices.iterator();
       	Object object = fetcher.getObject(name);
    	if (null != object) return TractHelper.convert(object);
        while (tails.hasNext()) {
            SuffixDefinition def = tails.next();
            	object = fetcher.getObject(name + "." + def.getSuffix());
            	if (null != object) return TractHelper.convert(object);
        }
        return ret;
    }
}
