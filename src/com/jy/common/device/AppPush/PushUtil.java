package com.jy.common.device.AppPush;


import org.json.JSONObject;

import com.tencent.xinge.XingeApp;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;

/**
 * 信鸽苹果安桌推送
 * @author lamaq
 *
 */

public class PushUtil {
	
	
	public void SendPush( String message ,String service ){
		
	}
	
	//小米推送
	
	public void MisendMessage(  ) throws Exception {
  	     Constants.useOfficial();
  	     Sender sender = new Sender(PushConfig.mi_Android_AppSecret);
  	     String messagePayload= "This is a message";
  	     String title = "notification title";
  	     String description = "notification description";
  	     Message message = new Message.Builder()
  	                .title(title)
  	                .description(description).payload(messagePayload)
  	                .restrictedPackageName(PushConfig.MY_PACKAGE_NAME)
  	                .notifyType(1)     // 使用默认提示音提示
  	                .build();
  	     sender.send(message, "SePsQP1YLKxsM1Fwg5ZGyDJ1xYaV3zfqNEdjAJiDYQMnwwLj5eSY8ngudoo2k0EQ", 0); //根据regID，发送消息到指定设备上，不重试。
   	}
	
	//信鸽推送
	
	public JSONObject PushTokenAndroid(String title, String content, String token ){
		return XingeApp.pushTokenAndroid(PushConfig.xg_Android_ACCESS_ID, PushConfig.xg_Android_SECRET_KEY, title, content, token);
	}
//	public JSONObject PushAllAndroid(String title, String content ){
//		return XingeApp.pushAllAndroid(PushConfig.Android_ACCESS_ID, PushConfig.Android_SECRET_KEY, title, content);
//	}
	public JSONObject PushTokenIos( String content, String token ){
		return XingeApp.pushTokenIos(PushConfig.IOS_ACCESS_ID, PushConfig.IOS_SECRET_KEY,  content, token,XingeApp.IOSENV_DEV);
	}
//	public JSONObject PushAllIos( String content ){
//		return XingeApp.pushAllIos(PushConfig.IOS_ACCESS_ID, PushConfig.IOS_SECRET_KEY,  content,XingeApp.IOSENV_DEV);
//	}
	
}
