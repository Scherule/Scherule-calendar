package com.scherule.calendaring

fun fillToUUIDString(ending: String) = "00000000-0000-0000-0000-000000000000"
        .replaceRange(IntRange(35 - ending.count(), 35), ending)