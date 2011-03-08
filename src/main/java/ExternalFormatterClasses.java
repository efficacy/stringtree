import org.stringtree.juicer.formatter.ExternalFormatter;
import org.stringtree.util.FileReadingUtils;
import org.stringtree.util.ResourceUtils;

public class ExternalFormatterClasses {
    public org.stringtree.juicer.formatter.ExternalFormatter c1;
    public org.stringtree.util.FileReadingUtils c2;
    public org.stringtree.util.ResourceUtils c3;
		
    public FilterLock c4;
    public FilterReplace c5;
    public ForceReplace c6;
    public Join c7;
    public Lock c8;
    public Replace c9;
    public Include c10;
	
	private ExternalFormatterClasses() {
	    c1 = new ExternalFormatter((String)null, null);
	    c2 = new FileReadingUtils();
	    c3 = new ResourceUtils();
	    c4 = new FilterLock();
	    c5 = new FilterReplace();
	    c6 = new ForceReplace();
	    c7 = new Join();
	    c8 = new Lock();
	    c9 = new Replace();
	    c10 = new Include();
	}
}
