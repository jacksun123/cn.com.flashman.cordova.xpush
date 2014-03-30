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
package cn.com.flashman.cordova.xpush;

import java.util.Stack;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class Baidu extends CordovaPlugin {
  static private CordovaActivity webview;
  static private int icon;
  static public Stack messageQueue = new Stack();
  static public int nitify_id = 65535;
  static private boolean isActive = false;

    /**
     * Constructor.
     */
    public Baidu() {
      
    }

    //设置webview引用
    static public void setWebview(CordovaActivity wv){
      webview = wv;
    }
    
    //设置界面是否活跃状态
    static public void setActive(boolean bool){
      isActive = bool;
    }
    
    static public void setIcon(int i){
      icon = i;
    }

  static public void pushCallback(JSONObject r) {
    //前台直接通知界面
    if(isActive && !webview.isFinishing()){
      webview.sendJavascript("cordova.plugins.XPush.Baidu.pushCheck()");
      
    //后台发送消息通知
    }else{
      try {
        if(r.getString("type") == "message"){
          JSONObject info = new JSONObject(r.getString("message"));
          String title = info.getString("title");
          String desc = info.getString("description");
          NotificationManager nm = (NotificationManager) webview.getSystemService(webview.NOTIFICATION_SERVICE);
          Notification notification = new Notification(icon, title, System.currentTimeMillis());
          notification.defaults = Notification.DEFAULT_ALL;
          PendingIntent pt = PendingIntent.getActivity(webview, 0, new Intent(webview, webview.getClass()), 0);
          notification.setLatestEventInfo(webview, title, desc, pt);
          nm.notify(nitify_id, notification);
        }
      } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
    
    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    // public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    //     super.initialize(cordova, webView);
    // }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
     public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
         JSONObject r = new JSONObject();
        if (action.equals("pushCheck")) {
          if(!messageQueue.isEmpty()){
            r = (JSONObject) messageQueue.pop();
            r.put("status", 1); //Succeed
          }else{
            r.put("status", 0); //Nothing
          }
    } else if (action.equals("getUserId")) {
            r.put("status", 1); //Succeed
      r.put("type", "getUserId");
      r.put("userid", Utils.getUserId(webview.getContext()));
        }else {
            return false;
        }
        callbackContext.success(r);
        return true;
    }

}