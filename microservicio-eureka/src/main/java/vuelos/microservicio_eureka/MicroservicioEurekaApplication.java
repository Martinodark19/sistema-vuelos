package vuelos.microservicio_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@SpringBootApplication
//habilitar el servidor de eureka
@EnableEurekaServer
public class MicroservicioEurekaApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(MicroservicioEurekaApplication.class, args);
	}
}
