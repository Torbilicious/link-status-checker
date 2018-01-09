package de.torbilicious.linkchecker.site

import de.torbilicious.linkchecker.site.SiteState.DOWN
import java.net.URL

data class Site(val url: URL) {
    var state = DOWN
        set(value) {
            previousState = state
            field = value
        }

    var previousState = DOWN
        private set
}