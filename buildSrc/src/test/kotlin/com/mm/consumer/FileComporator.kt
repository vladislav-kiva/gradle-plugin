package com.mm.consumer

import java.io.BufferedReader
import java.io.FileReader

object FileComporator {

    public fun isFilesEquals(expectedPath: String, pathToCompare: String): Boolean {
        val reader1 = BufferedReader(FileReader(expectedPath))
        val reader2 = BufferedReader(FileReader(pathToCompare))
        var line1: String? = reader1.readLine()
        var line2: String? = reader2.readLine()
        var areEqual = true
        var lineNum = 1
        while (line1 != null || line2 != null) {
            if (line1 == null || line2 == null) {
                areEqual = false
                break
            } else if (!line1.equals(line2, ignoreCase = true)) {
                areEqual = false
                break
            }
            line1 = reader1.readLine()
            line2 = reader2.readLine()
            lineNum++
        }
        reader1.close()
        reader2.close()
        return if (areEqual) {
            true
        } else {
            println("Two files have different content. They differ at line $lineNum")
            println("Expected has $line1 and but has $line2 at line $lineNum")
            false
        }
    }
}