package com.totof.callhelp.util

import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * Utility to convert satellite GPS WGS84 coordinates (latitude, longitude)
 * into DFCI grid position coordinates used by French emergency services (e.g. KF60C7.2).
 */
object DfciConverter {

    /**
     * Converts WGS84 latitude and longitude in degrees to a DFCI grid coordinate string.
     * Example: (45.089512, 5.7123941) -> "KF60C7.2"
     */
    fun fromWgs84(lat: Double, lng: Double): String {
        // Lambert II Extended projection parameters (EPSG:27572)
        val a = 6378249.2
        val f = 1.0 / 293.4660212936269
        val e = sqrt((2 * f) - (f * f))
        val phi0 = Math.toRadians(46.8)
        val lambda0 = Math.toRadians(2.337229166666667) // Paris meridian relative to Greenwich
        val x0 = 600000.0
        val y0 = 2200000.0

        fun latIsoNtf(phi: Double): Double {
            val sinPhi = sin(phi)
            return ln(tan((Math.PI / 4) + (phi / 2)) * ((1 - (e * sinPhi)) / (1 + (e * sinPhi))).pow(e / 2))
        }

        val l0 = latIsoNtf(phi0)
        val n = sin(phi0)
        val c = (a / sqrt(1 - (e * e * sin(phi0) * sin(phi0)))) * (1.0 / tan(phi0)) * exp(n * l0)

        val phi = Math.toRadians(lat)
        val lambda = Math.toRadians(lng)
        val l = latIsoNtf(phi)
        val rho = c * exp(-n * l)
        val theta = n * (lambda - lambda0)

        val x = x0 + (rho * sin(theta))
        val y = (y0 + (c * exp(-n * l0))) - (rho * cos(theta))

        // Letters for 100km block (excluding I and J)
        val letters100 = "ABCDEFGHKLMNPQRSTUVWXYZ"

        val ix100 = floor(x / 100000.0).toInt()
        val iy100 = floor((y - 1500000.0) / 100000.0).toInt()

        val l100x = if (ix100 in letters100.indices) letters100[ix100] else '?'
        val l100y = if (iy100 in letters100.indices) letters100[iy100] else '?'

        val rx100 = ((x % 100000.0) + 100000.0) % 100000.0
        val ry100 = ((y % 100000.0) + 100000.0) % 100000.0

        val d20x = floor(rx100 / 20000.0).toInt() * 2
        val d20y = floor(ry100 / 20000.0).toInt() * 2

        val rx20 = rx100 % 20000.0
        val ry20 = ry100 % 20000.0

        val l2k = "ABCDE"[floor(rx20 / 2000.0).toInt().coerceIn(0, 4)]

        val rx2 = rx20 % 2000.0
        val ry2 = ry20 % 2000.0

        val r5 = floor((2000.0 - ry2) / 400.0).toInt().coerceIn(0, 4)
        val c5 = floor((rx2 % 1000.0) / 400.0).toInt().coerceIn(0, 4)

        val dSub = (r5 * 5) + c5 + 1

        val rx400 = rx2 % 400.0
        val cDot = floor(rx400 / 80.0).toInt().coerceIn(0, 4) + 1

        return "$l100x$l100y$d20x$d20y$l2k$dSub.$cDot"
    }
}
