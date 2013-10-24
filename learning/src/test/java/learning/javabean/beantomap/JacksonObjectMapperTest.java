package learning.javabean.beantomap;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import learning.javabean.AdultUser;
import learning.javabean.Child;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class JacksonObjectMapperTest {

	private static final String SALARY = "213334.924356";

	@Test
	public void shouldConverBeanToMap() {

		AdultUser adultUser = new AdultUser("John", 19, "john@mail.com", "(371)22-173232");
		adultUser.setSalary(new BigDecimal(SALARY));
		Child child = new Child("Anna", 5);
		adultUser.setChild(child);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> adultUserMap = mapper.convertValue(adultUser, Map.class);

		assertThat(adultUserMap.get("name")).isEqualTo("John");
		assertThat(adultUserMap.get("salary")).isEqualTo(new Double(SALARY));

		Map<String, Object> childMap = (Map<String, Object>) adultUserMap.get("child");
		assertThat(childMap.get("name")).isEqualTo("Anna");
	}
}
