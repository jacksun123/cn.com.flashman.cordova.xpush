cn.com.flashman.cordova.xpush
=============================

百度推送cordova/phonegap插件(目前只支持Android)

Example:

	cordova create hello com.example.hello HelloWorld
	cd hello
	cordova plugin add https://github.com/zhushunqing/cn.com.flashman.cordova.xpush

	cp -f plugins/cn.com.flashman.cordova.xpush/www/index.html www/
	cp -f plugins/cn.com.flashman.cordova.xpush/www/index.js www/js/

	cordova platform add android

	cp -f plugins/cn.com.flashman.cordova.xpush/AndroidManifest.xml platforms/android/
	cp -f plugins/cn.com.flashman.cordova.xpush/src/android/HelloWorld.java platforms/android/src/com/example/hello/

	cordova build android
	or
	cordova run android

	Android Project Files See ./platfrom/android/

OR:

	Eclipse -> New -> Other -> Android Project from Existing Code
	From "./example/"


注意事项：

	Android

	修改AndroidManifest.xml
	    <!-- 在百度开发者中心查询应用的API Key -->
	    <meta-data android:name="api_key" android:value="YYWwiP0RXLeRdhx6BECbIQnD" />
	    <!-- push service end -->

	需要在AndroidManifest.xml中添加属性，否则执行绑定时会报错。
		<application android:name="com.baidu.frontia.FrontiaApplication" .../>

	主Activity里添加属性
		<activity android:launchMode="singleTask" .../>

推送消息测试

	见 Baidu-Push-SDK-Php-2.0.4-basic/sample/sample.php