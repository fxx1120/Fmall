package com.fmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by fxx028 on 2019/1/9.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
