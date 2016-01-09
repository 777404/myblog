package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.field.rules.Optional;
import com.github.zxh0.pbg2.proto.field.types.*;
import com.github.zxh0.pbg2.proto.field.types.Double;
import com.github.zxh0.pbg2.proto.field.types.Float;
import com.github.zxh0.pbg2.proto.field.types.String;
import org.junit.Test;

import java.io.IOException;

public class DefaultValuesTest {

    @Message
    static class DefaultValues {
        @Optional(tag = 1) Bool f1 = new Bool(true);
        @Optional(tag = 2) Int32 f2 = new Int32(100);
        @Optional(tag = 3) Int64 f3 = new Int64(100);
        @Optional(tag = 4) Uint32 f4 = new Uint32(100);
        @Optional(tag = 5) Uint64 f5 = new Uint64(100);
        @Optional(tag = 6) Sint32 f6 = new Sint32(100);
        @Optional(tag = 7) Sint64 f7 = new Sint64(100);
        @Optional(tag = 8) Fixed32 f8 = new Fixed32(100);
        @Optional(tag = 9) Fixed64 f9 = new Fixed64(100);
        @Optional(tag = 10) Sfixed32 f10 = new Sfixed32(100);
        @Optional(tag = 11) Sfixed64 f11 = new Sfixed64(100);
        @Optional(tag = 12) Float f12 = new Float(3.14f);
        @Optional(tag = 13) Double f13 = new Double(3.14);
        @Optional(tag = 14) String f14 = new String("abc");
    }

    @Test
    public void test() throws IOException {
        TestHelper.testOutput(DefaultValues.class, "pb2/default_values_test.proto");
    }

}
