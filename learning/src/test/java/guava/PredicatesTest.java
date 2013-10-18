package guava;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.containsPattern;
import static com.google.common.base.Predicates.or;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.all;
import static com.google.common.collect.Iterables.filter;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Iterator;

import org.fest.assertions.core.Condition;
import org.junit.Before;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

/**
 * Guava: Predicates, String, Iterables <br>
 * Fest assertions
 * 
 */
public class PredicatesTest {

	Country latvia;
	Country argentina;
	Country jamaica;

	ImmutableList<Country> countries;

	Predicate<Country> latviaPredicate;
	Predicate<Country> kingstonPredicate;

	@Before
	public void setUp() {
		latvia = new Country("Latvia", "Riga");
		argentina = new Country("Argentina", "");
		jamaica = new Country("Jamaica", "Kingston");
		countries = ImmutableList.of(latvia, argentina, jamaica);

		latviaPredicate = new Predicate<Country>() {
			public boolean apply(Country country) {
				return latvia.getName().equals(country.getName());
			}
		};

		kingstonPredicate = new Predicate<Country>() {
			public boolean apply(Country country) {
				return jamaica.getCapital().equals(country.getName());
			}
		};
	}

	public void shouldUseCustomPredicate() {
		// given
		Predicate<Country> capitalCityProvidedPredicate = new Predicate<Country>() {
			public boolean apply(Country country) {
				return !isNullOrEmpty(country.getCapital());
			}
		};

		// when
		boolean allCountriesSpecifyCapitalCity = all(countries, capitalCityProvidedPredicate);

		// then
		assertThat(allCountriesSpecifyCapitalCity).isFalse();
	}

	public void shouldNotFilterCountriesUsingComposedByAndPredicate() {
		// given
		Condition<Iterable<Country>> anyCountry = new Condition<Iterable<Country>>() {
			public boolean matches(Iterable<Country> country) {
				return country.iterator().hasNext();
			}
		};

		Predicate<Country> composedPredicate = and(latviaPredicate, kingstonPredicate);

		// when
		Iterable<Country> countriesWithIncorrectCapital = filter(countries, composedPredicate);

		// then
		assertThat(countriesWithIncorrectCapital).doesNotHave(anyCountry);
	}

	public void shouldFilterCountriesUsingComposedByOrPredicate() {
		// given
		Predicate<Country> composedPredicate = or(latviaPredicate, kingstonPredicate);

		// when
		Iterable<Country> fileteredCountries = filter(countries, composedPredicate);

		// then
		Iterator<Country> iterator = fileteredCountries.iterator();
		assertThat(iterator.next()).isEqualTo(latvia);
		assertThat(iterator.next()).isEqualTo(jamaica);
	}

	public void shouldCheckPattern() {
		Predicate<CharSequence> twoDigitsPredicate = containsPattern("\\d\\d*!$");

		assertThat(twoDigitsPredicate.apply("I am 18 years old!")).isTrue();
	}
}
