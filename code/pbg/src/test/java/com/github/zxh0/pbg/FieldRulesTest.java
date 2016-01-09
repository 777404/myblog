package com.github.zxh0.pbg;

import com.github.zxh0.pbg.gen.ProtoGen;
import com.github.zxh0.pbg.msg.Message;
import com.github.zxh0.pbg.msg.field.rules.Optional;
import com.github.zxh0.pbg.msg.field.rules.Repeated;
import com.github.zxh0.pbg.msg.field.rules.Required;
import com.github.zxh0.pbg.msg.field.types.Fixed64;
import com.github.zxh0.pbg.msg.field.types.Float;
import com.github.zxh0.pbg.msg.field.types.Int32;
import com.github.zxh0.pbg.msg.field.types.Sint32;

public class FieldRulesTest {

    @Message
    static class Msg {
        @Required(tag = 1) Int32 intField;
        @Repeated(tag = 2) Float f2;
        @Optional(tag = 3) Sint32 f3;
        @Optional(tag = 4) Fixed64 f4 = new Fixed64(123);
    }

    public static void main(String[] args) {
        String x = ProtoGen.gen(Msg.class);
        System.out.println(x);
    }

}
