package de.torbilicious.linkchecker.checker

import de.torbilicious.linkchecker.site.Site
import de.torbilicious.linkchecker.site.SiteState
import de.torbilicious.linkchecker.site.SiteState.DOWN
import de.torbilicious.linkchecker.site.SiteState.UP
import khttp.get

class LinkChecker {
    fun check(site: Site): SiteState {
        val code = get(site.url.toString()).statusCode

        return if (code == 200) {
            UP
        } else {
            DOWN
        }
    }
}