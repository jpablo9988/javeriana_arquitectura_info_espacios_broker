package co.smartuj.info_espacios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
 
@OpenAPIDefinition(
        info = @Info(
                title = "pujkong",
                version = "v2.0"
        ),
        servers = @Server(url = "http://pujkong:8080")
)
public class OpenAPIConfig {
}