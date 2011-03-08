import org.stringtree.juicer.formatter.Transformation;
import org.stringtree.juicer.tract.IncludeTractPipelineFilter;

public class Include extends Transformation {
    
	public void init(String tail) {
		setOther(new IncludeTractPipelineFilter(tail));
		setName("Include (" + tail + ")");
	}
}
