package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.msg.Enum;
import org.junit.Test;

import java.io.IOException;

public class EnumsTest {

    @Enum(allowAlias = true)
    public static class EnumAllowingAlias {
        public static final int UNKNOWN = 0;
        public static final int STARTED = 1;
        public static final int RUNNING = 1;
    }

    @Test
    public void test() throws IOException {
        TestHelper.testOutput(EnumAllowingAlias.class, "pb2/enum_allowing_alias_test.proto");
    }

}
