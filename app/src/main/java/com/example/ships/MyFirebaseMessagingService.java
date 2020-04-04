package com.example.ships;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("NEW_TOKEN",token);

        storeToken(token);
    }

//
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        final Intent intent = new Intent(this, MainActivity.class);
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        int notificationID = new Random().nextInt(3000);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            setupChannels(notificationManager);
//        }
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
//                R.drawable.notify_icon);
//
//        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.notify_icon)
//                .setLargeIcon(largeIcon)
//                .setContentTitle(remoteMessage.getData().get("title"))
//                .setContentText(remoteMessage.getData().get("message"))
//                .setAutoCancel(true)
//                .setSound(notificationSoundUri)
//                .setContentIntent(pendingIntent);
//
//        //Set notification color to match your app color template
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
//        }
//        notificationManager.notify(notificationID, notificationBuilder.build());
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setupChannels(NotificationManager notificationManager) {
//        CharSequence adminChannelName = "New notification";
//        String adminChannelDescription = "Device to device notification";
//
//        NotificationChannel adminChannel;
//        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
//        adminChannel.setDescription(adminChannelDescription);
//        adminChannel.enableLights(true);
//        adminChannel.setLightColor(Color.RED);
//        adminChannel.enableVibration(true);
//        if (notificationManager != null) {
//            notificationManager.createNotificationChannel(adminChannel);
//        }
//    }
//
//

    private void storeToken(String token) {
        SharedPreferencesManager.getInstance(getApplicationContext()).storeToken(token);
    }


}
