import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.cors.CORS
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import kotlinx.serialization.json.Json
import io.ktor.server.plugins.forwardedheaders.*
fun main(args: Array<String>){
    val env = applicationEngineEnvironment {
        envConfig()
    }
    embeddedServer(CIO, env).start(wait = true)
}
fun ApplicationEngineEnvironmentBuilder.envConfig() {
    module {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(ForwardedHeaders)
        install(Authentication) {
            bearer {
                // Configure bearer authentication
            }
        }
        install(Resources)
        install(CORS)
//        install(Compression)
        module()

    }
    connector {
        host = "0.0.0.0"
        port = 8080
    }

}
fun Application.module() {

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}