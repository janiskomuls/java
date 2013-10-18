package guava;

import static com.google.common.base.CharMatcher.ANY;
import static com.google.common.base.CharMatcher.BREAKING_WHITESPACE;
import static com.google.common.base.CharMatcher.DIGIT;
import static com.google.common.base.CharMatcher.JAVA_LETTER;
import static com.google.common.base.CharMatcher.noneOf;
import static com.google.common.base.CharMatcher.is;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Guava: CharMatcher <br>
 * Fest assertions
 * 
 */
public class CharMatcherTest {

	@Test
	public void shouldNotMatchChar() {
		assertThat(noneOf("xZ").matchesAnyOf("anything")).isTrue();
	}

	@Test
	public void shouldMatchAny() {
		assertThat(ANY.matchesAllOf("anything")).isTrue();
	}

	@Test
	public void shouldMatchBreakingWhitespace() {
		assertThat(BREAKING_WHITESPACE.matchesAllOf("\r\n")).isTrue();
	}

	@Test
	public void shouldMatchDigits() {
		assertThat(DIGIT.matchesAllOf("2134")).isTrue();
	}

	@Test
	public void shouldNotMatchDigits() {
		assertThat(DIGIT.matchesAllOf("fsfsgdr")).isFalse();
	}

	@Test
	public void shouldRetainOnlyDigits() {
		assertThat(DIGIT.retainFrom("hello 23 42")).isEqualTo("2342");
	}

	@Test
	public void shouldRetainDigitsOrWhiteSpaces() {
		assertThat(DIGIT.or(BREAKING_WHITESPACE).retainFrom("hello 23 42")).isEqualTo(" 23 42");
	}

	@Test
	public void shouldTrimSpaces() {
		assertThat(BREAKING_WHITESPACE.trimFrom(" hello 23 42 ")).isEqualTo("hello 23 42");
	}

	@Test
	public void shouldRemoveDigitsOrLetters() {
		assertThat(DIGIT.or(JAVA_LETTER).removeFrom("hello 23 42")).isEqualTo("  ");
	}

	@Test
	public void shouldRetainNothingDueToExcludingConstraints() {
		assertThat(DIGIT.and(JAVA_LETTER).retainFrom("hello 23 42")).isEqualTo("");
	}

	@Test
	public void shouldCollapseAgeByXX() {
		assertThat(DIGIT.collapseFrom("Age: 23", '*')).isEqualTo("Age: *");
	}

	@Test
	public void shouldContainLetters() {
		assertThat(JAVA_LETTER.countIn(" this is some text to count letters")).isEqualTo(28);
	}

	@Test
	public void shouldFindLastIndexOfLetterR() {
		assertThat(is('r').lastIndexIn(" this is some text to count letters")).isEqualTo(33);
	}

	@Test
	public void shouldRemoveDigitsBetween3and6() {
		assertThat(com.google.common.base.CharMatcher.inRange('3', '6').removeFrom("from 0 to 9: 0123456789")).isEqualTo("from 0 to 9: 012789");
	}

	@Test
	public void shouldRemoveAllExceptDigitsBetween3and6() {
		assertThat(com.google.common.base.CharMatcher.inRange('3', '6').negate().removeFrom("from 0 to 9: 0123456789")).isEqualTo("3456");
	}
}
