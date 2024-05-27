package funimage.api.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.io.File;
import org.springframework.util.FileCopyUtils;



@SpringBootApplication
public class ImageWatermarkApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(ImageWatermarkApplicationController.class, args);
    }

}

@RestController

class ImageController {

    private static final String IMAGE_DIRECTORY = "images";

    @PostMapping("/api/images/watermark")
    public ResponseEntity<ImageResponse> watermarkImage(@RequestBody ImageRequest imageRequest) {
        try {
            byte[] imageData = Base64.getDecoder().decode(imageRequest.getBase64Image());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(bis);
            bis.close();

            BufferedImage watermarkedImage = applyWatermark(originalImage, imageRequest.getWatermarkText(), new Point(50, 50));

            String fileName = UUID.randomUUID().toString() + ".png"; 
            File imageFile = new File(IMAGE_DIRECTORY, fileName);
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs(); 
            }
            ImageIO.write(watermarkedImage, "png", imageFile);

            String staticDirectory = "src/main/resources/static/images/";
            File staticImageFile = new File(staticDirectory, fileName);
            if (!staticImageFile.getParentFile().exists()) {
                staticImageFile.getParentFile().mkdirs(); 
            }
            FileCopyUtils.copy(imageFile, staticImageFile);

            String imageUrl = "images/" + fileName;

            ImageResponse response = new ImageResponse(imageRequest.getBase64Image(), imageUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ImageResponse(null, null));
        }
    }


    private BufferedImage applyWatermark(BufferedImage originalImage, String watermarkText, Point position) {
        BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = watermarkedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString(watermarkText, position.x, position.y);
        g2d.dispose();
        return watermarkedImage;
    }
}

class ImageRequest {
    private String base64Image;
    private String watermarkText;

    // Getters e Setters
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }
}


class ImageResponse {
    private String base64Image;
    private String imageUrl;

    public ImageResponse(String base64Image, String imageUrl) {
        this.base64Image = base64Image;
        this.imageUrl = imageUrl;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

