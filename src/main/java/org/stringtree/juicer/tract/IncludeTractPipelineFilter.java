package org.stringtree.juicer.tract;

import org.stringtree.util.FileReadingUtils;

public class IncludeTractPipelineFilter extends ExternalTractPipeline {
	public IncludeTractPipelineFilter(String filename) {
		super(FileReadingUtils.readFile(filename));
	}
}
