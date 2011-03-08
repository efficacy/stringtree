import org.stringtree.juicer.formatter.Transformation;
import org.stringtree.juicer.tract.JoinTractFilter;

public class Join
	extends Transformation
{
	public void init(String tail)
	{
		setOther(new JoinTractFilter());
		setName("Join");
	}
}
