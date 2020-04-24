package com.example.androidlectureexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Example26Sub_ArduinoNetworkService extends Service {
    public Example26Sub_ArduinoNetworkService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
