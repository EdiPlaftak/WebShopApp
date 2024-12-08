package hr.tvz.android.projektplaftak.controller

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessagingService.NOTIFICATION_SERVICE
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.model.Jersey

class OrderBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val handler = Handler(Looper.getMainLooper())

        handler.post{

            val broadcastJersey = intent?.extras?.getParcelable(context?.resources?.getString(R.string.broadcastJersey), Jersey::class.java)

            val notificationBuilder = Notification.Builder(context, context?.resources?.getString(R.string.appOrderChannel))

            notificationBuilder.setSmallIcon(R.drawable.cart)
            notificationBuilder.setContentTitle(context?.resources?.getString(R.string.successfulOrder))
            notificationBuilder.setContentText(context?.resources?.getString(R.string.successfulOrderClick))

            val resultIntent = Intent(context, JerseyActivity::class.java)
            resultIntent.putExtra(context?.resources?.getString(R.string.jersey), broadcastJersey)

            var pendingIntent: PendingIntent? = null

            when(broadcastJersey?.id){
                1 -> pendingIntent = PendingIntent.getActivity(context, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                2 -> pendingIntent = PendingIntent.getActivity(context, 2, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                3 -> pendingIntent = PendingIntent.getActivity(context, 3, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                4 -> pendingIntent = PendingIntent.getActivity(context, 4, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                5 -> pendingIntent = PendingIntent.getActivity(context, 5, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                6 -> pendingIntent = PendingIntent.getActivity(context, 6, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                7 -> pendingIntent = PendingIntent.getActivity(context, 7, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                8 -> pendingIntent = PendingIntent.getActivity(context, 8, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                9 -> pendingIntent = PendingIntent.getActivity(context, 9, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                10 -> pendingIntent = PendingIntent.getActivity(context, 10, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                11 -> pendingIntent = PendingIntent.getActivity(context, 11, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                12 -> pendingIntent = PendingIntent.getActivity(context, 12, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                13 -> pendingIntent = PendingIntent.getActivity(context, 13, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                14 -> pendingIntent = PendingIntent.getActivity(context, 14, resultIntent, PendingIntent.FLAG_IMMUTABLE)
                15 -> pendingIntent = PendingIntent.getActivity(context, 15, resultIntent, PendingIntent.FLAG_IMMUTABLE)
            }

            notificationBuilder.setContentIntent(pendingIntent)

            val notification = notificationBuilder.build()

            val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(2, notification)
        }

    }

}