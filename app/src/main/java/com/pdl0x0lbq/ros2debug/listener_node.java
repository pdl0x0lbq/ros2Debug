package com.pdl0x0lbq.ros2debug;
import android.widget.TextView;

import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.subscription.Subscription;
public class listener_node extends BaseComposableNode{
    private final String topic;
    private final TextView listenerView;
    private Subscription<std_msgs.msg.String> subscriber;
    public listener_node(final String name, final String topic,
                        final TextView listenerView) {
        super(name);
        this.topic = topic;
        this.listenerView = listenerView;
        this.subscriber = this.node.<std_msgs.msg.String>createSubscription(
                std_msgs.msg.String.class, this.topic, msg
                        -> {
                    this.listenerView.setText("Hello ROS2 from Android: " + msg.getData() +
                            "\r\n" + listenerView.getText());

                });
    }
}
