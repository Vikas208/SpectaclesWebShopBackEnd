package com.example.SpectaclesWebShop.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SpectaclesWebShop.Info.Code;
import com.example.SpectaclesWebShop.Secret;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    private static Cloudinary getCloudinaryClient() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", Secret.CLOUD_NAME,
                "api_key", Secret.API_KEY,
                "api_secret", Secret.API_SECRET,
                "secure", true));
    }

    public HashMap<String, Object> uploadSingalImage(MultipartFile file, String filePath) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map<?, ?> params = ObjectUtils.asMap(
                    "public_id", filePath,
                    "overwrite", true,
                    "secure", true);
            Map<?, ?> uploadResult = getCloudinaryClient().uploader().upload(uploadedFile, params);
            HashMap<String, Object> obj = new HashMap<String, Object>();
            obj.put("url", uploadResult.get("url"));
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] uploadMultipleImages(List<MultipartFile> file, long id) {
        try {
            String[] links = new String[file.size()];
            for (int i = 0; i < file.size(); ++i) {
                File uploadedFile = convertMultiPartToFile(file.get(i));
                Map<?, ?> params = ObjectUtils.asMap(
                        "public_id", "ProductCarousel/product_" + uploadedFile.getName() + "_" + id,
                        "overwrite", true,
                        "secure", true);
                Map<?, ?> uploadResult = getCloudinaryClient().uploader().upload(uploadedFile, params);
                links[i] = uploadResult.get("url").toString();
            }
            return links;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] uploadCarousel(List<MultipartFile> file) {
        try {
            String[] links = new String[file.size()];
            for (int i = 0; i < file.size(); ++i) {
                File uploadedFile = convertMultiPartToFile(file.get(i));

                Map<?, ?> params = ObjectUtils.asMap(
                        "public_id", "Carousel/" + uploadedFile.getName(),
                        "overwrite", true,
                        "secure", true);
                Map<?, ?> uploadResult = getCloudinaryClient().uploader().upload(uploadedFile, params);
                // System.out.println(uploadResult.toString());
                links[i] = uploadResult.get("url").toString();
            }
            return links;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteImage(String filepath) {

        try {
            Map<?, ?> params = ObjectUtils.asMap(
                    "public_id", filepath,
                    "invalidate", true,
                    "secure", true);
            getCloudinaryClient().uploader().destroy(filepath, params);
            return Code.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code.ERROR_CODE;
    }

    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
