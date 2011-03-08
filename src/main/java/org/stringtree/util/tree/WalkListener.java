package org.stringtree.util.tree;

public interface WalkListener {
    void start();

    void step(Object object);

    void stop();
}
