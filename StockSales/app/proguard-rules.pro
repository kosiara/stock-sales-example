# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/kosiara/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes SourceFile, LineNumberTable, Signature, *Annotation*

# ===========  BUTTERKNIFE ===================
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-dontwarn okio.**
-dontwarn java.lang.invoke**


# keep model
-keep class com.bk.stocksales.model.** { *; }

-keep class com.bk.stocksales.conversion.model.** { *; }

# guava
-injars path/to/myapplication.jar
-injars lib/guava-r07.jar
-libraryjars lib/jsr305.jar
-outjars myapplication-dist.jar

-dontoptimize
-dontobfuscate
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}
