package tests.http;

import org.stringtree.http.HTTPClient;

import junit.framework.TestCase;

public class HTTPClientTest extends TestCase {

    public void testIsRedirectionNullReturnsFalse() {
        assertFalse(HTTPClient.isRedirection(null));
    }

    public void testIsRedirectionEmptyReturnsFalse() {
        assertFalse(HTTPClient.isRedirection(""));
    }

    public void testIsRedirectionNotStartingWith3ReturnsFalse() {
        assertFalse(HTTPClient.isRedirection("200"));
    }

    public void testIsRedirectionStartingWith3ButLongerThan3DigitsReturnsFalse() {
        assertFalse(HTTPClient.isRedirection("3000"));
    }

    public void testIsRedirectionStartingWith3ButNotFolowedByDigitsReturnsFalse() {
        assertFalse(HTTPClient.isRedirection("3bc"));
    }

    public void testIsRedirectionValidReturnsTrue() {
        assertTrue(HTTPClient.isRedirection("300"));
        assertTrue(HTTPClient.isRedirection("301"));
        assertTrue(HTTPClient.isRedirection("302"));
        assertTrue(HTTPClient.isRedirection("303"));
        assertTrue(HTTPClient.isRedirection("304"));
        assertTrue(HTTPClient.isRedirection("305"));
        assertTrue(HTTPClient.isRedirection("306"));
        assertTrue(HTTPClient.isRedirection("307"));
    }
}
