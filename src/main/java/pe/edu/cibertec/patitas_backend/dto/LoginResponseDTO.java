package pe.edu.cibertec.patitas_backend.dto;

public record LoginResponseDTO(String code, String msj, String user,String userGmail, String tipoDocumento, String numeroDocumento) {
}
