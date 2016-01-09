package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.gen.ProtoGen;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.IOException;

public class TestHelper {

    public static void testOutput(Class<?> msg, String proto) {
        try {
            String actual = ProtoGen.gen(msg);
            String expected = IOUtils.toString(ClassLoader.getSystemResource(proto));
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
