package name.bychkov.messagedigest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 9)
public class MessageDigestBenchmark
{
	private static final String ALGORITHM = "SHA-512";
	private static final int STRING_LENGTH = 128;
	
	public static void main(String[] args) throws IOException
	{
		org.openjdk.jmh.Main.main(args);
	}
	
	@State(Scope.Thread)
	public static class EmptyMessageDigestState
	{
		String randomString;
		MessageDigest md;
		
		@Setup(Level.Iteration)
		public void initIteration()
		{
			randomString = RandomStringUtils.random(STRING_LENGTH);
		}
	}

	@State(Scope.Thread)
	public static class NonEmptyMessageDigestState
	{
		String randomString;
		MessageDigest md;
		
		@Setup(Level.Iteration)
		public void initIteration() throws NoSuchAlgorithmException
		{
			md = MessageDigest.getInstance(ALGORITHM);
			randomString = RandomStringUtils.random(STRING_LENGTH);
		}
	}

	@Benchmark
	public void create(EmptyMessageDigestState state) throws NoSuchAlgorithmException
	{
		state.md = MessageDigest.getInstance(ALGORITHM);
	}
	
	@Benchmark
	public void createUpdateDigest(EmptyMessageDigestState state) throws NoSuchAlgorithmException
	{
		state.md = MessageDigest.getInstance(ALGORITHM);
		state.md.update(state.randomString.getBytes());
		state.md.digest();
	}
	
	@Benchmark
	public void updateDigest(NonEmptyMessageDigestState state)
	{
		state.md.update(state.randomString.getBytes());
		state.md.digest();
	}
	
	@Benchmark
	public void resetUpdateDigest(NonEmptyMessageDigestState state)
	{
		state.md.reset();
		state.md.update(state.randomString.getBytes());
		state.md.digest();
	}
	
	@Benchmark
	public void createResetUpdateDigest(EmptyMessageDigestState state) throws NoSuchAlgorithmException
	{
		state.md = MessageDigest.getInstance(ALGORITHM);
		state.md.reset();
		state.md.update(state.randomString.getBytes());
		state.md.digest();
	}
}
