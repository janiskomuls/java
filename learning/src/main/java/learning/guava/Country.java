package learning.guava;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Objects;

public class Country {

	private String name;

	private String capital;

	private String isoCode;

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

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		checkState(isoCode.length() == 2, "Iso code leght should be 2");
		this.isoCode = isoCode;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Country))
			return false;
		Country otherCountry = (Country) other;
		return equal(this.name, otherCountry.name) && equal(this.capital, otherCountry.capital);
	}

	public int hashCode() {
		return Objects.hashCode(name, capital);
	}

	public String toString() {
		return toStringHelper(this).add("code", name).addValue(capital).toString();
	}
}