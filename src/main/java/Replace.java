import org.stringtree.juicer.formatter.FromToTransformation;
import org.stringtree.juicer.tract.RegexReplaceTractFilter;

public class Replace 
	extends FromToTransformation
{
	public void init(String from, String to)
	{
		setOther(new RegexReplaceTractFilter(from, to));
		setName("Replacement ('" + from + "'->'" + to + "')");
	}
}
