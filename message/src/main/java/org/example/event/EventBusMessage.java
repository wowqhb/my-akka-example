package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Karol
 * @version 1.0
 * @date 2020/12/18 14:08
 */
@Data
@AllArgsConstructor
public class EventBusMessage implements Serializable {
    final Object payload;
}
