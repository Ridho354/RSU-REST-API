package com.rsu.latihanrsu.controller;

import com.rsu.latihanrsu.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rsu/api/files")
public class FileController {
    private final StorageService storageService;

    @GetMapping("/{folder}/{filename}")
    public ResponseEntity<?> downloadFile(
            @PathVariable String folder, @PathVariable String filename) {
        Resource resource = storageService.downloadFile(folder + "/" + filename);
        String contentDisposition = String.format("inline; filename=\"%s\"", resource.getFilename()); // inline karena image

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource); 
    }

    
}
