package hr.tvz.android.projektplaftak.controller

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.model.Jersey

class WidgetProvider: AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val widgetJersey: Jersey? = intent?.extras?.getParcelable(context?.resources?.getString(R.string.widgetJersey), Jersey::class.java)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidget = ComponentName(context!!.packageName, WidgetProvider::class.java.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(appWidget)

        for(appWidgetId in appWidgetIds){
            val remoteViews = RemoteViews(context.packageName, R.layout.widget)

            when(widgetJersey?.id){
                1 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.grizzlies)
                2 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.cowboys)
                3 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.nets)
                4 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.pelicans)
                5 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.pistons)
                6 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.mercury)
                7 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.san_francisco)
                8 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.kings)
                9 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.raptors)
                10 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.blazers)
                11 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.vikings)
                12 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.knicks)
                13 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.hawks)
                14 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.eagles)
                15 -> remoteViews.setImageViewResource(R.id.widgetJerseyImage, R.drawable.bucks)
            }

            remoteViews.setTextViewText(R.id.widgetJerseyTeam, widgetJersey?.team)

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }

        super.onReceive(context, intent)
    }

}