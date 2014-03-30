
cordova create hello com.example.hello HelloWorld
cd hello
cordova plugin add ~/Documents/gCloud/Android/Cordova-XPush/plugins/cn.com.flashman.cordova.xpush/

cp -f plugins/cn.com.flashman.cordova.xpush/www/index.html www/
cp -f plugins/cn.com.flashman.cordova.xpush/www/index.js www/js/

cordova platform add android

cp -f plugins/cn.com.flashman.cordova.xpush/AndroidManifest.xml platforms/android/
cp -f plugins/cn.com.flashman.cordova.xpush/src/android/HelloWorld.java platforms/android/src/com/example/hello/

cordova build android
or
cordova run android

Baidu推送

	修改AndroidManifest.xml
	    <!-- 在百度开发者中心查询应用的API Key -->
	    <meta-data android:name="api_key" android:value="YYWwiP0RXLeRdhx6BECbIQnD" />
	    <!-- push service end -->

	Android下需要在AndroidManifest.xml中添加属性，否则执行绑定时会报错。
		<application android:name="com.baidu.frontia.FrontiaApplication" .../>

	主Activity里添加属性
		<activity android:launchMode="singleTask" .../>

	启动服务代码 参考src/android/HelloWorld.java

		//依赖包
		import android.app.Notification;
		import android.content.res.Resources;
		import com.baidu.android.pushservice.CustomPushNotificationBuilder;
		import com.baidu.android.pushservice.PushConstants;
		import com.baidu.android.pushservice.PushManager;

        //设置webview引用
        Baidu.setWebview(this);

		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
		// 这里把apikey存放于manifest文件中，只是一种存放方式，
		// 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this, "api_key")
		// 通过share preference实现的绑定标志开关，如果已经成功绑定，就取消这次绑定
		if (!Utils.hasBind(getApplicationContext())) {
			Log.d("YYY", "before start work at " + Calendar.getInstance().getTimeInMillis());
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY, 
					Utils.getMetaValue(HelloWorld.this, "api_key"));
			Log.d("YYY", "after start work at " + Calendar.getInstance().getTimeInMillis());
			// Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
			PushManager.enableLbs(getApplicationContext());
			Log.d("YYY", "after enableLbs at " + Calendar.getInstance().getTimeInMillis());
		}

		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				getApplicationContext(),
				resource.getIdentifier("notification_custom_builder", "layout", pkgName), 
				resource.getIdentifier("notification_icon", "id", pkgName), 
				resource.getIdentifier("notification_title", "id", pkgName), 
				resource.getIdentifier("notification_text", "id", pkgName));
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		cBuilder.setLayoutDrawable(resource.getIdentifier("simple_notification_icon", "drawable", pkgName));
		PushManager.setNotificationBuilder(this, 1, cBuilder);

