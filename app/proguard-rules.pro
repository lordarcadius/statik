# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Route sealed class hierarchy + nested types
-keep class com.vipuljha.statik.navigation.Route { *; }
-keep class com.vipuljha.statik.navigation.Route$* { *; }

# Keep generated serializers and singletons in navigation package
-keepclassmembers class com.vipuljha.statik.navigation.** {
    public static ** INSTANCE;
    public static kotlinx.serialization.KSerializer serializer(...);
}

# Keep only serializer accessors for your models
-keepclassmembers class com.vipuljha.statik.feature.**.domain.model.** {
    public static ** INSTANCE;
    public static kotlinx.serialization.KSerializer serializer(...);
}

# Keep serializer accessors for *only* classes annotated with @Serializable
-if @kotlinx.serialization.Serializable class *
-keepclassmembers class <1> {
    public static ** INSTANCE;
    public static kotlinx.serialization.KSerializer serializer(...);
}