package learning.javabean.validation;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import learning.javabean.Adult;
import learning.javabean.Child;
import learning.javabean.User;

import org.apache.bval.jsr303.ApacheValidationProvider;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * 
 * Junit, Hamcrest-all, javax.validation, jsr303.ApacheValidationProvider
 * 
 */
public class ApacheBeanValidationProviderTest {

	private static final int INCORRECT_CHILD_AGE = 6;

	private static final int CORRECT_AGE_19 = 19;

	private static final int CORRECT_AGE_55 = 55;

	private static final int INCORRECT_AGE_12 = 12;

	private static final int INCORRECT_AGE_4231 = 4231;

	private static final String CORRECT_NAME = "Johan";

	private static final String CORRECT_EMAIL = "johan@gmail.com";

	private static final String CORRECT_PHONE = "(371)22-163563";

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void shouldFailWithTooSmallAge() {
		User adultUser = new Adult(CORRECT_NAME, INCORRECT_AGE_12, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("age"));
		assertThat(violation.getMessage(), is("must be greater than or equal to 18"));
	}

	@Test
	public void shouldValidateNestedBean() {
		Adult adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		adultUser.setChild(new Child(CORRECT_NAME, INCORRECT_CHILD_AGE));

		Set<ConstraintViolation<Adult>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<Adult> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("child.age"));
		assertThat(violation.getMessage(), is("must be less than or equal to 5"));
	}

	@Test
	public void shouldNotValidateNestedBean() {
		Adult adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		adultUser.setChild2(new Child(CORRECT_NAME, INCORRECT_CHILD_AGE));

		Set<ConstraintViolation<Adult>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(0));
	}

	@Test
	public void shouldValidateCollectionContent() {
		Adult adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		adultUser.addChild(new Child(CORRECT_NAME, INCORRECT_CHILD_AGE));

		Set<ConstraintViolation<Adult>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<Adult> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("children[0].age"));
		assertThat(violation.getMessage(), is("must be less than or equal to 5"));
	}

	@Test
	public void shouldFailWithTooBigAge() {
		User adultUser = new Adult(CORRECT_NAME, INCORRECT_AGE_4231, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("age"));
		assertThat(violation.getMessage(), is("numeric value out of bounds (<3 digits>.<0 digits> expected)"));
	}

	@Test
	public void shouldFailWithNullAge() {
		User adultUser = new Adult(CORRECT_NAME, null, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("age"));
		assertThat(violation.getMessage(), is("may not be null"));
	}

	@Test
	public void shouldFailWithIncorrectEmail() {
		User adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_55, "johan#gmail.com", CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("email"));
		assertThat(violation.getMessage(), is("not a well-formed email address"));
	}

	@Test
	public void shouldFailWithEmptyName() {
		User adultUser = new Adult("", CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("name"));
		assertThat(violation.getMessage(), is("could not be empty"));
	}

	@Test
	public void shouldFailWithNullName() {
		User adultUser = new Adult(null, CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("name"));
		assertThat(violation.getMessage(), is("could not be empty"));
	}

	@Test
	public void shouldFailWithIncorrectPhone() {
		User adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, "371-22-156666");

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(1));

		ConstraintViolation<User> violation = violations.iterator().next();

		assertThat(violation.getPropertyPath().toString(), is("phone"));
		assertThat(violation.getMessage(), is("must match the following regular expression: \\(\\d{3}\\)\\d{2}-\\d{6}"));
	}

	@Test
	public void shouldValidateNonBeans() {
		Set<ConstraintViolation<Object>> violations = validator.validate((Object) "");

		assertThat(violations, hasSize(0));

		violations = validator.validate((Object) 12);

		assertThat(violations, hasSize(0));
	}

	@Test
	public void shouldNull() {
		User adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, "371-22-156666");
		Set<ConstraintViolation<ImmutableMap<Integer, User>>> violations = validator.validate(ImmutableMap.of(1, adultUser, 2, adultUser));
		assertThat(violations, hasSize(0));
	}

	@Test
	public void shouldPassValidation() {
		User adultUser = new Adult(CORRECT_NAME, CORRECT_AGE_19, CORRECT_EMAIL, CORRECT_PHONE);

		Set<ConstraintViolation<User>> violations = validator.validate(adultUser);

		assertThat(violations, hasSize(0));
	}
}
