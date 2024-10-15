package pe.edu.cibertec.patita_frontend_wc_a.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patita_frontend_wc_a.dto.LoginResponseDTO;
import pe.edu.cibertec.patita_frontend_wc_a.dto.LoginResquestDTO;

import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginControlllerAsync {

    @Autowired
    WebClient webClientAutenticacion;



    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginResquestDTO loginResquestDTO) {
        //validar caompos de entrada

        if(loginResquestDTO.tipoDocumento() == null || loginResquestDTO.tipoDocumento().trim().length() ==0 ||
         loginResquestDTO.numeroDocumento() == null || loginResquestDTO.numeroDocumento().trim().length() ==0 ||
         loginResquestDTO.password() == null || loginResquestDTO.password().trim().length() ==0){
            return Mono.just(new LoginResponseDTO("01", "Error : debe completar correctamente el campo","",""));
        }

         try {

//consumir el servivio del backend

           return webClientAutenticacion.post()
                     .uri("/login")
                     .body(Mono.just(loginResquestDTO),LoginResquestDTO.class)
                      .retrieve()
                     .bodyToMono(LoginResponseDTO.class)
                   .flatMap(response -> {
                       if(response.codigo().equals("00")){
                           return  Mono.just(new LoginResponseDTO("00","",response.nombreUsuario(),""));
                       }else {
                          return Mono.just(new LoginResponseDTO("02", "Errror Autenticacion fallida","",""));
                       }
                   });

         }catch (Exception e){
             System.out.println(e.getMessage());
             return Mono.just(new LoginResponseDTO("02", "Ocuarrio un problema en la autenticacion","",""));

         }

    }

    @PostMapping("/logout-async")
    public Mono<ResponseEntity<String>> cerrarSesion(@RequestBody LoginResquestDTO loginResquestDTO) {
        System.out.println("Datos recibidos para cerrar sesión: " + loginResquestDTO);
        return webClientAutenticacion.post()
                .uri("/cerrar-async") // Se añade la ruta al final de la base URL
                .bodyValue(loginResquestDTO) // Envía el objeto de solicitud de cierre de sesión
                .retrieve()
                .bodyToMono(String.class) // Cambia el tipo según lo que devuelva tu API
                .map(response -> ResponseEntity.ok(response))
                .onErrorResume(e -> {
                    System.err.println("Error en el cierre de sesión: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error en el cierre de sesión"));
                });
    }

    //
    }

