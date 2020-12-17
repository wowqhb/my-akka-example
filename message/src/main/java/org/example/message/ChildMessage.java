package org.example.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 09:39
 */
public class ChildMessage {
    @Data
    @Accessors(chain = true)
    public static class Request {
        String message;
    }

    @Data
    @Accessors(chain = true)
    public static class Response {
        String requestMessage;
        long receivedTime;
    }
}
