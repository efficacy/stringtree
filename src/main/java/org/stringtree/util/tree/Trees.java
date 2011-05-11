package org.stringtree.util.tree;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import org.stringtree.util.Utils;

@SuppressWarnings("rawtypes")
public class Trees {
	public static final Tree EMPTY_TREE = new EmptyTree();

    public static Tree getRoot(Tree tree) {
        Tree ret = tree;
        for (Tree up = tree.getParent(); up != null; up = tree.getParent()) {
            ret = up;
        }

        return ret;
    }

    /*
     * note - don't be tempted to add a comparison for "parent", as it recurses
     * straight back down to here again!
     */
    public static boolean equals(Tree t1, Tree t2) {
        return Utils.same(t1.getValue(), t2.getValue())
                && Utils.same(t1.getChildren(), t2.getChildren());
    }

    public static void indent(Writer out, int level) throws java.io.IOException {
        for (int i = 0; i < level; ++i) {
            out.write(' ');
        }
    }

    @SuppressWarnings("unchecked")
	public static void dump(Tree t, Writer out, int level)
            throws java.io.IOException {
        indent(out, level);
        out.write("Tree: '" + t + "' p='" + t.getParent() + "' v='"
                + t.getValue() + "'\n");
        Collection<Tree> kids = t.getChildren();
        if (kids != null) {
            Iterator it = kids.iterator();
            while (it.hasNext()) {
                dump((Tree) it.next(), out, level + 1);
            }
        }
    }

    public static void dump(Tree t, Writer out) throws java.io.IOException {
        dump(t, out, 0);
    }

    public static void dump(Tree t, OutputStream out) {
        try {
            dump(t, new OutputStreamWriter(out));
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
	public static void adopt(MutableTree parent, MutableTree child) {
        child.setParent(parent);
        parent.addChild(child);
    }
}
