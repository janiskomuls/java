package learning.guava;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class SplitterTest {

	List<String> numbersList;
	String joinedNumbers;

	@Before
	public void setUp() {
		numbersList = asList("3", "5", "76", "8");
		joinedNumbers = Joiner.on(", ").join(numbersList);
	}

	@Test
	public void shouldSplitNumbersStringIntoIterable() {
		List<String> splittedNumbers = Splitter.on(", ").splitToList(joinedNumbers);

		assertThat(splittedNumbers, is(numbersList));
	}
	
	@Test
	public void s3houldSplitNumbersStringIntoIterable() {
		List<String> splittedLanguages = Splitter.onPattern("\\d").splitToList("Java6Scala2PHP5");
		
		assertThat(splittedLanguages).isNotEmpty().hasSize(3).contains("Java6", "Scala2", "PHP5");
	}
}
