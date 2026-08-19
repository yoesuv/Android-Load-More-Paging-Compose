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

#----------------------------------------------------------------------------
# kotlinx.serialization (used for network models + Navigation type-safe routes)
#----------------------------------------------------------------------------
# Navigation type-safe routes (composable<Route>() / toRoute<Route>()) and the
# custom NavType resolve serializers reflectively via typeOf<T>() +
# serializer(KType), so R8 must keep the @Serializable classes, their companion
# objects and the generated *$$serializer classes.
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Keep all @Serializable classes (PostModel, AppRoute + subclasses)
-keep @kotlinx.serialization.Serializable class com.yoesuv.infinite_scroll.** { *; }

# Keep companion objects so their serializer() accessors survive
-keepclassmembers @kotlinx.serialization.Serializable class com.yoesuv.infinite_scroll.** {
    *** Companion;
}

# Keep generated serializers
-keep,includedescriptorclasses class com.yoesuv.infinite_scroll.**$$serializer { *; }
-keepclassmembers class com.yoesuv.infinite_scroll.** {
    kotlinx.serialization.KSerializer serializer(...);
}