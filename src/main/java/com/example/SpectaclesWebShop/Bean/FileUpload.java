package com.example.SpectaclesWebShop.Bean;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
    private MultipartFile file;

    FileUpload(){

    }
    public FileUpload(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
