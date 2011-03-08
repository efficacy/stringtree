package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.juicer.formatter.ExternalFormatter;
import org.stringtree.util.FileReadingUtils;

public class FileExternalWikiFilterTest extends WikiFilterTest {
    
	private String filename = "testfiles/wiki.transform";

	protected StringFinder createFormatter(Fetcher context) {
		type = "FileExternalWikiFilter";
		return new ExternalFormatter(FileReadingUtils.readFile(filename), context);
	}
}

