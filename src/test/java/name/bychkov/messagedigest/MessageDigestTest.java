package name.bychkov.messagedigest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageDigestTest {

	@Test
	public void checkMutualInfluence() throws NoSuchAlgorithmException {
		final String algorithm = "SHA-512";

		MessageDigest md1 = MessageDigest.getInstance(algorithm);
		md1.update("test-1".getBytes(StandardCharsets.UTF_8));

		MessageDigest md2 = MessageDigest.getInstance(algorithm);
		md2.update("test-2".getBytes(StandardCharsets.UTF_8));
		
		byte[] digest1 = md1.digest();
		String digest1Str = bytesToString(digest1);
		Assertions.assertEquals(
				"18926f94712af04ecf84c4f35389bc316963567c6a2ce8332c28427d07595bb01b469363200387fa49aab3a3b441bd2ec562abd3daa2b1d107dafd8af336664e",
				digest1Str);

		byte[] digest2 = md2.digest();
		String digest2Str = bytesToString(digest2);
		Assertions.assertEquals(
				"de1c28a2e610e73472d96c3340640fb6c052a5a2329c6fe7e293d0568318d77e8d37b84216f42683e426c185e33f5ae251f0af6558d3d0a15895a80246f9539d",
				digest2Str);

	}

	private String bytesToString(byte[] checksum) {
		StringBuilder sb = new StringBuilder();
		for (byte b : checksum) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString().toLowerCase();
	}

}
