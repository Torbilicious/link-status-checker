package de.torbilicious.linkchecker.checker

import com.github.kittinunf.fuel.Fuel
import de.torbilicious.linkchecker.site.Site
import de.torbilicious.linkchecker.site.SiteState
import de.torbilicious.linkchecker.site.SiteState.DOWN
import de.torbilicious.linkchecker.site.SiteState.UP


class LinkChecker {
    fun check(site: Site): SiteState {
        val (_, response) = Fuel.get(site.url.toString()).responseString()

        return if (response.statusCode == 200) {
            UP
        } else {
            DOWN
        }
    }
}
