package autenticacionsegura;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class Auth2fUtil {
	public static boolean verificar(Date fecha,String secretKeyUsuario,String totpverificar) {
		String steps = "0";
		long T = fecha.getTime();
		steps = Long.toHexString(T).toUpperCase();
		while (steps.length() < 16)
			steps = "0" + steps;
		String totp = TOTPborrar.generateTOTP(secretKeyUsuario,steps, "6");
		if(totp.contentEquals(totpverificar)) {
			return true;
		}
			
		return false;
		
	}
	
	public static String generar(Date fecha,String secretKeyUsuario) {
		String steps = "0";
		int X = 30;
		long T = TimeUnit.MILLISECONDS.toSeconds(fecha.getTime());
		T = (T - 0) / X;
		steps = Long.toHexString(T).toUpperCase();
		while (steps.length() < 16)
			steps = "0" + steps;
		String totp = TOTPborrar.generateTOTP(secretKeyUsuario,steps, "6");
		System.out.println("-----------------------");	
		System.out.println(TOTPborrar.generateTOTP(secretKeyUsuario,steps, "6"));
		System.out.println(TOTPborrar.generateTOTP256(secretKeyUsuario, steps, "6"));
		System.out.println(TOTPborrar.generateTOTP512(secretKeyUsuario, steps, "6"));
		System.out.println("-----------------------");	
		return totp;
		
	}
	
	
	public static String generar2() {
		TOTP2 totp2 = new TOTP2();
		String key = totp2.generarTokensBase32();
		return key;
		
	}
	
	public String generateKeys() {
		final GoogleAuthenticator gAuth = new GoogleAuthenticator();	
		final GoogleAuthenticatorKey googleAuthkey = gAuth.createCredentials();
		String key = googleAuthkey.getKey();
		return key;
	}
	
	//Configure the Google Authenticator App by scanning the following QR code image
	private static String createQRCode(String qrCodeData, String filePath, String charset,
				@SuppressWarnings("rawtypes") Map hintMap, int qrCodeheight, int qrCodewidth)
				throws WriterException, IOException {
			@SuppressWarnings("unchecked")
			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
			File file = new File(filePath);
			MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), file.toPath());
			Encoder encoder = Base64.getEncoder();
			String base64Image = encoder.encodeToString(Files.readAllBytes(file.toPath()));
			// using PosixFilePermission to set file permissions 777
			Set<PosixFilePermission> perms = new HashSet<>();
			// add owners permission
			perms.add(PosixFilePermission.OWNER_READ);
			perms.add(PosixFilePermission.OWNER_WRITE);
			// add group permissions
			perms.add(PosixFilePermission.GROUP_READ);
			perms.add(PosixFilePermission.GROUP_WRITE);
			// add others permissions
			perms.add(PosixFilePermission.OTHERS_READ);
			Files.setPosixFilePermissions(Paths.get(file.toString()), perms);
//			Logger.info("2FA QR code generated");
			System.out.println("2FA qr code generated");
			return "data:image/png;base64," + base64Image;
	}
//	
	
	public static void main(String args[]) throws UnsupportedEncodingException {
	
		Auth2fUtil a2 = new Auth2fUtil();
		System.out.println(a2.generar2());
		//System.out.println(a2.generateKeys());
		
		
//		Date fecha = new Date();
////		String k = new Auth2fUtil().generateKeys();
//		String k = "NBXGRJY6VRKEMTRK";
//		//NHWV MHET NHSY B2GZ
//		String totp = Auth2fUtil.generar(fecha, TOTP.toHexadecimal(k));
//		System.out.println("K="+k);
//		System.out.println("T="+totp);
//		
	}
	
	
//	public boolean performAuthentication(String value, Users user) {
//		final GoogleAuthenticator gAuth = new GoogleAuthenticator();	
//
//        Integer totp = Integer.valueOf((value.equals("") ? "-1" : value));
//        boolean unused = isUnusedPassword(totp, windowSize.get());
//        boolean matches = gAuth.authorize(user.getGoogle2FaAuthKey(), totp);
//        return (unused && matches);
//	}
//	
//	private boolean isUnusedPassword(int password, int windowSize) {
//        long now = new Date().getTime();
//        long timeslotNow = now / KEY_VALIDATION_INTERVAL_MS;
//        int forwardTimeslots = ((windowSize - 1) / 2);
//        long timeslotThen = lastVerifiedTime / KEY_VALIDATION_INTERVAL_MS;
//        if (password != lastUsedPassword || timeslotNow > timeslotThen + forwardTimeslots) {
//            lastUsedPassword = password;
//            lastVerifiedTime = now;
//            return true;
//        }
//        return false;
//}
	
	
}
