package com.example.SpectaclesWebShop.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SpectaclesWebShop.Bean.FileUpload;
import com.example.SpectaclesWebShop.Secret;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloud")
public class ImageUploadController {
    private static Cloudinary getCloudinaryClient() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", Secret.CLOUD_NAME,
                "api_key", Secret.API_KEY,
                "api_secret", Secret.API_SECRET,
                "secure", true));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@ModelAttribute FileUpload file, @RequestParam("p_id") long p_id) {
        try {

            File uploadedFile = convertMultiPartToFile(file.getFile());
            Map<?, ?> params = ObjectUtils.asMap(
                    "public_id", "Products_Images/product_" + p_id,
                    "overwrite", true,
                    "secure", true);
            Map<?, ?> uploadResult = getCloudinaryClient().uploader().upload(uploadedFile, params);

            return new ResponseEntity<>(uploadResult.get("url").toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
