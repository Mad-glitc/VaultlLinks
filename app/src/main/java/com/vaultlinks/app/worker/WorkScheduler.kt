package com.vaultlinks.app.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduler @Inject constructor(@dagger.hilt.android.qualifiers.ApplicationContext private val context: Context) {

    private val workManager get() = WorkManager.getInstance(context)

    fun enqueueMetadataFetch(linkId: Long) {
        val request = OneTimeWorkRequestBuilder<MetadataFetchWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(workDataOf(MetadataFetchWorker.KEY_LINK_ID to linkId))
            .build()
        workManager.enqueueUniqueWork("metadata_fetch_$linkId", ExistingWorkPolicy.REPLACE, request)
    }

    fun scheduleDailyReminder(hour: Int, minute: Int) {
        val delay = computeInitialDelay(hour, minute)
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            ReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelDailyReminder() {
        workManager.cancelUniqueWork(ReminderWorker.WORK_NAME)
    }

    private fun computeInitialDelay(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }
}
