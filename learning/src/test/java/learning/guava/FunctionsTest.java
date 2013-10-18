package learning.guava;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.forMap;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Map;

import learning.guava.Country;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Guava: Functions, Collections2, ImmutableList, ImmutableMap <br>
 * Hamcrest matchers
 * 
 */
public class FunctionsTest {

	Country latvia;
	Country argentina;
	Country jamaica;

	ImmutableList<Country> countries;

	@Before
	public void setUp() {
		latvia = new Country("Latvia", "Riga");
		argentina = new Country("Argentina", "Buenos Aires");
		jamaica = new Country("Jamaica", "Kingston");
		countries = ImmutableList.of(latvia, argentina, jamaica);
	}

	@Test
	public void shouldConvertListUsingComposedFunction() {
		Function<Country, String> composedFunction = createComposedFunctionToGetReversedAndCapitalizedNamesList();

		Collection<String> reversedAndCapitalizedNames = transform(countries, composedFunction);

		assertThat(reversedAndCapitalizedNames, contains("AIVTAL", "ANITNEGRA", "ACIAMAJ"));
	}

	@Test
	public void shouldConvertMapToListUsingForMapFunction() {
		Collection<String> capitals = transform(getCountryNamesList(), capitalsFromCountriesFunction());

		assertThat(capitals, contains("Riga", "Buenos Aires", "Kingston"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhileConvertMapToListUsingForMapFunction() {
		Collection<String> capitals = transform(asList("Latvia", "Uganda"), capitalsFromCountriesFunction());

		assertThat(capitals, contains("Riga"));
	}

	@Test
	public void shouldSetUnknownToUnexistingKeysWhileConvertMapToListUsingForMapFunction() {
		Function<String, String> capitalsFromUnknownCountriesFunction = forMap(getCountriesMap(), "Unknown");

		Collection<String> capitals = transform(asList("Latvia", "Uganda"), capitalsFromUnknownCountriesFunction);

		assertThat(capitals, contains("Riga", "Unknown"));
	}

	private Function<String, String> capitalsFromCountriesFunction() {
		return forMap(getCountriesMap());
	}

	private Function<Country, String> createComposedFunctionToGetReversedAndCapitalizedNamesList() {
		Function<Country, String> uppercaseFunction = new Function<Country, String>() {
			public String apply(Country country) {
				String name = nullToEmpty(country.getName());
				return name.toUpperCase();
			}
		};

		Function<String, String> reverseFunction = new Function<String, String>() {
			public String apply(String input) {
				input = nullToEmpty(input);
				return new StringBuilder(input).reverse().toString();
			}
		};

		return compose(reverseFunction, uppercaseFunction);
	}

	private Collection<String> getCountryNamesList() {
		Function<Country, String> countryNameFunction = new Function<Country, String>() {
			public String apply(Country country) {
				return nullToEmpty(country.getName());
			}
		};

		return transform(countries, countryNameFunction);
	}

	private Map<String, String> getCountriesMap() {
		return ImmutableMap.of(latvia.getName(), latvia.getCapital(), argentina.getName(), argentina.getCapital(), jamaica.getName(), jamaica.getCapital());
	}
}
