package com.yoesuv.infinitescroll.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.yoesuv.infinitescroll.core.models.PostModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

inline fun <reified T : Any> createSerializableNavType(
    isNullableAllowed: Boolean = false,
    customJson: Json = Json, // Allow providing a custom Json instance if needed
): NavType<T> {
    return object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        // Get the KSerializer for type T. This requires T to be serializable.
        // For non-primitive types, the compiler generates a .serializer() extension
        // or it can be found in a companion object.
        // For this to work best, T should have a publicly accessible serializer.
        // Using currentReifiedTypeSerializer() might be more robust if available
        // or you might need to pass the serializer explicitly if reification doesn't always find it.
        // However, for most common cases with @Serializable data classes, this should work.
        val serializer: KSerializer<T> = customJson.serializersModule.serializer() // Or a more direct way if possible

        override fun get(
            bundle: Bundle,
            key: String,
        ): T? = bundle.getString(key)?.let { customJson.decodeFromString(serializer, it) }

        override fun parseValue(value: String): T {
            // value is URL-decoded by Navigation framework before reaching here
            return customJson.decodeFromString(serializer, value)
        }

        override fun serializeAsValue(value: T): String {
            // Navigation framework will URL-encode this string
            return customJson.encodeToString(serializer, value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: T,
        ) {
            bundle.putString(key, customJson.encodeToString(serializer, value))
        }

        // Optional: override 'name' to be more descriptive if needed, though often not critical
        // override val name: String
        //     get() = T::class.java.name // Example name
    }
}

object CustomNavTypes {
    val PostModelType = createSerializableNavType<PostModel>()
}
