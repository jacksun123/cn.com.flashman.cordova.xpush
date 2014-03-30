/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.example.hello;

import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.*;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.res.Resources;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.com.flashman.cordova.xpush.Baidu;
import cn.com.flashman.cordova.xpush.Utils;

public class HelloWorld extends CordovaActivity {
  public boolean isActive;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.init();
    // Set by <content src="index.html" /> in config.xml
    super.loadUrl(Config.getStartUrl());
    // super.loadUrl("file:///android_asset/www/index.html");
    // 设置webview引用
    Baidu.setWebview(this);
    //设置icon引用
    Baidu.setIcon(R.drawable.icon);

    // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
    // 这里把apikey存放于manifest文件中，只是一种存放方式，
    // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
    // "api_key")
    // 通过share preference实现的绑定标志开关，如果已经成功绑定，就取消这次绑定
    if (!Utils.hasBind(getApplicationContext())) {
      PushManager.startWork(getApplicationContext(),
          PushConstants.LOGIN_TYPE_API_KEY,
          Utils.getMetaValue(getApplicationContext(), "api_key"));
      // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
      PushManager.enableLbs(getApplicationContext());
    }else{
      Log.d("TAG", "Push userId:" + Utils.getUserId(getApplicationContext()));
    }
/*
    Resources resource = this.getResources();
    String pkgName = this.getPackageName();
    // Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
    // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
    // 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
    CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
        getApplicationContext(), resource.getIdentifier(
            "notification_custom_builder", "layout", pkgName),
        resource.getIdentifier("notification_icon", "id", pkgName),
        resource.getIdentifier("notification_title", "id", pkgName),
        resource.getIdentifier("notification_text", "id", pkgName));
    cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
    cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
        | Notification.DEFAULT_VIBRATE);
    cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
    cBuilder.setLayoutDrawable(resource.getIdentifier(
        "simple_notification_icon", "drawable", pkgName));
    PushManager.setNotificationBuilder(this, 1, cBuilder);
*/
  }

  protected void onResume(){
    super.onResume();
    //置为活跃状态
    Baidu.setActive(true);
    //清除消息通知
    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    nm.cancel(Baidu.nitify_id);
  }
  
  protected void onPause(){
    super.onPause();
    //置为非活跃状态
    Baidu.setActive(false);
  }
}
