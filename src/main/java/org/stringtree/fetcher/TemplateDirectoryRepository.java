package org.stringtree.fetcher;

import java.io.File;

import org.stringtree.fetcher.filter.SuffixFilter;

public class TemplateDirectoryRepository extends StringDirectoryRepository {

    public TemplateDirectoryRepository(String dir) {
        super(new File(dir), new SuffixFilter(".tpl"), false);
    }

}
