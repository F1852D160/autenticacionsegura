package autenticacionsegura.impl;

import java.security.GeneralSecurityException;

import autenticacionsegura.AuthTOTP;
import autenticacionsegura.TOTP2;

public class AuthUtilImpl implements AuthTOTP {
	TOTP2 totp = new TOTP2();
	@Override
	public String generarTokenUsuario() {
		return totp.generarTokensBase32();
	}

	@Override
	public boolean isValidCodigo(String secretKey,String codigo) {
		String cod0 = "";
		if(codigo == null)return false;
		try {
			cod0 = totp.generarCodigoTOTP(secretKey);
			return codigo.equals(cod0);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

	@Override
	public String generarQrCodeImage(String keyId, String tokenUsuario) {
		return totp.qrImageUrl(keyId, tokenUsuario);
	}

	
	@Override
	public void mostrarVentanaTOTP() {
		// TODO Auto-generated method stub
		
	}
	
}
