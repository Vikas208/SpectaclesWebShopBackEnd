package com.example.SpectaclesWebShop.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SpectaclesWebShop.Bean.FileUpload;
import com.example.SpectaclesWebShop.Secret;
import com.example.SpectaclesWebShop.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/cloud")
public class ImageUploadController {

    @Autowired
    CloudinaryService cloudinaryService;

    private static Cloudinary getCloudinaryClient() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", Secret.CLOUD_NAME,
                "api_key", Secret.API_KEY,
                "api_secret", Secret.API_SECRET,
                "secure", true));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
            @RequestParam(value = "p_id", defaultValue = "0", required = false) long p_id) {
        try {
            HashMap<String, Object> obj = cloudinaryService.uploadSingalImage(file, "Products_Images/product_" + p_id);
            if (obj != null) {
                return ResponseEntity.ok(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/uploadProductCarousel", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> uploadProductCarousel(@RequestParam("file") MultipartFile[] file,
            @RequestParam(value = "id", defaultValue = "0", required = false) long id) {
        try {
            String[] links = cloudinaryService.uploadMultipleImages(List.of(file), id);
            if (links != null) {
                return ResponseEntity.ok(links);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/uploadCarousel", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> uploadCarousel(@RequestParam List<MultipartFile> file) {
        try {
            String[] links = cloudinaryService.uploadCarousel(file);
            if (links != null) {
                return ResponseEntity.ok(links);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/uploadLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadLogo(@RequestParam MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map<?, ?> params = ObjectUtils.asMap(
                    "public_id", "ShopDetails/logo",
                    "overwrite", true,
                    "secure", true);
            Map<?, ?> uploadResult = getCloudinaryClient().uploader().upload(uploadedFile, params);
            HashMap<String, Object> obj = new HashMap<String, Object>();
            obj.put("url", uploadResult.get("url"));
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
