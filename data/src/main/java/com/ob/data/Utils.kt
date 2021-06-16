package com.ob.data

import java.math.BigInteger
import java.security.*

object Utils {
  fun md5(input: String): String = try {
    var md5: String = BigInteger(1, MessageDigest.getInstance("MD5").digest(input.toByteArray())).toString(16)
    while (md5.length < 32) md5 = "0$md5"
    md5
  } catch (e: NoSuchAlgorithmException) {
    ""
  }
}