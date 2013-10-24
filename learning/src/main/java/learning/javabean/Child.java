package learning.javabean;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Child implements User {

	@NotEmptyCustomAnnotation
	private String name;

	@NotNull
	@Min(0)
	@Max(5)
	@Digits(integer = 1, fraction = 0)
	private Integer age;

	public Child(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return "";
	}

	public String getPhone() {
		return "";
	}
}