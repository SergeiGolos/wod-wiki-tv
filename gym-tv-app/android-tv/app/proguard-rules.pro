# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/you/Library/Android/sdk/tools/proguard/proguard-android-optimize.txt
# You can edit the include path and order by changing the proguardFiles
# attribute in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If you use reflection or introspection add the necessary keep rules.
# For example, if you use Ktor's default serialization, you might need:
# -keepattributes Signature
# -keepclassmembers class * { @kotlinx.serialization.Serializable <fields>; }
# -keepclasseswithmembers class * { @kotlinx.serialization.Serializable <methods>; }
# -keep class kotlinx.serialization.internal.*

# Hilt
# Hilt uses reflection and generated code, these rules prevent R8 from removing necessary code.
-dontwarn dagger.hilt.android.internal.** # Suppress warnings for Hilt's internal classes.
-dontwarn dagger.hilt.internal.aggregatedroot.codegen.** # Suppress warnings for Hilt's generated root codegen.
-dontwarn dagger.hilt.processor.internal.disableplugins.DisablePluginsProcessor # Suppress warnings for Hilt's plugin disabling processor.
