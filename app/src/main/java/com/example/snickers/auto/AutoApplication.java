package com.example.snickers.auto;

import android.app.Application;
import android.content.Context;

public class AutoApplication extends Application {

        private static volatile Context context;

        @Override
        public void onCreate() {
            super.onCreate();
            context = this;
        }

        public static Context getInstanse(){
            return context;
        }

}
