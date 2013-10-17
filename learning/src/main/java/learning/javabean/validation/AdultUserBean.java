package learning.javabean.validation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.bval.constraints.Email;

class AdultUserBean implements User {
	@NotEmptyCustomAnnotation
	private String name;

	@NotNull
	@Min(18)
	@Digits(integer = 3, fraction = 0)
	private Integer age;

	@Email
	@NotEmptyCustomAnnotation
	private String email;

	@Pattern(regexp = "\\(\\d{3}\\)\\d{2}-\\d{6}")
	private String phone;

	AdultUserBean(String name, Integer age, String email, String phone) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
}