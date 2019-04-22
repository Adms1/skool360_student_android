package com.anandniketanbhadaj.skool360student.FCMImplementation;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static Context ctx;
    private static int notifyID = 1;
    String data,message;
    private String screen = "";

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ctx = this;

        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));

//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        Log.d(TAG, "Notification Message Title: " + remoteMessage.getNotification().getTitle());
//        Log.d(TAG, "Notification Message Data: " + remoteMessage.getData().toString());
//        Log.d(TAG, "Notification Message icon:" + remoteMessage.getNotification().getIcon());
//        Log.d(TAG, "Notification Message Notification: " + remoteMessage.getNotification());
//        Map<String, String> params = remoteMessage.getData();
//        JSONObject object = new JSONObject(params);
//        try {
//            data = object.getString("type").toString();
//            Log.d(TAG, "Megha" + data);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        message=remoteMessage.getNotification().getBody();
//        Log.e("JSON_OBJECT", object.toString());
        sendNotification(remoteMessage);//remoteMessage.getNotification().getBody());
    }
//    @Override
//    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
//
//
//    }
    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage remoteMessage) {
        notifyID = (int) (System.currentTimeMillis() & 0xfffffff);

        Intent notificationIntent = new Intent(ctx, DashBoardActivity.class);

        String data = String.valueOf(remoteMessage.getData());
//
//        try {
//            JSONObject dataObject = new JSONObject (data);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Utility.setPref(ctx, "studid", remoteMessage.getData().get("StudentId"));

        notificationIntent.putExtra("fromNotification",remoteMessage.getData().get("type"));
        if(remoteMessage.getData().get("type").equalsIgnoreCase("Birthday")){
            notificationIntent.putExtra("Name",remoteMessage.getData().get("Name"));
        }
//        else if(remoteMessage.getData().get("type").equalsIgnoreCase("Announcement")){
//
//        }

        notificationIntent.putExtra("message",remoteMessage.getData().get("body"));//remoteMessage.getNotification().getBody());
        notificationIntent.putExtra("cometonotification","true");
        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));

        notificationIntent.setAction(String.valueOf(notifyID));
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, notifyID, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker(String.valueOf(remoteMessage.getData().get("body")))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Skool 360")//Bhadaj
                .setContentText(remoteMessage.getData().get("body") + " from " + remoteMessage.getData().get("Name"))//remoteMessage.getNotification().getBody()
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true).build();

        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        // Play default notification sound
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.defaults |= Notification.DEFAULT_LIGHTS;

        // Vibrate if vibrate is enabled
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        //Show the notification
        notificationManager.notify(notifyID, noti);
        //Integer.valueOf(push_message_id)

        /*Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(messageBody)
                .setContentTitle("Skool 360 Shilaj")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingNotificationIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());*/
    }

//        Log.e(TAG, "From: " + remoteMessage.getData().toString());
//
//        if (remoteMessage == null)
//            return;
//
//        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification());
//        Log.d(TAG, "Message :" + remoteMessage.getData());
//
//        String data = String.valueOf(remoteMessage.getData());
//        String message = remoteMessage.getNotification().getBody();
//        Utility.setPref(this, "data", data);
//        Utility.setPref(this, "message", message);
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//            //data = remoteMessage.getData().get("type");
//            message = remoteMessage.getNotification().getBody();
//            String[] split = message.split("\\-");
//            AppConfiguration.dataNOtification = split[0].trim();
//            AppConfiguration.messageNotification = message;
//            if (!AppConfiguration.dataNOtification.equalsIgnoreCase("")) {
//                Log.d("dataNotification", AppConfiguration.dataNOtification);
//                Log.d("dataMessage", AppConfiguration.messageNotification);
//            }
//            handleNotification(message, split[0]);
//        } else {
//            // Check if message contains a data payload.
//            if (remoteMessage.getData().size() > 0) {
//                try {
//                    JSONObject json = new JSONObject(remoteMessage.getData().get("data").toString());
////                    Utility.setPref(this, "data", json.getString("data"));
////                    Utility.setPref(this, "message", json.getString("message"));
////
////                    AppConfiguration.dataNOtification = json.getString("data").trim();
////                    AppConfiguration.messageNotification = json.getString("message");
////                    if (!AppConfiguration.dataNOtification.equalsIgnoreCase("")) {
////                        Log.d("dataNotification", AppConfiguration.dataNOtification);
////                        Log.d("dataMessage", AppConfiguration.messageNotification);
////                    }
//                    handleDataMessage(json);
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception: " + e.getMessage());
//                }
//            }
//        }
//

//    }

//    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//
//        try {
//            JSONObject jsonObject = json.getJSONObject("data");
//
//            String data = jsonObject.getString("type");
//            String message = jsonObject.getString("message");
//
//
//            if (!isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                sendNotification(message, data);
//            } else {
//                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                resultIntent.putExtra("message", message);
//                resultIntent.putExtra("fromNotification", data);
//                sendNotification(message, data);
//
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
//    private void sendNotification(String message, String data) {
//        notifyID = (int) (System.currentTimeMillis() & 0xfffffff);
//
//        Intent notificationIntent = new Intent().setClass(ctx, LoginActivity.class);
//        notificationIntent.putExtra("fromNotification", data);
//        notificationIntent.putExtra("message", message);
//        notificationIntent.putExtra("cometonotification", "true");
//        Log.d("Messsagetype", message + data);
//
//        notificationIntent.setAction(String.valueOf(notifyID));
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent pendingNotificationIntent =
//                PendingIntent.getActivity(ctx, notifyID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification noti = new NotificationCompat.Builder(ctx)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setTicker(message)
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Skool 360")//Bhadaj
//                .setContentText(message)//remoteMessage.getNotification().getBody()
//                .setContentIntent(pendingNotificationIntent)
//                .setAutoCancel(true).build();
//
//        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
//        noti.flags |= Notification.FLAG_AUTO_CANCEL;
//        // Play default notification sound
//        noti.defaults |= Notification.DEFAULT_SOUND;
//        noti.defaults |= Notification.DEFAULT_LIGHTS;
//
//        // Vibrate if vibrate is enabled
//        noti.defaults |= Notification.DEFAULT_VIBRATE;
//        //Show the notification
//        notificationManager.notify(notifyID, noti);
//        //Integer.valueOf(push_message_id)
//
//        /*Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentText(messageBody)
//                .setContentTitle("Skool 360 Shilaj")
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingNotificationIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());*/
//    }

//    private void handleNotification(String message, String data) {
//        if (!isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//
//            sendNotification(message, data);
//        } else {
//            // If the app is in background, firebase itself handles the notification
//            sendNotification(message, data);
//        }
//    }


}
