package de.torbilicious.linkchecker.checker

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import de.torbilicious.linkchecker.site.Site
import de.torbilicious.linkchecker.site.SiteState
import de.torbilicious.linkchecker.site.SiteState.DOWN
import de.torbilicious.linkchecker.site.SiteState.UP
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*


class LinkChecker {
    init {
        val cert = getPem()
        if (cert != null) {
            if (FuelManager.instance.keystore == null) {
                val ks = loadDefaultKeystore()
                ks?.setCertificateEntry(UUID.randomUUID().toString(), cert)

                FuelManager.instance.keystore = loadDefaultKeystore()
            }

        }
    }

    fun check(site: Site): SiteState {
        val (_, response) = Fuel.get(site.url.toString()).responseString()

        return if (response.statusCode == 200) {
            UP
        } else {
            DOWN
        }
    }
}

fun getPem(): Certificate? {
    val pemStream = LinkChecker::class.java.
            classLoader.
            getResource("pflive.pem").openStream()


    val fact = CertificateFactory.getInstance("X.509")
    return fact.generateCertificate(pemStream)
}

fun loadDefaultKeystore(): KeyStore? {
    val filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar)
    val `is` = FileInputStream(filename)
    val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
    val password = "changeit"
    keystore.load(`is`, password.toCharArray())

    return keystore
}
