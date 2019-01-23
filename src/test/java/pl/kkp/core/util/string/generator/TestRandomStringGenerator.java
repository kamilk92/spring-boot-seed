package pl.kkp.core.util.string.generator;

import org.junit.Test;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestRandomStringGenerator {
    @Test
    public void isGenerateRandomPassword() {
        int strLen = 5;
        RandomStringGenerator generator = new RandomStringGenerator(strLen);

        assertGeneratedStrLength(generator, strLen);

        strLen = 21;
        assertGeneratedStrLength(generator, strLen);
    }

    private void assertGeneratedStrLength(RandomStringGenerator generator, int expectedStrLen) {
        generator.setStrLength(expectedStrLen);
        String generatedStr = generator.generate();
        assertThat(generatedStr).isNotEmpty();
        assertThat(generatedStr.length()).isEqualTo(expectedStrLen);
    }
}
