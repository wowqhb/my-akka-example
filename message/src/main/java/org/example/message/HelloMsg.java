package org.example.message;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/15 10:32
 */
public class HelloMsg implements Serializable {
    @Data
    @Accessors(chain = true)
    public static class Request implements Serializable {
        String message = "Hello world!";
    }

    @Data
    @Accessors(chain = true)
    public static class Response implements Serializable {
        String message = "I received \"Hello world!\"";
        long time;
        List<String> list;
    }

    @ToString
    public static class CleanListRequest implements Serializable {
    }

    @ToString
    public static class CleanListResponse implements Serializable {
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
