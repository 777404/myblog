package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.field.rules.Required;
import com.github.zxh0.pbg2.proto.field.types.*;
import com.github.zxh0.pbg2.proto.field.types.Double;
import com.github.zxh0.pbg2.proto.field.types.Float;
import com.github.zxh0.pbg2.proto.field.types.String;
import org.junit.Test;

import java.io.IOException;

public class FieldTypesTest {

    @Message
    static class FieldTypes {
        @Required(tag = 1) Bool f1;
        @Required(tag = 2) Int32 f2;
        @Required(tag = 3) Int64 f3;
        @Required(tag = 4) Uint32 f4;
        @Required(tag = 5) Uint64 f5;
        @Required(tag = 6) Sint32 f6;
        @Required(tag = 7) Sint64 f7;
        @Required(tag = 8) Fixed32 f8;
        @Required(tag = 9) Fixed64 f9;
        @Required(tag = 10) Sfixed32 f10;
        @Required(tag = 11) Sfixed64 f11;
        @Required(tag = 12) Float f12;
        @Required(tag = 13) Double f13;
        @Required(tag = 14) String f14;
        @Required(tag = 15) Bytes f15;
    }

    @Test
    public void test() throws IOException {
        TestHelper.testOutput(FieldTypes.class, "pb2/field_types_test.proto");
    }

}
