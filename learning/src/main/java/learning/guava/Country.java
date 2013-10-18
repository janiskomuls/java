package learning.guava;

import static com.google.common.base.Preconditions.checkNotNull;

public class Country {

	private String name;

	private String capital;

	Country(String name, String capital) {
		checkNotNull(name);
		checkNotNull(capital);
		this.name = name;
		this.capital = capital;
	}

	public String getName() {
		return name;
	}

	public String getCapital() {
		return capital;
	}
}
