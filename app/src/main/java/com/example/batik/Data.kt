package com.example.batik

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Data(val name: String? = null, val date: String? = null, val asal: String? = null, val
description: String? = null, val url: String? = null, @Exclude val key: String? = null)