package com.sample.home


import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.sample.domain.dto.login.products.DomainProduct
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavTypes {
    val DomainProductType = object : NavType<DomainProduct>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): DomainProduct? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): DomainProduct {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: DomainProduct): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: DomainProduct) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}