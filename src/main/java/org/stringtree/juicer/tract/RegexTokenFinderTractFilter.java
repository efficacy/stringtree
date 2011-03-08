package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.Pattern;
import org.stringtree.tract.MapTract;

import org.stringtree.juicer.JuicerLockHelper;

public class RegexTokenFinderTractFilter extends BasicTractFilter {
    
	protected Pattern compiled;
	protected Matcher matcher;

	protected Tract input;
	protected String current;
	protected int index;
	protected boolean foundToken;
	protected int group;
	protected boolean lock;

	public RegexTokenFinderTractFilter(String from, boolean lock, int group) {
		this.compiled = Pattern.compile(from);
		this.lock = lock;
		this.group = group;
	}

	public RegexTokenFinderTractFilter(String from, boolean lock) {
		this(from, lock, 0);
	}

	public RegexTokenFinderTractFilter(String from, int group) {
		this(from, false, group);
	}

	public RegexTokenFinderTractFilter(String from) {
		this(from, false, 0);
	}

	private void startNextInput() {
		input = source.nextTract();
		if (input == null || JuicerLockHelper.isLocked(input)) {
			current = null;
			matcher = null;
			index = 0;
			foundToken = false;
		} else {
			current = input.getContent();
			matcher = compiled.matcher(current);
			index = 0;
			find();
		}
	}

	protected void find() {
		foundToken = matcher.find();
	}

	public void connectSource(TractSource source) {
		super.connectSource(source);
		startNextInput();
	}
	
	public Tract nextTract() {
		Tract ret = null;

		if (foundToken) {
			ret = new MapTract("", input);
			
			if (index < matcher.start()) {
				ret.setContent(current.substring(index, matcher.start()));
				index = matcher.start();
			} else {
				processToken(ret);
				if (lock) {
					JuicerLockHelper.lock(ret); 
				} 
			}
		} else if (current != null && index < current.length()) {
			ret = new MapTract(current.substring(index, current.length()), input);
			index = current.length();
			startNextInput();
		} else if (current == null && input != null) {
			ret = input;
			startNextInput();
		}

		return ret;
	}

	protected void processToken(Tract ret) {
		ret.setContent(matcher.group(group));
		TokenHelper.setTokenFlag(ret);
		index = matcher.end();
		find();
	}
}
