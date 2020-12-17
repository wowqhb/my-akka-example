package org.example.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/17 09:39
 */
public class ChildMessage implements Serializable {
    @Data
    @Accessors(chain = true)
    public static class Request implements Serializable {
        String message;
    }

    @Data
    @Accessors(chain = true)
    public static class Response implements Serializable {
        String requestMessage;
        long receivedTime;
    }
}
