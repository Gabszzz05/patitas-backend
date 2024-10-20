package pe.edu.cibertec.patitas_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.patitas_backend.dto.LogOutResponseDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AutenticacionService;
import pe.edu.cibertec.patitas_backend.session.UserActual;

import java.io.IOException;
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
            String[] datosUsuario = autenticacionService.validarUsuario(loginRequestDTO);
            //Imprimimos el array(No necesario)
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));

            //Thread.sleep(Duration.ofSeconds(2000));
            //
            if(datosUsuario == null){
                return new LoginResponseDTO("01", "ERROR: Usuario no encontrado", "", "", "", "");
            }

            //Almacenar datos
            UserActual.setUserData(datosUsuario[2], datosUsuario[3]);

            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1], datosUsuario[2], datosUsuario[3]);
            //Agregamos el InterruptedException
            // | InterruptedException
        } catch (IOException e) {
            return new LoginResponseDTO("99", "ERROR: Ocurrio un problema", "", "", "", "");
        }/* catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }

    //Cierre de Sesion
    @PostMapping("/logout")
    public LogOutResponseDTO logout(){

        String tipoDocumento = UserActual.getTipoDocumento();
        String numeroDocumento = UserActual.getNumeroDocumento();

        System.out.println("Tipo Documento :" + tipoDocumento);
        System.out.println("Numero Documento" + numeroDocumento);

        //Verificar
        if(tipoDocumento != null && numeroDocumento != null){
            try {
                autenticacionService.cierreSesion(tipoDocumento, numeroDocumento);
                UserActual.clear();
                return new LogOutResponseDTO("00", "Cierre de Sesion correcto");
            }catch (IOException e){
                return new LogOutResponseDTO("02", "ERROR: Cierre de Sesion incorrecto" + e.getMessage());
            }
        }else {
            return new LogOutResponseDTO("99", "No hay sesion activa");
        }
    }
}
