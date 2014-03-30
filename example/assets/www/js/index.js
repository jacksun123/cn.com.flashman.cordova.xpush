/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
        document.addEventListener('resume', this.onResume, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicity call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        var parentElement = document.getElementById('deviceready');
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');
        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        app.pushCheck();
    },
    onResume: function() {
        app.pushCheck();
    },
    pushCheck: function(){
        var baidu = cordova.require('cn.com.flashman.cordova.xpush.Baidu');
        baidu.pushCallback = app.pushCallback;
        baidu.pushCheck();
    },
    pushCallback: function(message){
        if(message['status'] == 1){
            var out = "My pushCallback\n";
            out += app.dump(message, 'message');
            alert(out);
            console.log(out);
        }
    },
    dump: function(obj, name, depth) {
      if (depth > 5) {
             return indent + name + ": <Maximum Depth Reached>\n";
      }
      if (typeof obj == "object") {
         var child = null;
         var output = name + ": \n";
         for (var item in obj)
         {
           try {
                  child = obj[item];
           } catch (e) {
                  child = "<Unable to Evaluate>";
           }
           if (typeof child == "object") {
                  output += app.dump(child, item, depth + 1);
           } else {
                  output += "\t" + item + ": " + child + "\n";
           }
         }
         return output;
      } else {
         return obj;
      }
   }
    
};
