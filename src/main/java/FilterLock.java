import org.stringtree.juicer.formatter.AugmentFromToTransformation;
import org.stringtree.juicer.tract.RegexSplitReplaceTractFilter;

public class FilterLock extends AugmentFromToTransformation {
    
	public void init(String from, String to) {
		setOther(new RegexSplitReplaceTractFilter(from, to));
		setName("FilterLock ('" + from + "'->'" + to + "')");
	}
}
