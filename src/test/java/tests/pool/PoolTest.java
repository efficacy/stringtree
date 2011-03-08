package tests.pool;

import junit.framework.TestCase;

import org.stringtree.pool.ObjectFactory;
import org.stringtree.pool.ObjectPool;
import org.stringtree.pool.Pool;
import org.stringtree.pool.Verifiable;

class DV implements Verifiable {

	public final int id;
	public boolean valid;
	public boolean disposed;
	
	public DV(int id, boolean valid) {
		this.id = id;
		this.valid = valid;
		this.disposed = false;
	}
	
	@Override public boolean valid() {
		return valid;
	}
}

class DVFactory implements ObjectFactory<DV> {
	Pool<DV> pool;
	int n = 1;
	
	@Override public DV create() {
		return new DV(n++, true);
	}

	@Override
	public void dispose(DV t) {
		t.disposed = true;
		t.valid = false;
	}

	@Override
	public void setPool(Pool<DV> pool) {
		this.pool = pool;
	}
}

public class PoolTest extends TestCase {
	ObjectPool<DV> pool;
	DV obj;
	
	public void setUp() {
		pool = new ObjectPool<DV>(new DVFactory());
	}
	
	public void testClaim() {
		obj = pool.claim();
		assertEquals(1, obj.id);
	}
	
	public void testTwoClaims() {
		obj = pool.claim();
		assertEquals(1, obj.id);

		obj = pool.claim();
		assertEquals(2, obj.id);
	}
	
	public void testClaimReleaseClaim() {
		obj = pool.claim();
		assertEquals(1, obj.id);
		
		pool.release(obj);

		obj = pool.claim();
		assertEquals(1, obj.id);
	}
	
	public void testClaimInvalidReleaseClaim() {
		obj = pool.claim();
		assertEquals(1, obj.id);
		
		obj.valid = false;
		pool.release(obj);

		obj = pool.claim();
		assertEquals(2, obj.id);
	}
	
	public void testClaimReleaseInvalidClaim() {
		obj = pool.claim();
		assertEquals(1, obj.id);
		
		pool.release(obj);
		obj.valid = false;

		obj = pool.claim();
		assertEquals(2, obj.id);
	}
}
