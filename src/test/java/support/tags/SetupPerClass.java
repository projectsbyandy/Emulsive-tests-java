package support.tags;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag("SetupPerClass")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public @interface SetupPerClass {
}
