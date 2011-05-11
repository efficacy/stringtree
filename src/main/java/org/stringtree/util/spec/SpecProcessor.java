package org.stringtree.util.spec;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.stringtree.Fetcher;
import org.stringtree.Listable;
import org.stringtree.Repository;
import org.stringtree.SystemContext;
import org.stringtree.fetcher.FallbackRepository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.FetcherStringKeeper;
import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringFinderHelper;
import org.stringtree.finder.StringKeeper;
import org.stringtree.finder.live.IndirectStringLink;
import org.stringtree.finder.live.ObjectLink;
import org.stringtree.finder.live.ResourceLink;
import org.stringtree.json.BufferErrorListener;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;
import org.stringtree.util.ContextClassUtils;
import org.stringtree.util.StringUtils;
import org.stringtree.util.URLReadingUtils;
import org.stringtree.util.iterator.ReaderLineIterator;

class ObjectCreationHandler extends SimpleHandler {
    
    private StringFinder context;

    public ObjectCreationHandler(StringFinder context) {
        this.context = context;
    }

    public Object parseString(String name, String text) {
        list.add(name);
        return ContextClassUtils.createObject(text, context, false);
    }
}

class JSONErrorReporter extends BufferErrorListener {
    
    private String name;

    public JSONErrorReporter(String name) {
        this.name = name;
    }
    
    public void end() {
        if (buffer.length() > 0) {
            System.err.println("Warning! JSON Error on context value '" + name + "': " + buffer.toString());
        }
    }
}

public class SpecProcessor {
    
    public static final String LINK = "org.stringtree.factory.live.StringLink ";
    public static final String COMMENT = "#";
    public static final Object NO_ACTION = new Object();

    protected Repository repository;

    private List<HandlerDefinition> types;

    public SpecProcessor(final StringKeeper context) {
        this.repository = context;
        types = new ArrayList<HandlerDefinition>();

        types.add(new HandlerDefinition("[]", new ArrayFormat()));
        types.add(new HandlerDefinition(",", new CSVFormat()));

        types.add(new HandlerDefinition("$", new ObjectCreationHandler(context)));

        types.add(new HandlerDefinition("~", new SimpleHandler() {
            public Object parseString(String name, String text) {
                return list.add(new ObjectLink(text));
            }
        }));
        types.add(new HandlerDefinition("^", new SimpleHandler() {
            public Object parseString(String name, String text) {
                return list.add(new IndirectStringLink(text));
            }
        }));
        types.add(new HandlerDefinition("%", new SimpleHandler() {
            public Object parseString(String name, String text) {
                JSONReader reader = new JSONValidatingReader(new JSONErrorReporter(name));
                return reader.read(text);
            }
        }));
        types.add(new HandlerDefinition("@", new SimpleOrIncludeHandler() {
            public Object parseString(String name, String text) {
                return URLReadingUtils.readURL(text);
            }
            public void include(String name) throws IOException {
                read(name);
            }
        }));
        types.add(new HandlerDefinition("!", new SimpleOrIncludeHandler() {
            public Object parseString(String name, String text) {
                return list.add(new ResourceLink(text));
            }
            public void include(String name) throws IOException {
                readResource(name);
            }
        }));
        types.add(new HandlerDefinition("\"", new SimpleHandler() {
            public Object parseString(String name, String text) {
                return text.substring(1, text.length() - 1);
            }
        }));
        types.add(new HandlerDefinition("+", new MethodCallHandler("add") {
            public void addDefault(String name) {
                StorerHelper.put(context, name, new ArrayList<Object>());
            }
        }));
        types.add(new HandlerDefinition("-", new MethodCallHandler("remove") {
            public void addDefault(String name) {
                // this method intentionally left blank
            }
        }));
        types.add(new HandlerDefinition("?", new RememberingHandler(repository) {
            public Object parse(String name, Object value) {
                Object old = null;
                if (storer instanceof Fetcher) {
                    Fetcher fetcher = (Fetcher)storer;
                    old = fetcher.getObject(name);
                }
                if (null==old) {
                    storer.put(name, value);
                }
                return NO_ACTION;
            }
        }));
        types.add(new HandlerDefinition("#", new ContextHandler() {
            @SuppressWarnings("unchecked")
            public Object parseString(String name, String value) {
                Object obj = context.getObject(name);
                if (null == obj) {
                    obj = new LinkedHashMap<String, Object>();
                } else {
                    if (!(obj instanceof Map)) return NO_ACTION;
                }
                Map<String, Object> map = (Map<String, Object>)obj;
                Repository subcontext = new FallbackRepository(new MapFetcher(map), context);
                SpecProcessor.this.process(subcontext, value);
                return list.add(map);
            }
        }));
    }

    public SpecProcessor(StringFinder context) {
        this(StringFinderHelper.ensureKeeper(context));
    }

