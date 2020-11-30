package top.maplefix.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Maple
 * @description : websocket配置
 *  配置WebSocket消息代理端点，即stomp服务端
 *  为了连接安全，setAllowedOrigins设置的允许连接的源地址
 *  如果在非这个配置的地址下发起连接会报403
 *  进一步还可以使用addInterceptors设置拦截器，来做相关的鉴权操作
 *
 *  extends AbstractWebSocketMessageBrokerConfigurer已过时,使用实现WebSocketMessageBrokerConfigurer接口
 * @date : 2020/1/18 16:35
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker // 注解开启STOMP协议来传输基于代理的消息，此时控制器支持使用
@MessageMapping
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * 注册STOMP协议的节点(endpoint),并映射的指定的URL
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 将clientMessage注册为STOMP的一个端点
        // 客户端在订阅或发布消息到目的路径前，要连接该端点
        // setAllowedOrigins允许所有域连接，否则浏览器可能报403错误
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")//表示允许连接的域名
                .withSockJS();//表示支持以SockJS方式连接服务器
    }

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                while (true) {
                    try {
                        LoggerMessage loggerMessage = LoggerQueue.getInstance().poll();
                        log.info("推送日志内容为：" + loggerMessage);
                        if (loggerMessage != null){
                            if (messagingTemplate != null)
                                messagingTemplate.convertAndSend("/topic/pullLogger", loggerMessage);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
    }

}
