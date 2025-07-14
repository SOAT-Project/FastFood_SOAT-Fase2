package soat.project.fastfoodsoat.infrastructure.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.gateway.QRCodeServiceGateway;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class ZXingQRCodeImpl implements QRCodeServiceGateway {

    @Override
    public byte[] generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        }   catch (Exception error) {
            throw new RuntimeException("Error generating QR code", error);
        }
    }
}
