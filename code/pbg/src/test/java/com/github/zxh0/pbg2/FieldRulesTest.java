package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.gen.ProtoGen;
import com.github.zxh0.pbg2.msg.Message;
import com.github.zxh0.pbg2.msg.field.rules.Optional;
import com.github.zxh0.pbg2.msg.field.rules.Repeated;
import com.github.zxh0.pbg2.msg.field.rules.Required;
import com.github.zxh0.pbg2.msg.field.types.Fixed64;
import com.github.zxh0.pbg2.msg.field.types.Float;
import com.github.zxh0.pbg2.msg.field.types.Int32;
import com.github.zxh0.pbg2.msg.field.types.Sint32;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
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
        String expected = IOUtils.toString(ClassLoader.getSystemResource("pb2/field_rules.proto"));
        String actual = ProtoGen.gen(FieldRules.class);
        Assert.assertEquals(expected, actual);
    }

}
