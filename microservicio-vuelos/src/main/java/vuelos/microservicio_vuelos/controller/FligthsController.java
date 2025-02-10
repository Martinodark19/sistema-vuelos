package vuelos.microservicio_vuelos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import vuelos.microservicio_vuelos.dto.FlightDto;
import vuelos.microservicio_vuelos.service.FligthsService;

@RestController
@RequestMapping("/flight")
public class FligthsController 
{
    private FligthsService fligthsService;

    public FligthsController(FligthsService fligthsService)
    {
        this.fligthsService = fligthsService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo() 
    {
        return ResponseEntity.ok("Información del usuarioo biennn");
    }

    @PostMapping("createFlight")
        public Mono<ResponseEntity<String>> createFligths(@RequestBody FlightDto flightDto) {
            return fligthsService.createFlight(flightDto) // Llama al servicio que devuelve un Mono<Boolean>
                .flatMap(sendToService -> 
                {
                    if (sendToService) 
                    {
                        System.out.println("paso por el true: " + sendToService);
                        return Mono.just(ResponseEntity.status(200).body("Vuelo creado con éxito"));
                    } 
                    else 
                    {
                        System.out.println("Vuelo no creado: " + sendToService);
                        return Mono.just(ResponseEntity.status(404).body("Ha ocurrido un error al crear el vuelo"));
                    }
                })
                .onErrorResume(error -> {
                    System.err.println("Error en el servidor: " + error.getMessage());
                    return Mono.just(ResponseEntity.status(500).body("Ha ocurrido un error inesperado en el servidor. Por favor, reintente nuevamente."));
                });
}

}
