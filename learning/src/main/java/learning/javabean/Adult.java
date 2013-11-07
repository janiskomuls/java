package learning.javabean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.bval.constraints.Email;

public class Adult implements User {
	@NotEmptyCustomAnnotation
	private String name;

	@NotNull
	@Min(18)
	@Digits(integer = 3, fraction = 0)
	private Integer age;

	@Digits(fraction = 2, integer = 4)
	private BigDecimal salary;

	@Email
	@NotEmptyCustomAnnotation
	private String email;

	@Pattern(regexp = "\\(\\d{3}\\)\\d{2}-\\d{6}")
	private String phone;

	@Valid
	private Child child;

	private Child child2;

	@Valid
	private List<Child> children = new ArrayList<Child>();

	public Adult(String name, Integer age, String email, String phone) {
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

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Child getChild2() {
		return child2;
	}

	public void setChild2(Child child2) {
		this.child2 = child2;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void addChild(Child child) {
		children.add(child);
	}
}