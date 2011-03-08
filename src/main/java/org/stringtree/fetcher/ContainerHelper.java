package org.stringtree.fetcher;

import org.stringtree.Container;
import org.stringtree.Fetcher;

public class ContainerHelper {

    public static boolean contains(Fetcher fetcher, String name) {
        if (fetcher instanceof Container) {
            return ((Container) fetcher).contains(name);
        }

        Object obj = fetcher.getObject(Container.CONTAINER);
        if (obj instanceof Container) {
            return ((Container) obj).contains(name);
        }

        return false;
    }
}
