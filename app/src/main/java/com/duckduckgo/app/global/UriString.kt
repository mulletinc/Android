/*
 * Copyright (c) 2017 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.global

import android.net.Uri
import android.support.v4.util.PatternsCompat

class UriString {

    companion object {

        private const val localhost = "localhost"
        private const val space = " "
        private val webUrlRegex = PatternsCompat.WEB_URL.toRegex()

        fun sameOrSubdomain(child: String, parent: String): Boolean {
            val childHost = Uri.parse(child)?.baseHost ?: return false
            val parentHost = Uri.parse(parent)?.baseHost ?: return false
            return parentHost == childHost || childHost.endsWith(".$parentHost")
        }

        fun isWebUrl(inputQuery: String): Boolean {
            val uri = Uri.parse(inputQuery).withScheme()
            if (uri.scheme != UrlScheme.http && uri.scheme != UrlScheme.https) return false
            if (uri.userInfo != null) return false
            if (uri.host == null) return false
            if (uri.path.contains(space)) return false
            return isValidHost(uri.host)
        }

        private fun isValidHost(host: String): Boolean {
            if (host == localhost) return true
            if (host.contains(space)) return false
            if (host.contains("!")) return false

            if (webUrlRegex.containsMatchIn(host)) return true
            return false
        }
    }
}
