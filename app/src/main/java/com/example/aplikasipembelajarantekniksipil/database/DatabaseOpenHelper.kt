package com.example.aplikasipembelajarantekniksipil.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper : SQLiteAssetHelper {

    constructor(context: Context): super(context, "aplikasi_teknik_sipil.db", null, 1)
}