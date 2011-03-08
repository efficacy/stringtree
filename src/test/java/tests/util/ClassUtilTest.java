package tests.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.util.PackageParameterClassUtils;
import org.stringtree.util.ParameterClassUtils;
import org.stringtree.util.ClassUtils;

public class ClassUtilTest extends TestCase {
    
    CreatedClass obj;
    ClassLoader loader;
    List<String> packages;

    public void setUp() {
        loader = ClassUtilTest.class.getClassLoader();
        packages = new ArrayList<String>();
        packages.add("tests.wibble.");
        packages.add("tests.util");
    }

    public void testSimpleObjectCreation() {
        obj = (CreatedClass) ClassUtils.createObject("tests.util.SimpleClass");
        assertTrue("simple object not null", obj != null);
        assertEquals("simple object contents", "Hello", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) ClassUtils.createObject("tests.util.BothClass");
        assertTrue("both(simple) object not null", obj != null);
        assertEquals("both(simple) object contents", "Hello", obj.contents);
        assertEquals(1, obj.requests);
    }

    public void testParamsObjectCreation() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("greeting", "Hola");

        obj = (CreatedClass) ParameterClassUtils.createObject(
                "tests.util.SimpleClass", params, loader);
        assertTrue("simple object not null", obj != null);
        assertEquals("simple object contents", "Hello", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) ParameterClassUtils.createObject(
                "tests.util.ParamClass", params);
        assertTrue("param object not null", obj != null);
        assertEquals("param object contents", "Hola", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) ParameterClassUtils.createObject(
                "tests.util.BothClass", params);
        assertTrue("both(param) object not null", obj != null);
        assertEquals("both(param) object contents", "Hola", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) ParameterClassUtils.createObject(
                "tests.util.StringParamClass", "ugh");
        assertTrue("param object not null", obj != null);
        assertEquals("param object contents", "ugh", obj.contents);
        assertEquals(1, obj.requests);
    }

    public void testPackagesObjectCreation() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("greeting", "Hola");

        obj = (CreatedClass) PackageParameterClassUtils.createObject(
                "SimpleClass", params, packages);
        assertTrue("simple object not null", obj != null);
        assertEquals("simple object contents", "Hello", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) PackageParameterClassUtils.createObject(
                "tests.util.SimpleClass", params, packages);
        assertTrue("full-package object not null", obj != null);
        assertEquals("full-package object contents", "Hello", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) PackageParameterClassUtils.createObject(
                "tests.util.StringParamClass blue", packages);
        assertTrue("full-package+arg object not null", obj != null);
        assertEquals("full-package+arg object contents", "blue", obj.contents);
        assertEquals(1, obj.requests);

        obj = (CreatedClass) PackageParameterClassUtils.createObject(
                "StringParamClass green", packages);
        assertTrue("simple+arg object not null", obj != null);
        assertEquals("simple+arg object contents", "green", obj.contents);
        assertEquals(1, obj.requests);
    }
}
