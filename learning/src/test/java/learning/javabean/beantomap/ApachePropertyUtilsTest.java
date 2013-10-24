package learning.javabean.beantomap;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;

import learning.javabean.Adult;
import learning.javabean.Child;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

public class ApachePropertyUtilsTest {

	@Test
	public void shouldReturnMapContaingFirstLevelNestedBeans() throws Exception {

		Adult adult = new Adult("John", 19, "john@mail.com", "(371)22-173232");
		Child child = new Child("Anna", 5);
		adult.setChild(child);

		@SuppressWarnings("unchecked")
		Map<String, Object> firstLevelNested = PropertyUtils.describe(adult);

		// @formatter:off
		assertThat(firstLevelNested).hasSize(8)
		.containsKey("name").containsValue("John")
		.containsKey("child").containsValue(child)
		.containsKey("class").containsValue(Adult.class);
		// @formatter:on
	}
}
