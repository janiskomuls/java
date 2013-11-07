package learning.javabean.beantomap;

import static org.fest.assertions.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;

import learning.javabean.Adult;
import learning.javabean.Child;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("unchecked")
public class ApachePropertyUtilsTest {

	@Test
	public void shouldReturnMapContaingFirstLevelNestedBeans() throws Exception {

		Adult adult = new Adult("John", 19, "john@mail.com", "(371)22-173232");
		BigDecimal salary = new BigDecimal("123.45");
		adult.setSalary(salary);
		Child child = new Child("Anna", 5);
		adult.setChild(child);

		Map<String, Object> firstLevelNested = PropertyUtils.describe(adult);

		// @formatter:off
		assertThat(firstLevelNested).hasSize(9)
		.containsKey("name").containsValue("John")
		.containsKey("child").containsValue(child)
		.containsKey("class").containsValue(Adult.class);
		// @formatter:on

		assertThat(firstLevelNested.get("salary")).isEqualTo(salary);
	}

	@Test
	public void shouldReturnMapContaingEmptyInformationWhenDescribesMapInsteadOfBean() throws Exception {

		Adult adult = new Adult("John", 19, "john@mail.com", "(371)22-173232");
		Child child = new Child("Anna", 5);
		adult.setChild(child);

		Map<String, Object> secondLevelNested = PropertyUtils.describe(ImmutableMap.of("name", "Koljan", "age", 12, "child", child));

		// @formatter:off
		assertThat(secondLevelNested).hasSize(2)
		.containsKey("empty").containsValue(null)
		.containsKey("class").containsValue(null);
		// @formatter:on
	}
}
