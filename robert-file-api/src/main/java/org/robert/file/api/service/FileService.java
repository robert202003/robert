package org.robert.file.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  String upload(String applicationName, String moduleName, MultipartFile file);

}
