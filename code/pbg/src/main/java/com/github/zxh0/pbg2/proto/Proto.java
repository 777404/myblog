package com.github.zxh0.pbg2.proto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={TYPE})
public @interface Proto {

    enum OptimizeForOption {
        SPEED,
        CODE_SIZE,
        LITE_RUNTIME
    }

    // file options
    public String javaPackage() default "";
    public String javaOuterClassname() default "";
    public OptimizeForOption optimizeFor() default OptimizeForOption.SPEED;
    public boolean ccGenericServices() default true;
    public boolean javaGenericServices() default true;
    public boolean pyGenericServices() default true;
    public boolean ccEnableArenas() default false;

}
