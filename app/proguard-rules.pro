-keepattributes *Annotation*
-keepclassmembers class kotlinx.serialization.internal.SerializationConstructorMarker

-keep,includedescriptorclasses class com.vaultlinks.app.**$$serializer { *; }
-keepclassmembers class com.vaultlinks.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.vaultlinks.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class com.vaultlinks.app.data.local.entity.** { *; }
-keep class com.vaultlinks.app.domain.model.** { *; }

-dontwarn org.jsoup.**
-keep class org.jsoup.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
