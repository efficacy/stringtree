import org.stringtree.juicer.formatter.FromToTransformation;
import org.stringtree.juicer.tract.RegexSplitReplaceTractFilter;

public class Lock extends FromToTransformation {
    
	public void init(String from, String to) {
		setOther(new RegexSplitReplaceTractFilter(from, to));
		setName("Lock ('" + from + "'->'" + to + "')");
	}
}
