package com.meng.missyou.manager.redis;

import com.meng.missyou.bo.OrderMessageBO;
import com.meng.missyou.service.CouponBackService;
import com.meng.missyou.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TopicMessageListener implements MessageListener {
    @Autowired
    private OrderCancelService orderCancelService;
    @Autowired
    private CouponBackService couponBackService;

//    private static ApplicationEventPublisher publisher;
//    @Autowired
//    public void setPublisher(ApplicationEventPublisher publisher){
//        TopicMessageListener.publisher = publisher;
//    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String expiredKey = new String(body);
        String topic = new String(channel);
        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);
        //此处要发布一个事件，才能搭配使用@EventListener 来订阅、监听、触发
        //TopicMessageListener.publisher.publishEvent(orderMessageBO);
        this.orderCancelService.cancel(orderMessageBO);
        this.couponBackService.returnBack(orderMessageBO);
    }
}
