package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Proto;
import org.junit.Test;

public class OptionsTest {

    @Proto
    static class Options {

    }

    @Test
    public void test() {
        TestHelper.testOutput(Options.class, "pb2/options_test.proto");
    }

}
