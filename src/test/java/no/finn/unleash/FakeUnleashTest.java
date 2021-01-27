package no.finn.unleash;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeUnleashTest {

    @Test
    public void should_enable_all_toggles() throws Exception {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enableAll();

        assertThat(fakeUnleash.isEnabled("unknown")).isTrue();
        assertThat(fakeUnleash.isEnabled("unknown2")).isTrue();
    }

    @Test
    public void should_enable_specific_toggles() throws Exception {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable("t1", "t2");

        assertThat(fakeUnleash.isEnabled("t1")).isTrue();
        assertThat(fakeUnleash.isEnabled("t2")).isTrue();
        assertThat(fakeUnleash.isEnabled("unknown")).isFalse();
    }

    @Test
    public void should_disable_all_toggles() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable("t1", "t2");
        fakeUnleash.disableAll();

        assertThat(fakeUnleash.isEnabled("t1")).isFalse();
    }

    @Test
    public void should_disable_one_toggle() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable("t1", "t2");
        fakeUnleash.disable("t2");

        assertThat(fakeUnleash.isEnabled("t1")).isTrue();
        assertThat(fakeUnleash.isEnabled("t2")).isFalse();
    }

    @Test
    public void should_be_disabled_even_when_true_is_default() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.disable("t1");

        assertThat(fakeUnleash.isEnabled("t1", true)).isFalse();
    }

    @Test
    public void should_be_disabled_on_disable_all_with_true_as_default() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.disableAll();

        assertThat(fakeUnleash.isEnabled("t1", true)).isFalse();
    }

    @Test
    public void should_be_able_to_reset_all_disables() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.disable("t1");
        fakeUnleash.resetAll();
        assertThat(fakeUnleash.isEnabled("t1", true)).isTrue();

    }

    @Test
    public void should_be_able_to_reset_single_disable() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.disable("t1");
        fakeUnleash.disable("t2");
        fakeUnleash.reset("t1");

        assertThat(fakeUnleash.isEnabled("t1", true)).isTrue();
        assertThat(fakeUnleash.isEnabled("t2", true)).isFalse();

    }

    @Test
    public void should_get_all_feature_names() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable("t1", "t2");
        fakeUnleash.disable("t2");

        List<String> expected = Arrays.asList(new String[]{"t1", "t2"});
        assertThat(fakeUnleash.getFeatureToggleNames()).containsAll(expected);
    }

    @Test
    public void should_get_variant() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable("t1", "t2");
        fakeUnleash.setVariant("t1", new Variant("a", (String) null, true));

        assertThat(fakeUnleash.getVariant("t1").getName()).isEqualTo("a");
    }

    @Test
    public void should_get_disabled_variant_when_toggle_is_disabled() {
        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.disable("t1", "t2");
        fakeUnleash.setVariant("t1", new Variant("a", (String) null, true));

        assertThat(fakeUnleash.getVariant("t1").getName()).isEqualTo("disabled");
    }

}
