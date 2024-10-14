package pe.edu.cibertec.patitas_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AutenticacionService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {
    //Implmentamos los metodos de la interface

    //Hacemos referencia a nuestro archivo
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {
        //Definimos la variable a retornar
        String[] datosUsuario = null;

        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))){
            //Implementamos la lectura del archivo
            String line;
            while ((line = br.readLine()) != null){
                String[] datos = line.split(";");
                if(loginRequestDTO.tipoDocumento().equals(datos[0]) &&
                        loginRequestDTO.numeroDocumento().equals(datos[1]) &&
                        loginRequestDTO.password().equals(datos[2])){
                    //Inicializamos el array de datosUsuario
                    datosUsuario = new String[4];
                    datosUsuario[0] = datos[3]; //Recuperar Nombre
                    datosUsuario[1] = datos[4]; //Recuperar Correo
                    //Se agrego para recuperar el tipoDocumento y numeroDocumento
                    datosUsuario[2] = datos[0];
                    datosUsuario[3] = datos[1];
                    break;
                }
            }

        } catch (IOException e){
            datosUsuario = null;
            throw new IOException(e);
        }

        return datosUsuario;
    }

    @Override
    public void cierreSesion(String tipoDocumento, String numeroDocumento) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaHora = LocalDateTime.now().format(formatter);
        String registro = tipoDocumento + ";" + numeroDocumento + ";" + fechaHora;

        String path = "src/main/resources/cerrar_sesion.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){
            bw.write(registro);
            bw.newLine(); //Nueva linea para cada registro
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
