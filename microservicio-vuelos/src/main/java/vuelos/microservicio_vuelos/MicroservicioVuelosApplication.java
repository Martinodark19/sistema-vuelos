package vuelos.microservicio_vuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservicioVuelosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioVuelosApplication.class, args);
	}

}
