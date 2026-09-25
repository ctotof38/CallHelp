package com.totof.callhelp

import com.totof.callhelp.util.DfciConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testDfciConversion() {
        val dfci1 = DfciConverter.fromWgs84(45.089512, 5.7123941)
        val dfci2 = DfciConverter.fromWgs84(45.089519, 5.7123878)

        assertEquals("KF60C7.2", dfci1)
        assertEquals("KF60C7.2", dfci2)
    }

    @Test
    fun checkApkSize() {
        val apkFile = File("build/outputs/apk/debug/call-help.apk")
        if (apkFile.exists()) {
            val sizeMb = apkFile.length() / (1024.0 * 1024.0)
            println("Generated APK Size: String.format('%.2f MB', $sizeMb) -> $sizeMb MB")
        } else {
            println("APK file not found")
        }
    }
}
