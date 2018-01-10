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
        val certs = getPems()
        val ks = loadDefaultKeystore()
        certs.forEach {
            ks?.setCertificateEntry(UUID.randomUUID().toString(), it)
        }

        FuelManager.instance.keystore = loadDefaultKeystore()
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

fun getPems(): List<Certificate?> {
    val pems = listOf("pflive.pem", "pfstage.pem", "pftest.pem")

    return pems.map {
        val pemStream = LinkChecker::class.java.
                classLoader.
                getResource(it).openStream()


        val fact = CertificateFactory.getInstance("X.509")

        fact.generateCertificate(pemStream)
    }
}

fun loadDefaultKeystore(): KeyStore? {
    val filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar)
    val `is` = FileInputStream(filename)
    val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
    val password = "changeit"
    keystore.load(`is`, password.toCharArray())

    return keystore
}
