# my-akka-example
akka example, send message(tell &amp; ask), cluster



```xml
<!-- 本地actor发送消息tell方式和ask方式 -->
<module>tell-and-ask</module>
<!-- 消息定义 -->
<module>message</module>
<!-- 简单的集群创建代码 -->
<module>simple-cluster-example</module>
<!-- 创建集群服务器端，用于接收消息并发送响应消息 -->
<module>cluster-send-message-server</module>
<!-- 创建集群客户端，发送消息到集群服务器端并接收响应 -->
<module>cluster-send-message-client</module>
<!-- 单机订阅发布测试 -->
<module>simple-event-bus-example</module>
<!-- 集群订阅发布测试 -->
<module>cluster-event-bus-example</module>
```

