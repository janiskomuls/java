package learning.guava;

import static com.google.common.base.Objects.firstNonNull;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Objects.equal, hashCode, toStringHelper, firstNonNull
 * Fest assertions
 * 
 */
public class ObjectsTest {

	@Test
	public void shouldAssertCountriesEquality() {
		assertThat(new Country("Lv", "Riga")).isEqualTo(new Country("Lv", "Riga"));
	}

	@Test
	public void shouldNotAssertCountriesEquality() {
		assertThat(new Country("Ru", "Moscow")).isNotEqualTo(new Country("Lv", "Riga"));
	}

	@Test
	public void shouldAssertCountriesHashCode() {
		assertThat(new Country("Lv", "Riga").hashCode()).isEqualTo(new Country("Lv", "Riga").hashCode());
	}

	@Test
	public void shouldNotAssertCountriesHashCode() {
		assertThat(new Country("Ru", "Moscow").hashCode()).isNotEqualTo(new Country("Lv", "Riga").hashCode());
	}

	@Test
	public void shouldGenerateVerboseToString() {
		assertThat(new Country("Ru", "Moscow").toString()).isEqualTo("Country{code=Ru, Moscow}");
	}

	@Test
	public void shouldReturnFirstNonNull() {
		assertThat(firstNonNull(null, "1st non-null")).isEqualTo("1st non-null");
	}
}
