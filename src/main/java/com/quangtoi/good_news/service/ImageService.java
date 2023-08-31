package com.quangtoi.good_news.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile imageFile);
}
