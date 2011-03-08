package org.stringtree.timing;

public class Tickler implements Clock {
	public static final Tickler STOPPED = new Tickler(new SystemClock(), Long.MAX_VALUE, Long.MAX_VALUE, null);
	
	protected Clock clock;
	protected Ticklish target;
	
	private Thread timerThread;
	private boolean stopping = false;

	public Tickler(final Clock clock, final long delay, final long interval, final Ticklish target) {
		this.clock = clock;
		this.target = target;

		if (Long.MAX_VALUE != delay) createThread(delay, interval);
	}

	private void createThread(final long delay, final long interval) {
		timerThread = new Thread("objectTickler") {

			@Override
			public void run() {
				try {
					if (delay > 0) sleep(delay);

					while (true) {
						try {
							if (null != target) target.tickle(clock.getTime());
						}
						catch (Throwable t) {
							System.err.println("Sadly, tickled object (" + target + ") is not ticklish");
							t.printStackTrace(System.err);
						}

						if (interval > 0) sleep(interval);
					}
				}
				catch (InterruptedException e) {
					if (!stopping) {
						interrupt();
					}
				}
			}
		};

		timerThread.setDaemon(true);
		timerThread.start();
	}

	public Tickler(Clock clock, long interval, Ticklish target) {
		this(clock, 0, interval, target);
	}
	
	public void stop() {
		if (null != timerThread) {
			stopping = true;
			timerThread.interrupt();
		}
	}

	@Override
	public long getTime() {
		return clock.getTime();
	}
}
