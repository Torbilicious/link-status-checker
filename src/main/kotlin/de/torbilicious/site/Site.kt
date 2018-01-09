package de.torbilicious.site

import de.torbilicious.site.SiteState.*
import java.net.URL

data class Site(val url: URL) {
    var previousState = DOWN
}