package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.OptimizeForOption;
import com.github.zxh0.pbg2.proto.Proto;
import com.github.zxh0.pbg2.proto.field.rules.Repeated;
import com.github.zxh0.pbg2.proto.field.types.Int32;
import org.junit.Test;

public class OptionsTest {

    @Proto(
        javaPackage = "com.example.foo",
        javaOuterClassname = "Ponycopter",
        optimizeFor = OptimizeForOption.CODE_SIZE
    )
    static class OptionsProto {
        @Message
        static class Options {
            @Repeated(tag = 4, packed = true) Int32 samples;
        }
    }

    @Test
    public void test() {
        TestHelper.testOutput(OptionsProto.class, "pb2/options_test.proto");
    }

}
