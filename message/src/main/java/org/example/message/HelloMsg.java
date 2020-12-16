package org.example.message;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/15 10:32
 */
public class HelloMsg {
    @Data
    @Accessors(chain = true)
    public static class Request implements MessageJacksonSerializable {
        String message = "Hello world!";
    }

    @Data
    @Accessors(chain = true)
    public static class Response implements MessageJacksonSerializable {
        String message = "I received \"Hello world!\"";
        long time;
        List<String> list;
    }

    @ToString
    public static class CleanListRequest implements MessageJacksonSerializable {
    }

    @ToString
    public static class CleanListResponse implements MessageJacksonSerializable {
        List<String> list;

        public List<String> getList() {
            return list;
        }

        public CleanListResponse setList(List<String> list) {
            this.list = new ArrayList<>();
            this.list.addAll(list);
            list.clear();
            return this;
        }
    }
}
