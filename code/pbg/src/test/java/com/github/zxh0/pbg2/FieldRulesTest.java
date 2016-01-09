package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.field.rules.Optional;
import com.github.zxh0.pbg2.proto.field.rules.Repeated;
import com.github.zxh0.pbg2.proto.field.rules.Required;
import com.github.zxh0.pbg2.proto.field.types.Fixed64;
import com.github.zxh0.pbg2.proto.field.types.Float;
import com.github.zxh0.pbg2.proto.field.types.Int32;
import com.github.zxh0.pbg2.proto.field.types.Sint32;
import org.junit.Test;

import java.io.IOException;

public class FieldRulesTest {

    @Message
    static class FieldRules {
        @Required(tag = 1) Int32 intField;
        @Repeated(tag = 2) Float f2;
        @Optional(tag = 3) Sint32 f3;
        @Optional(tag = 4) Fixed64 f4 = new Fixed64(123);
    }

    @Test
    public void test() throws IOException {
        TestHelper.testOutput(FieldRules.class, "pb2/field_rules_test.proto");
    }

}
