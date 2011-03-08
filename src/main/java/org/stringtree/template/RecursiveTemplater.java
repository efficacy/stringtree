package org.stringtree.template;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.FallbackFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.FetcherStringKeeper;
import org.stringtree.finder.StringFinder;
import org.stringtree.template.pattern.AssignmentPatternHandler;
import org.stringtree.template.pattern.IndirectPatternHandler;
import org.stringtree.template.pattern.IteratingPatternHandler;
import org.stringtree.template.pattern.LiteralPatternHandler;
import org.stringtree.template.pattern.PeelbackPatternHandler;
import org.stringtree.template.pattern.PipePatternHandler;
import org.stringtree.template.pattern.PresentAbsentPatternHandler;
import org.stringtree.template.pattern.TemplatePatternHandler;
import org.stringtree.util.NullToEmptyString;
import org.stringtree.util.ObjectToString;
import org.stringtree.util.StringUtils;

public abstract class RecursiveTemplater implements Templater {
    
    private static final int OUTSIDE = 0;
    private static final int ENTERING = 0x10000;
    private static final int INSIDE = 0x2000;
    private static final char PILOT = '$';
    private static final char START = '{';
    private static final char END = '}';
    private static final ObjectToString DEFAULT_STRING_CONVERTER = new NullToEmptyString();
    
    private ObjectToString converter = DEFAULT_STRING_CONVERTER;

    /**
     * expand a named template, filling in substitutions from the supplied
     * context.
     * 
     * Note that if the template name starts with '@' the rest of the template
     * name is looked up in the context instead
     */
    public void expand(StringFinder context, String templateName, StringCollector collector) {
        Object tpl = templateName.startsWith("@") 
            ? context.getObject(templateName.substring(1))
            : getTemplate(templateName, context);
        expandTemplate(context, tpl, collector);
    }

    /**
     * expand a directly-supplied template, which may be a String or a Tract,
     * filling in substitutions from the supplied context.
     */
    public void expandTemplate(StringFinder context, Object tpl, StringCollector collector) {
        if (tpl instanceof Tract) {
            expandTract(context, (Tract) tpl, collector);
        } else if (tpl != null) {
            expandString(context, StringUtils.stringValue(tpl), collector);
        }
    }

    protected void expandString(StringFinder context, String template, StringCollector collector) {
        int state = OUTSIDE;
        StringBuffer token = new StringBuffer();
        CharacterIterator it = new StringCharacterIterator(template);
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            switch (state + c) {
            case OUTSIDE + PILOT:
                state = ENTERING;
                break;
            case ENTERING + START:
                state = INSIDE;
                break;
            case INSIDE + END:
                state = OUTSIDE;
                String trimmed = token.toString().trim();
                String toWrite = get(trimmed, context, collector);
                collector.write(toWrite);
                token.setLength(0);
                break;
            default:
                switch (state) {
                case ENTERING: // got $ without { - treat it as a literal
                    state = OUTSIDE;
                    collector.write(PILOT);
                    collector.write(c);
                    break;
                case INSIDE:
                    token.append(c);
                    break;
                default:
                    collector.write(c);
                }
            }
        }

        if (state == ENTERING) { // catch the odd case of $ at EOF
            collector.write(PILOT);
        } else if (state == INSIDE) { // catch the even odder case of unclosed
                                        // ${ at EOF
            collector.write(PILOT);
            collector.write(START);
            collector.write(token.toString());
        }
    }

    protected void expandTract(StringFinder context, Tract tract,
            StringCollector collector) {
        Fetcher parent = context.getUnderlyingFetcher();
        Fetcher child = tract.getUnderlyingFetcher();
        StorerHelper.put(child, Templater.PARENT, parent);
        StringFinder subcontext = new FetcherStringKeeper(new FallbackFetcher(child, parent));
        expandString(subcontext, tract.getContent(), collector);
    }

    protected abstract Object getTemplate(String templateName, Fetcher context);

    private TemplatePatternHandler[] handlers = new TemplatePatternHandler[] {
            new AssignmentPatternHandler(),
            new IndirectPatternHandler(),
            new PresentAbsentPatternHandler(), 
            new LiteralPatternHandler(), 
            new PeelbackPatternHandler(),
            new IteratingPatternHandler(),
            new PipePatternHandler()
        };

    public Object getObject(String name, StringFinder context, StringCollector collector) {
        if (name == null)
            return null;

        Object ret = context.getObject(name);
        for (int i = 0; ret == null && i < handlers.length; ++i) {
            ret = handlers[i].getObject(name, context, this, collector);
        }

        return ret;
    }

    public String get(String name, StringFinder context, StringCollector collector) {
        return convert(getObject(name, context, collector));
    }
    
    public void setStringConverter(ObjectToString converter) {
        this.converter = converter;
    }

    private String convert(Object obj) {
        String ret = converter.convert(obj);
        return ret;
    }
}