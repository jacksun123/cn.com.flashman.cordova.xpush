cn.com.flashman.cordova.xpush
=============================

百度推送cordova/phonegap插件

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
