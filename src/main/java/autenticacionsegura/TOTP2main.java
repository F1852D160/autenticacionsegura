package autenticacionsegura;

public class TOTP2main {
	public static void main(String[] args) throws Exception {
		
		TOTP2 twoFactorAuthUtil = new TOTP2();

		// String base32Secret = twoFactorAuthUtil.generateBase32Secret();
		String base32Secret = "NY4A5CPJZ46LXZCP";//esta es la clave privada

		System.out.println("secret = " + base32Secret);

		// this is the name of the key which can be displayed by the authenticator program
		String keyId = "user@j256.com";
		// generate the QR code
		System.out.println("Image url = " + twoFactorAuthUtil.qrImageUrl(keyId, base32Secret));
		// we can display this image to the user to let them load it into their auth program

		//generamos un codigo para compararlo luego con la entrada del usuario
		String code = twoFactorAuthUtil.generarCodigoTOTP(base32Secret);

		/*
		 * muestra como va cambiando el codigo totp a lo largo del tiempo
		 */
		while (true) {
			long diff = TOTP2.TIME_STEP_SECONDS
					- ((System.currentTimeMillis() / 1000) % TOTP2.TIME_STEP_SECONDS);
			code = twoFactorAuthUtil.generarCodigoTOTP(base32Secret);
			System.out.println("Codigo secreto  = " + code + ", cambiara " + diff + " segundos");
			Thread.sleep(1000);
		}
	}
}
