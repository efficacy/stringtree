package tests.template;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.CachedFetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.ResourceFetcher;
import org.stringtree.fetcher.SuffixResourceFilter;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.StringFinder;
import org.stringtree.template.ByteArrayStringCollector;
import org.stringtree.template.DirectFetcherTemplater;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;
import org.stringtree.template.TemplaterHelper;
import org.stringtree.tract.MapTract;
import org.stringtree.util.XMLEscaper;
import org.stringtree.util.sort.Sorter2;

import junit.framework.TestCase;

public class DirectTemplateTest extends TestCase {
    
    private static final String NL = System.getProperty("line.separator");
    Templater templater;
    Map<String, Object> tplstore;
    Fetcher templates;
    MapFetcher context;
    StringCollector collector;
    StringFinder finder;

    public void setUp() {
        tplstore = new HashMap<String, Object>();
        context = new MapFetcher();
        finder = new FetcherStringFinder(context);
        templates = new MapFetcher(tplstore);
        templater = new DirectFetcherTemplater(templates);
        collector = new ByteArrayStringCollector();
        context.put("template", templates);
    }

    public void testSolePilot() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello$there");
        assertEquals("hello$there", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl1", "$there");
        assertEquals("$there", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testFragmentAtEOF() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl1", "hello$");
        assertEquals("hello$", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl1", "hello${");
        assertEquals("hello${", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl1", "hello${ugh");
        assertEquals("hello${ugh", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testSimpleExpand() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello");
        assertEquals("hello", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${name}'");
        assertEquals("hello ''", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl2", "hello '${  name  }'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testSimpleTractExpand() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", new MapTract("hello"));
        assertEquals("hello", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        Tract tract = new MapTract("hello '${name}'");
        tplstore.put("tpl1", tract);
        assertEquals("hello ''", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        context.put("name", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testTractExpandWithValues() {
        context.put("name", "Frank");

        Tract t1 = new MapTract("hello '${name}'");
        tplstore.put("tpl1", t1);

        Tract t2 = new MapTract("hello '${name}'");
        t2.put("name", "Margaret");
        tplstore.put("tpl2", t2);

        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
        assertEquals("hello 'Margaret'", TemplaterHelper.expand(templater, finder, "tpl2"));
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testSingleLetterNames() {
        tplstore.put("tpl3", "y='${y}'");
        assertEquals("y=''", TemplaterHelper.expand(templater, finder, "tpl3"));
        context.put("y", "register");
        assertEquals("y='register'", TemplaterHelper.expand(templater, finder, "tpl3"));

        tplstore.put("tpl3", "z='${z}'");
        assertEquals("z=''", TemplaterHelper.expand(templater, finder, "tpl3"));
        context.put("z", "register");
        assertEquals("z='register'", TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testInclude() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${*tpl2}'");
        tplstore.put("tpl2", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "hello '${  * tpl2  }'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testSingleIterate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*tpl2}");
        tplstore.put("tpl2", "hello '${this}'");
        context.put("name", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "${  name  *  tpl4  }");
        tplstore.put("tpl4", "hello '${ this  }'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMissingIterate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*tpl2}");
        tplstore.put("tpl2", "Found");
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "${name*tpl2%'Not Found'}");
        assertEquals("Not Found", TemplaterHelper.expand(templater, finder, "tpl3"));

        tplstore.put("tpl4", "${name*tpl2%*tpl5}");
        tplstore.put("tpl5", "huh?");
        assertEquals("huh?", TemplaterHelper.expand(templater, finder, "tpl4"));
    }

    public void testSingleConstantIterate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${'Frank'*tpl2}");
        tplstore.put("tpl2", "hello '${this}'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "${  'Frank'  *  tpl4  }");
        tplstore.put("tpl4", "hello '${ this  }'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testIterateIndirectTemplate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*@nametpl}");
        context.put("name", "Frank");
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("nametpl", "hello '${this}'");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder,"tpl1"));
    }

    public void testMultiIterate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*tpl2}");
        tplstore.put("tpl2", "hello '${this}' ");
        context.put("name", new Object[] { "Frank", "Margaret" });
        assertEquals("hello 'Frank' hello 'Margaret' ", 
        		TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "${  name  *  tpl4  }");
        tplstore.put("tpl4", "hello '${  this  }' ");
        assertEquals("hello 'Frank' hello 'Margaret' ", 
        		TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testSeparate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*tpl2/', '}");
        tplstore.put("tpl2", "hello '${this}'");
        context.put("name", new Object[] { "Frank", "Margaret" });
        assertEquals("hello 'Frank', hello 'Margaret'", 
        		TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "${  name  *  tpl4  /  ', '}");
        tplstore.put("tpl4", "hello '${  this  }'");
        assertEquals("hello 'Frank', hello 'Margaret'", 
        		TemplaterHelper.expand(templater, finder, "tpl3"));

        tplstore.put("tpl3", "${  name  *  tpl4  /  }");
        tplstore.put("tpl4", "hello '${  this  }'");
        assertEquals("hello 'Frank'" + NL + "hello 'Margaret'", 
        		TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testSelfSeparate() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "${name*/','}");
        context.put("name", new Object[] { "Frank", "Margaret" });
        assertEquals("Frank,Margaret", TemplaterHelper.expand(templater, finder, "tpl1"));

        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl2"));
        tplstore.put("tpl2", "${ name * / ','}");
        assertEquals("Frank,Margaret", TemplaterHelper.expand(templater, finder, "tpl2"));

        tplstore.put("tpl2", "${ name * / }");
        assertEquals("Frank" + NL +"Margaret", TemplaterHelper.expand(templater, finder, "tpl2"));
    }

    public void testPresentAbsent() {
        context.put("this", "Huh");
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello ${name?*tpl2:*tpl3}");
        tplstore.put("tpl2", "${this}, Dude!");
        tplstore.put("tpl3", "Nobody");
        assertEquals("hello Nobody", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello Huh, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl4", "hello ${  name  ? * tpl5 : * tpl3 }");
        tplstore.put("tpl5", "${this}, Dude!");
        assertEquals("hello Huh, Dude!", TemplaterHelper.expand(templater, finder, "tpl4"));
        
        context.put("name", Boolean.FALSE);
        assertEquals("hello Nobody", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testJustPresent() {
        context.put("this", "Huh");
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello ${name?*tpl2}");
        tplstore.put("tpl2", "${this}, Dude!");
        assertEquals("hello ", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello Huh, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "hello ${ name ? * tpl4 }");
        tplstore.put("tpl4", "${ this }, Dude!");
        assertEquals("hello Huh, Dude!", TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testJustAbsent() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello ${name:*tpl2}");
        tplstore.put("tpl2", "${this}, Dude!");
        assertEquals("hello , Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello ", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "hello ${ name2 : * tpl4 }");
        tplstore.put("tpl4", "${ this }, Dude!");
        assertEquals("hello , Dude!", TemplaterHelper.expand(templater, finder, "tpl3"));
        context.put("name2", "Frank");
        assertEquals("hello ", TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testPresentAbsentMixed() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name?name:*tpl2}");
        tplstore.put("tpl2", "Dude!");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "hello, ${ name2 ? name2 : * tpl2 }");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl3"));
        context.put("name2", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testPresentAbsentSelf() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name?:*tpl2}");
        tplstore.put("tpl2", "Dude!");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl3", "hello, ${ name2 ?  : * tpl2 }");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl3"));
        context.put("name2", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl3"));
    }

    public void testPresentAbsentLiteral() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name?name:'Dude!'}");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl2", "hello, ${ name2 ? name2 : 'Dude!' }");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl2"));
        context.put("name2", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl2"));
    }

    public void testPresentAbsentConstant() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${'Frank'?:'Dude!'}");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testPresentAbsentObject() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        Date now = new Date(1159872513481L);
        context.put("now", now);
        tplstore.put("tpl1", "hello, ${'Frank'?now:'Dude!'}");
        assertEquals("hello, " + now.toString(), TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testComment() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${? this is a short comment }");
        assertEquals("hello, ", TemplaterHelper.expand(templater, finder, "tpl1"));

        tplstore.put("tpl1", "hello, ${? this is a \n multi-line \r\n long comment }");
        assertEquals("hello, ", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testDeep() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${name*tpl2}'");
        tplstore.put("tpl2", "${*tpl3}");
        tplstore.put("tpl3", "${*tpl4}");
        tplstore.put("tpl4", "${this}");
        context.put("name", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testDeepValues() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${name*tpl2}'");
        tplstore.put("tpl2", "${this} ${'xx'*tpl3}");
        tplstore.put("tpl3", "${this} ${'yy'*tpl4}");
        tplstore.put("tpl4", "${this}");
        context.put("name", "Frank");
        assertEquals("hello 'Frank xx yy'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testDeepThis() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${name*tpl2}'");
        tplstore.put("tpl2", "${this} ${'xx'*tpl3}");
        tplstore.put("tpl3", "${this} ${'yy'*tpl4}");
        tplstore.put("tpl4", "${this}");
        context.put("name", "Frank");
        assertEquals("hello 'Frank xx yy'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testDeepWhitespace() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello '${  name * tpl2 }'");
        tplstore.put("tpl2", "${ * tpl3 }");
        tplstore.put("tpl3", "${ * tpl4 }");
        tplstore.put("tpl4", "${ this }");
        context.put("name", "Frank");
        assertEquals("hello 'Frank'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void subtestResourceTemplates() {
        // single-level substitution
        assertEquals("\"${FACADE_JNDI}\"", templates.getObject("FacadeJNDI"));
        assertEquals("\"\"", TemplaterHelper.expand(templater, finder, "FacadeJNDI"));
        context.put("FACADE_JNDI", "jdbc.example");
        assertEquals("\"jdbc.example\"", TemplaterHelper.expand(templater, finder, "FacadeJNDI"));

        // substitution and conditional included templates
        assertEquals(
                "\"${FACADE_DRIVER}\", \"${FACADE_URL}\"${FACADE_USER?*FacadeUser}",
                templates.getObject("FacadeDriver"));
        assertEquals("\"\", \"\"", TemplaterHelper.expand(templater, finder, "FacadeDriver"));
        context.put("FACADE_DRIVER", "com.efsol.MySQLDriver");
        assertEquals("\"com.efsol.MySQLDriver\", \"\"", 
        		TemplaterHelper.expand(templater, finder, "FacadeDriver"));
        context.put("FACADE_URL", "http://localhost:1234/");
        assertEquals("\"com.efsol.MySQLDriver\", \"http://localhost:1234/\"",
                TemplaterHelper.expand(templater, finder, "FacadeDriver"));
        context.put("FACADE_USER", "scott");
        context.put("FACADE_PASSWORD", "tiger");
        assertEquals(
                "\"com.efsol.MySQLDriver\", \"http://localhost:1234/\", \"scott\", \"tiger\"",
                TemplaterHelper.expand(templater, finder, "FacadeDriver"));
    }

    public void testRawResourceTemplates() {
        templates = new ResourceFetcher(Templater.class, new SuffixResourceFilter(".template"));
        templater = new DirectFetcherTemplater(templates);
        subtestResourceTemplates();
    }

    public void testCachedResourceTemplates() {
        templates = new CachedFetcher(new ResourceFetcher(Templater.class, new SuffixResourceFilter(".template")));
        templater = new DirectFetcherTemplater(templates);
        subtestResourceTemplates();
    }

    public void testTailSubstitution() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name?:forename?:'Dude!'}");
        assertEquals("hello, Dude!", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("forename", "Frank");
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Mr Carver");
        assertEquals("hello, Mr Carver", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodInvocation() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name.forename}");
        context.put("name", new FullName("Frank", "Carver"));
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodInvocationWithBrackets() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${name.forename()}");
        context.put("name", new FullName("Frank", "Carver"));
        assertEquals("hello, Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodInvocationWithLiteralBracketParameter() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${tool.underline('lala')}");
        context.put("tool", new Example());
        assertEquals("hello, _lala_", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodInvocationWithSymbolBracketParameter() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${tool.underline(name)}");
        context.put("tool", new Example());
        context.put("name", "Frank");
        assertEquals("hello, _Frank_", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodInvocationWithMultipleParameters() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl1", "hello, ${tool.dibble(name,count)}");
        context.put("tool", new Example());
        context.put("name", "Frank");
        context.put("count", 3);
        assertEquals("hello, FrankFrankFrank", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodReturnExpression() {
        assertEquals("", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("tool", new Example());
        tplstore.put("tpl1", "${tool.test(name)?'me':'you'}");
        context.put("name", "Frank");
        assertEquals("me", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Margaret");
        assertEquals("you", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMethodPassthrough() {
        context.put("text", "hello");
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text|converter.convert}");
        assertEquals("HELLO", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testComplexMethodPassthrough() {
        context.put("text", new FullName("Frank", "Carver"));
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text.forename|converter.convert}");
        assertEquals("FRANK", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testBeanMethodPassthrough() {
        context.put("text", "hello");
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text|converter.getItalic}");
        assertEquals("/hello/", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testConditionalPassthrough() {
        context.put("text", "hello");
        context.put("converter", new Example());
        tplstore.put("tpl1", "${switch?text|converter.convert:'goodbye'}");
        assertEquals("goodbye", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("switch", "on");
        assertEquals("HELLO", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testIteratingPassthrough() {
        context.put("text", "hello");
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text|converter.convert*tpl2}");
        tplstore.put("tpl2", "~~${this}~~");
        assertEquals("~~HELLO~~", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testMultiplePassthrough() {
        context.put("text", "hello");
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text|converter.convert|converter.underline}");
        assertEquals("_HELLO_", TemplaterHelper.expand(templater, finder, "tpl1"));
    }

    public void testNonStringPassthrough() {
        context.put("text", new Integer("123"));
        context.put("converter", new Example());
        tplstore.put("tpl1", "${text|converter.underline}");
        assertEquals("_123_", TemplaterHelper.expand(templater, finder, "tpl1"));
        tplstore.put("tpl2", "${text|converter.bold}");
        assertEquals("*123*", TemplaterHelper.expand(templater, finder, "tpl2"));
    }
    
    public void testDomainObjectBooleanPassThrough() {
        context.put("thing", new FullName("Frank", "Carver"));
        context.put("name.checker", new NameChecker());
        tplstore.put("tpl1", "${thing*tpl2}");
        tplstore.put("tpl2", "${this|name.checker.isDeveloper?'yup':'nope'}");
        assertEquals("yup", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testDateFormat() {
        context.put("date", new Date(1130838311844L));
        context.put("df", new SimpleDateFormat("yyyy-MM-dd"));
        tplstore.put("tpl1", "${date|df.format}");
        assertEquals("2005-11-01", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testLiteralAssignment() {
        assertEquals(null, context.getObject("ugh"));
        tplstore.put("tpl1", "x${ugh='hello'}y");
        assertEquals("xy", TemplaterHelper.expand(templater, finder, "tpl1"));
        assertEquals("hello", context.getObject("ugh"));
    }
    
    public void testParentAssignmentInTract() {
        Tract t1 = new MapTract("x${^ugh='hello'}y");
        t1.put(Templater.PARENT, context);
        tplstore.put("tpl1", t1);
        assertEquals("xy", TemplaterHelper.expand(templater, finder, "tpl1"));

        assertEquals(null, t1.getObject("ugh"));
        assertEquals("hello", context.getObject("ugh"));
    }
    
    public void testParentParentAssignmentInTract() {
        Fetcher parent = new MapFetcher();
        context.put(Templater.PARENT, parent);
        Tract t1 = new MapTract("x${^^ugh='hello'}y");
        t1.put(Templater.PARENT, context);
        tplstore.put("tpl1", t1);
        assertEquals("xy", TemplaterHelper.expand(templater, finder, "tpl1"));

        assertEquals(null, t1.getObject("ugh"));
        assertEquals(null, context.getObject("ugh"));
        assertEquals("hello", parent.getObject("ugh"));
    }
    
    public void testBaseAssignmentInTract() {
        Fetcher base = new MapFetcher();
        context.put(Templater.BASE, base);
        Tract t1 = new MapTract("x${@ugh='hello'}y");
        t1.put(Templater.PARENT, context);
        tplstore.put("tpl1", t1);
        assertEquals("xy", TemplaterHelper.expand(templater, finder, "tpl1"));

        assertEquals(null, t1.getObject("ugh"));
        assertEquals(null, context.getObject("ugh"));
        assertEquals("hello", base.getObject("ugh"));
    }
    
    public void testVariableAssignmentString() {
        assertEquals(null, context.getObject("ugh"));
        context.put("colour", "blue");
        tplstore.put("tpl1", "x${ugh=colour}y ${ugh}");
        assertEquals("xy blue", TemplaterHelper.expand(templater, finder, "tpl1"));
        assertEquals("blue", context.getObject("ugh"));
    }
    
    public void testVariableAssignmentObject() {
        assertEquals(null, context.getObject("ugh"));
        FullName name = new FullName("Frank", "Carver");
        context.put("admin", name);
        tplstore.put("tpl1", "x${ugh=admin}y ${ugh.forename}");
        assertEquals("xy Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
        assertEquals(name, context.getObject("ugh"));
    }

    public void testConditionalVariableAssignment() {
        assertEquals(null, context.getObject("ugh"));
        FullName name = new FullName("Frank", "Carver");
        context.put("admin", name);
        tplstore.put("tpl1", "x${ugh='true'?admin:'none'}y ${ugh.forename}");
        assertEquals("xy Frank", TemplaterHelper.expand(templater, finder, "tpl1"));
        assertEquals(name, context.getObject("ugh"));
    }
    
    public void testMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");
        //map.put("goodbye", "life");
        context.put("map", map);
        
        tplstore.put("map", "<map>${map*entry}</map>");
        tplstore.put("entry", "<entry>${this.key}=${this.value}</entry>");
        String ret = TemplaterHelper.expand(templater, finder, "map");
        assertEquals("<map><entry>hello=world</entry></map>", ret);
    }

    public void testSimpleExpandWithXMLEscape() {
        templater.setStringConverter(new XMLEscaper());
        
        tplstore.put("tpl1", "hello '${name}'");
        assertEquals("hello ''", TemplaterHelper.expand(templater, finder, "tpl1"));
        context.put("name", "Frank & Margaret");
        assertEquals("hello 'Frank &amp; Margaret'", 
                TemplaterHelper.expand(templater, finder, "tpl1"));

        context.put("name", "Frank > Margaret");
        assertEquals("hello 'Frank &gt; Margaret'", 
                TemplaterHelper.expand(templater, finder, "tpl1"));

        context.put("name", "Frank < Margaret");
        assertEquals("hello 'Frank &lt; Margaret'", 
                TemplaterHelper.expand(templater, finder, "tpl1"));

        context.put("name", "Frank \" Margaret");
        assertEquals("hello 'Frank &quot; Margaret'", 
                TemplaterHelper.expand(templater, finder, "tpl1"));

        context.put("name", "Frank ' Margaret");
        assertEquals("hello 'Frank &apos; Margaret'", 
                TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testPipeBeforeMultiply() {
        context.put("sorter", new Sorter2<String>());
        
        List<String> list = new ArrayList<String>(); 
        list.add("a");
        list.add("c");
        list.add("b");
        context.put("list", list);
        
        tplstore.put("tpl1", "${list|sorter.sort*/','}");
        assertEquals("a,b,c", TemplaterHelper.expand(templater, finder, "tpl1"));
     }
    
    public void testIndirectTemplate() {
        tplstore.put("tpl1", "x${@name}");
        tplstore.put("frank", "hello!");
        tplstore.put("nobody", "goodbye!");

        assertEquals("x", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        context.put("name", "frank");
        assertEquals("xhello!", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        context.put("name", "nobody");
        assertEquals("xgoodbye!", TemplaterHelper.expand(templater, finder, "tpl1"));
        
    }
    
    public void testTemplateAsValue() {
        tplstore.put("tpl1", "x${~tpl2}");
        tplstore.put("tpl2", "<a>");

        assertEquals("x<a>", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testIndirectValue() {
        tplstore.put("tpl1", "${name} = ${^name}");

        assertEquals(" = ", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        context.put("name", "frank");
        assertEquals("frank = ", TemplaterHelper.expand(templater, finder, "tpl1"));
        
        context.put("frank", "cool");
        assertEquals("frank = cool", TemplaterHelper.expand(templater, finder, "tpl1"));
        
    }
    
    public void testAssignmentPropagation() {
        tplstore.put("tpl1", "${a='hello'}${'ugh'*tpl2}");
        tplstore.put("tpl2", "${this} said '${a}'");
        assertEquals("ugh said 'hello'", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
    
    public void testXMLElementExpansion() {
        tplstore.put("element", "<${this.name}>${this.value}</${this.name}>");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "forename");
        map.put("value", "Frank");
        context.put("this", map );
        assertEquals("<forename>Frank</forename>", TemplaterHelper.expand(templater, finder, "element"));
    }
    
    public void testXMLElementToolExpansion() {
        context.put("this", new FullName("Frank", "Carver"));
        context.put("beantool", new BeanTool());
        tplstore.put("tpl1", "${beantool.forename?*element}");
        tplstore.put("element", "<${name}>${value}</${name}>");
        assertEquals("<forename>Frank</forename>", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
        
    public void testXMLElementExpansionSequence() {
        context.put("frank", new FullName("Frank", "Carver"));
        context.put("field", new BeanTool());
        tplstore.put("tpl1", "${frank*person}");
        tplstore.put("person", "<root>${field.forename?*element}${field.surname?*element}</root>");
        tplstore.put("element", "<${name}>${value}</${name}>");
        assertEquals("<root><forename>Frank</forename><surname>Carver</surname></root>", TemplaterHelper.expand(templater, finder, "tpl1"));
    }
}
