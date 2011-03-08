import org.stringtree.juicer.formatter.AugmentFromToTransformation;
import org.stringtree.juicer.tract.RegexAugmentReplaceTractFilter;

public class FilterReplace extends AugmentFromToTransformation {
    
	public void init(String from, String to) {
		setOther(new RegexAugmentReplaceTractFilter(from, to));
		setName("FilterReplace ('" + from + "'->'" + to + "')");
	}
}
