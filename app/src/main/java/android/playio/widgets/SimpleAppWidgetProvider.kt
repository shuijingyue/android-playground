package android.playio.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.*


class SimpleAppWidgetProvider() : AppWidgetProvider() {
    companion object {
        private const val TAG = "SimpleAppWidgetProvider"
    }
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "Hello world")
//        val simpleDataFormat = SimpleDateFormat("yyyyMMddHHmmss")
//        val start = simpleDataFormat.parse("20210304000000")
//        val end = simpleDataFormat.parse("20210305000000")
//        val calendar = Calendar.getInstance()
//        calendar.time = start!!
//        val startMillis = calendar.timeInMillis
//        calendar.time = end!!
//        val endMillis = calendar.timeInMillis
//        val usageStatsManager = context?.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager?
//        val stats = usageStatsManager?.queryAndAggregateUsageStats(startMillis, endMillis)
//        stats?.keys?.forEach {
//            Log.d(TAG, it)
//        }
    }
}