package text.qiao.com.utils.utils;

import com.guangyao.friendschat.model.entity.eventbus.FriendsChatEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2018/2/5.
 * EventBus工具类
 */

public class EventBusUtil {
    //开启Index加速
    public static void openIndex() {
        //如果运行报错“找不到MyEventBusIndex”，那么请先注释掉这句代码再运行项目，运行后会自动生成MyEventBusIndex文件，然后再加入这段代码来开启Index加速。
        //关于Index加速，请查看文章http://www.jianshu.com/p/6fb4d78db19b中的index部分。
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }

    //订阅事件
    public static void register(Object subscriber) {
        if(!EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().register(subscriber);
        }
    }

    //取消订阅
    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    //终止事件继续传递
    public static void cancelDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    //获取保存起来的粘性事件
    public static <T> T getStickyEvent(Class<T> classType){
        return EventBus.getDefault().getStickyEvent(classType);
    }

    //删除保存中的粘性事件
    public static void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    //发送事件
    public static void postEvent(Object event){
        EventBus.getDefault().post(event);
    }

    //发送粘性事件
    public static void postStickyEvent(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    //发送事件
    public static void postFriendsChatEvent(FriendsChatEvent collectionEvent) {
        postEvent(collectionEvent);
    }
}
