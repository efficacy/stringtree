import org.stringtree.juicer.formatter.FromToTransformation;
import org.stringtree.juicer.tract.RegexReplaceTractFilter;

public class ForceReplace extends FromToTransformation {
    
	public void init(String from, String to) {
		setOther(new RegexReplaceTractFilter(from, to, false));
		setName("Impolite Replacement ('" + from + "'->'" + to + "')");
	}
}
