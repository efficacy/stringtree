package org.stringtree.json.events;

import java.util.Map;

public interface PrefixMatchHandler {
    void handle(String prefix, Map<String, Object> context);
}
