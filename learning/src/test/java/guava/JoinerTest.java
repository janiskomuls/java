package guava;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Guava: Joiner, ImmutableMap, ImmutableList
 * 
 */
public class JoinerTest {

	@Test
	public void shouldJoinNumbersIntoOneString() {
		ImmutableList<Integer> numbers = ImmutableList.of(3, 5, 76, 8);

		String joined = Joiner.on(", ").join(numbers);

		assertThat(joined, is("3, 5, 76, 8"));
	}

	@Test
	public void shouldJoinNumbersIntoOneStringSkippingNulls() {
		List<Integer> numbers = asList(3, 5, null, 76, 8);

		String joined = Joiner.on(", ").skipNulls().join(numbers);

		assertThat(joined, is("3, 5, 76, 8"));
	}

	@Test
	public void shouldJoinNumbersIntoOneStringReplacingNulls() {
		List<Integer> numbers = asList(3, 5, null, 76, 8);

		String joined = Joiner.on(", ").useForNull("Nil").join(numbers);

		assertThat(joined, is("3, 5, Nil, 76, 8"));
	}

	@Test
	public void shouldJoinCountriesMapIntoOneString() {
		Map<String, String> countries = ImmutableMap.of("Lv", "Riga", "Uk", "London", "Ee", "Tallin");

		String joined = Joiner.on(" | ").withKeyValueSeparator(" -> ").join(countries);

		assertThat(joined, is("Lv -> Riga | Uk -> London | Ee -> Tallin"));
	}
}