    public SpecProcessor(Fetcher context) {
        this(new FetcherStringKeeper(context));
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    private void process(Repository context, String name, String text, boolean escaped) {
        Object value = null;
        Iterator<HandlerDefinition> it;

        boolean found;
        do {
            found = false;
            it = types.iterator();
            while (it.hasNext()) {
                HandlerDefinition def = (HandlerDefinition) it.next();
                if (name.endsWith(def.pattern)) {
                    name = name.substring(0, name.length() - def.pattern.length()).trim();
                    value = def.handler.parse(name, value != null ? value : text);
                    found = true;
                    break;
                }
            } 
        } while (true == found);

        if (value == NO_ACTION) {
            // do nothing, the handler has already done it
        } else if (value != null) {
            context.put(name, value);
        } else {
            context.put(name, escaped ? unescape(text) : text);
        }
    }

	@SuppressWarnings("rawtypes")
	public void read(StringFinder values) {
        if (values instanceof Listable) {
            Iterator it = ((Listable)values).list();
            while (it.hasNext()) {
                String name = (String) it.next();
                repository.put(name, values.getObject(name));
            }
        }
    }

    public void read(Properties properties) {
        Iterator<Object> it = properties.keySet().iterator();

        while (it.hasNext()) {
            String name = (String) it.next();
            String text = properties.getProperty(name);
            process(repository, name, text, false);
        }
    }

    public void load(StringFinder values) {
        start();
        read(values);
        finish();
    }

    public void read(String[] lines) {
        for (int i = 0; i < lines.length; ++i) {
            process(lines[i], false);
        }
    }

    public void load(String[] lines) {
        start();
        read(lines);
        finish();
    }

    public void read(Reader in, boolean autoclose) {
        read(createLineIterator(in, autoclose));
    }

    public void read(Iterator<String> it) {
        while (it.hasNext()) {
            String line = it.next();
            if (StringUtils.isBlank(line))
                break;

            process(line, true);
        }
    }

    public Iterator<String> createLineIterator(Reader in, boolean autoclose) {
        Iterator<String> it = new ReaderLineIterator(in, autoclose);
        return it;
    }

    public void read(Reader in) {
        read(in, true);
    }

    public void load(Reader in, boolean autoclose) {
        start();
        read(in, autoclose);
        finish();
    }

    public void read(InputStream in, boolean autoclose) {
        if (in != null) {
            read(new InputStreamReader(in), autoclose);
        }
    }

    public void read(InputStream in) {
        read(in, true);
    }

    public void readResource(String name) {
        ClassLoader loader = null;
        Object cl = repository.getObject(SystemContext.SYSTEM_CLASSLOADER);
        if (cl instanceof ClassLoader) {
            loader = (ClassLoader) cl;
        }
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }
        
        InputStream in = loader.getResourceAsStream(name);
        if (in != null) read(loader.getResourceAsStream(name), true);
    }

    public void read(String from, boolean autoclose) throws IOException {
        if (StringUtils.isBlank(from))
            return;

        URL url = URLReadingUtils.findURL(from, "file");
        read(url, autoclose);
    }
    
    public InputStream open(URL url) throws IOException {
        InputStream ret = null;
        try {
            ret = url.openStream();
        } catch (FileNotFoundException e) {
            // Business as usual, if file is missing, act as if it was present but empty
            System.err.println("warning, attempt to load spec from missing resource '" + url + "'");
            ret = new ByteArrayInputStream(new byte[]{});
        }
        return ret;
    }
    
    public void read(URL url, boolean autoclose) throws IOException {
        try {
            InputStream in = open(url);
            read(in, autoclose);
        } catch (FileNotFoundException e) {
            // Business as usual, if file is missing, act as if it was present but empty
            System.err.println("warning, attempt to load spec from missing file '" + url + "'");
        }
    }

    public void read(URL url) throws IOException {
        read(url, true);
    }

    public void load(String from, boolean autoclose) throws IOException {
        start();
        read(from, autoclose);
        finish();
    }

    public void load(String from) throws IOException {
        load(from, true);
    }

    public void read(String from) throws IOException {
        read(from, true);
    }

    public void process(Repository context, String line, boolean escaped) {
        if (StringUtils.isBlank(line) || line.startsWith(COMMENT))
            return;

        int sep = split(line);
        
        if (sep >= 0) {
            String name = line.substring(0, sep).trim();
            String text = line.substring(sep + 1).trim();
            process(context, name, text, escaped);
        }
    }

    public void process(String line, boolean escaped) {
        process(repository, line, escaped);
    }

    public void process(Repository context, String line) {
        process(context, line, false);
    }

    public void process(String line) {
        process(line, false);
    }

    public static String unescape(String text) {
        if (text.indexOf('\\') == -1)
            return text;

        StringBuffer buf = new StringBuffer();
        boolean escaped = false;

        CharacterIterator ci = new StringCharacterIterator(text);
        for (char c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
            if (escaped) {
                if (c == '\\')
                    buf.append('\\');
                else if (c == 'n')
                    buf.append('\n');
                else if (c == 'r')
                    buf.append('\r');
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else {
                buf.append(c);
            }
        }

        return buf.toString();
    }

    public void start() {
        Iterator<HandlerDefinition> it = types.iterator();
        while (it.hasNext()) {
            HandlerDefinition def = it.next(); 
            def.handler.open(StringFinderHelper.ensureFinder(repository));
        }
    }

    public void finish() {
        Iterator<HandlerDefinition> it = types.iterator();
        while (it.hasNext()) {
            HandlerDefinition def = it.next(); 
            def.handler.close(StringFinderHelper.ensureFinder(repository));
        }
    }

    private int split(String line) {
        int sep = -1;
        int sepColon = line.indexOf(':');
        int sepEquals = line.indexOf("=");

        if (sepColon == -1)
            sep = sepEquals;
        else if (sepEquals == -1)
            sep = sepColon;
        else
            sep = Math.min(sepColon, sepEquals);
        
        return sep;
    }
}
