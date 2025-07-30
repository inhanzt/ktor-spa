package com.github.inhanzt.ktor.features

import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationCall
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import org.slf4j.event.Level
import kotlin.test.assertEquals
import java.io.File

class Tests {

    private val spaRoute = "test"
    private val folderPath = "spa"
    private val defaultPage = "lol.html"

    private fun <R> withSPATestApplication(test: TestApplicationEngine.() -> R) =
        withTestApplication({
            install(SinglePageApplication) {
                defaultPage = this@Tests.defaultPage
                folderPath = this@Tests.folderPath
                spaRoute = this@Tests.spaRoute
            }
            install(CallLogging) {
                level = Level.DEBUG
            }
        }, test)

    /**
    * Retrieves a resource from the given class loader or default one.
    * @param name The relative path of the resource.
    * @param classLoader The classloader from which load the resource.
    * @param file The file where the resource should be places.
    * @return The [file] with the resource.
    */
    @JvmOverloads
    private fun getResource(name: String,
                    file: File = createTempFile().apply { deleteOnExit() },
                    classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ) = classLoader.getResourceAsStream(name)!!.let {
        file.apply { writeBytes(it.readBytes()) }
    }

    private fun defaultTest(url: String = "/", tests: TestApplicationCall.() -> Unit) =
        withSPATestApplication {
            with(handleRequest(Get, url) {
                addHeader(HttpHeaders.Accept, ContentType.Text.Html.toString())
            }, tests)
        }

    @Test
    fun `root address 404`() = defaultTest {
        assertEquals(HttpStatusCode.NotFound, response.status())
    }

    @Test
    fun `spa root 200`() = defaultTest("/$spaRoute") {
        assertEquals(HttpStatusCode.OK, response.status())
        assertEquals(getResource("spa/lol.html").readText(), response.content)
    }

    @Test
    fun `existing resource`() = defaultTest("/$spaRoute/static/test.html") {
        assertEquals(HttpStatusCode.OK, response.status())
        assertEquals(getResource("spa/static/test.html").readText(), response.content)
    }

}
