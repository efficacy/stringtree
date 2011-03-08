package org.stringtree.util.logging;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.util.Utils;

class Category {
    Category parent;

    Boolean logging;

    Category(Category parent) {
        this.parent = parent;
        this.logging = null;
    }
}

public class CategoryLogger extends DelegatedLogger {
    public static final String ALL = "ALL";
    public static final String nl = System.getProperty("line.separator");

    private static String spaces = "                                                            ";
    private Map<String, Category> categories;
    private Category root;

    public CategoryLogger(Logger logger) {
        super(logger);

        categories = new HashMap<String, Category>();
        root = new Category(null);

        root.logging = Boolean.TRUE;
        categories.put("ALL", root);
    }

    public CategoryLogger(Writer out) {
        this(new WriterLogger(out));
    }

    public CategoryLogger(OutputStream out) {
        this(new StreamLogger(out));
    }

    public CategoryLogger() {
        this(System.out);
    }

    private Category add(String name, Category parent) {
        Category ret = new Category(parent);
        categories.put(name, ret);
        return ret;
    }

    public void addCategory(String name, String parentName) {
        Category parent = categories.get(parentName);
        if (parent == null) {
            parent = root;
        }

        add(name, parent);
    }

    public void addCategory(String name) {
        add(name, root);
    }

    public boolean isLoggable(String name) {
        Category category = categories.get(name);
        if (category == null) {
            category = add(name, root);
        }

        while (category.logging == null) {
            category = category.parent;
        }

        return category.logging.booleanValue();
    }

    public void allowLogging(String name) {
        Category category = categories.get(name);
        if (category == null) {
            category = add(name, root);
        }

        category.logging = Boolean.TRUE;
    }

    public void preventLogging(String name) {
        Category category = categories.get(name);
        if (category == null) {
            category = add(name, root);
        }

        category.logging = Boolean.FALSE;
    }

    public static String indent(int level) {
        return spaces.substring(spaces.length() - level);
    }

    public void log(String category, String message, int level) {
        if (isLoggable(category)) {
            logPart("(");
            logPart(category);
            logPart(") ");
            if (level > 0)
                logPart(indent(level));
            log(message);
        }
    }

    public void log(String category, String message) {
        log(category, message, 0);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof CategoryLogger)) return false;
        
        CategoryLogger other = (CategoryLogger)obj;
        return super.equals(obj) && Utils.same(categories, other.categories)
            && Utils.same(root, other.root);
    }
    
    @Override
    public int hashCode() {
        return "$CategoryLogger$".hashCode() + super.hashCode(); 
    }
}