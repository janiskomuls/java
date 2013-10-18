package learning.guava;

import org.junit.Test;

/**
 * Preconditions
 * 
 */
public class PreconditionsTest {

	@Test
	public void shouldNotThrowExceptionOnCorrectParameters() {
		Country country = new Country("Lv", "Riga");
		country.setIsoCode("LV");
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerExceptionOnCheckNotNullFail() {
		new Country("Lv", null);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIlleagalStateExceptionOnCheckStateFail() {
		Country country = new Country("Lv", "Riga");
		country.setIsoCode("LVL");
	}
}
