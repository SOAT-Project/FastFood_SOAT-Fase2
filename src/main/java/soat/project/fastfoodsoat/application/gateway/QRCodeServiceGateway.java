package soat.project.fastfoodsoat.application.gateway;

public interface QRCodeServiceGateway {
    byte[] generateQRCodeImage(String text, int width, int height);
}
