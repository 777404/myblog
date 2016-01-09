package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Enum;
import org.junit.Test;

import java.io.IOException;

public class EnumsTest {

    @Enum
    static class Corpus {
        static final int UNIVERSAL = 0;
        static final int WEB = 1;
        static final int IMAGES = 2;
        static final int LOCAL = 3;
        static final int NEWS = 4;
        static final int PRODUCTS = 5;
        static final int VIDEO = 6;
    }

    @Test
    public void test() throws IOException {
        TestHelper.testOutput(Corpus.class, "pb2/enums_test.proto");
    }

}
