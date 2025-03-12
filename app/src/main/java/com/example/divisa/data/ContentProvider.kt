package com.example.divisa.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class ExchangeRateProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.divisa.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/exchange_rates")
    }

    private lateinit var db: AppDatabase

    override fun onCreate(): Boolean {
        context?.let {
            db = AppDatabase.getDatabase(it)
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?
    ): Cursor? {
        // Aquí implementa la consulta a la base de datos, ya sea utilizando el DAO o consultas SQLite directas.
        throw UnsupportedOperationException("query() not implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // Implementa la inserción de datos.
        throw UnsupportedOperationException("insert() not implemented")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("update() not implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("delete() not implemented")
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}
