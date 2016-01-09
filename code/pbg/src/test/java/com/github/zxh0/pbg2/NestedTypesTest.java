package com.github.zxh0.pbg2;

import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.field.rules.Optional;
import com.github.zxh0.pbg2.proto.field.rules.Repeated;
import com.github.zxh0.pbg2.proto.field.rules.Required;
import com.github.zxh0.pbg2.proto.field.types.String;
import org.junit.Test;

public class NestedTypesTest {

    @Message
    static class SearchResponse {
        static class Result {
            @Required(tag = 1) String url;
            @Optional(tag = 2) String title;
            @Repeated(tag = 3) String snippets;
        }
        @Repeated(tag = 1) Result result;
    }
    @Message
    static class SomeOtherMessage {
        @Optional(tag = 1) SearchResponse.Result result;
    }

    @Test
    public void test() {
        TestHelper.testOutput(SearchResponse.class, "pb2/nested_types_test.proto");
    }

}
