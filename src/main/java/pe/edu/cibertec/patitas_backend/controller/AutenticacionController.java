package pe.edu.cibertec.patitas_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AutenticacionService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;

@RestController//Permitira hacer e llamado como un servicio
@RequestMapping("/autenticacion")
public class AutenticacionController {
    @Autowired
    AutenticacionService autenticacionService;

    //Metodo           Al usar esto se convertira en un json a nivel de servicio
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            //Agregamos esto para comprobar el tiempo que se le asigno al front para que tenga una respuesta
            //Thread.sleep(Duration.ofSeconds(10));
            String[] datosUsuario = autenticacionService.validarUsuario(loginRequestDTO);
            //Imprimimos el array(No necesario)
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));
            //
            if(datosUsuario == null){
                return new LoginResponseDTO("01", "ERROR: Usuario no encontrado", "", "");
            }
            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]);
            //Agregamos el InterruptedException
            // | InterruptedException
        } catch (IOException e) {
            return new LoginResponseDTO("99", "ERROR: Ocurrio un problema", "", "");
        }
    }

}
