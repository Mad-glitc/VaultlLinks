package com.vaultlinks.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.vaultlinks.app.MainActivity
import com.vaultlinks.app.R
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.vaultlinks.app.domain.repository.LinkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Home screen widget: shows today's save count and a one-tap "Quick Save" button that opens
 * VaultLinks straight into the Save screen. No network calls, no data leaves the widget.
 */
class QuickSaveWidgetReceiver : AppWidgetProvider() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WidgetEntryPoint {
        fun linkRepository(): LinkRepository
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { widgetId -> updateWidget(context, appWidgetManager, widgetId) }
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_quick_save)

        val quickSaveIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, widgetId, quickSaveIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_quick_save_button, pendingIntent)
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent)

        // Best-effort count fetch; widgets update on a coarse periodic schedule so a brief
        // "…" while this resolves is an acceptable trade-off versus blocking onUpdate().
        views.setTextViewText(R.id.widget_recent_count, "Tap to save a link")
        appWidgetManager.updateAppWidget(widgetId, views)

        runCatching {
            val entryPoint = EntryPointAccessors.fromApplication(context.applicationContext, WidgetEntryPoint::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val total = entryPoint.linkRepository().observeTotalCount().first()
                val refreshedViews = RemoteViews(context.packageName, R.layout.widget_quick_save)
                refreshedViews.setTextViewText(R.id.widget_recent_count, "$total links saved")
                refreshedViews.setOnClickPendingIntent(R.id.widget_quick_save_button, pendingIntent)
                refreshedViews.setOnClickPendingIntent(R.id.widget_title, pendingIntent)
                appWidgetManager.updateAppWidget(widgetId, refreshedViews)
            }
        }
    }
}
