package de.torbilicious.linkchecker.config

import com.google.gson.Gson

class ConfigLoader {
    private val gson = Gson()

    fun getConfig(): ConfigModel {
        val configString = getConfigString()

        return gson.fromJson(configString, ConfigModel::class.java)
    }

    private fun getConfigString(): String {
        return ConfigLoader::class.java.
                classLoader.
                getResource("config.json").readText()
    }
}
