package autenticacionsegura;
/**
 * 
 * @author contenidos
 *
 */
public interface AuthTOTP {

	public String generarTokenUsuario();

	public String generarQrCodeImage(String keyId, String tokenUsuario);

	public boolean isValidCodigo(String secretKey, String codigo);

	public void mostrarVentanaTOTP();

}
