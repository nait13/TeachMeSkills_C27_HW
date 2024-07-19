package com.lesson46.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController
{
    private String directory = "D:\\";

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile)
    {
        try
        {
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
            System.out.println(resultStringBuilder);

            Files.copy(multipartFile.getInputStream(), Paths.get(directory).resolve(multipartFile.getOriginalFilename()));
            return ResponseEntity.ok("Upload file successfully: " + multipartFile.getOriginalFilename());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Could not upload the file:" + multipartFile.getOriginalFilename());
        }
    }

    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename")String filename)
    {
        Path file = Paths.get(directory).resolve(filename);
        Resource resource = null;
        try
        {
            resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable())
            {
                throw new RuntimeException("Could not read the file.");
            }
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("Error:" + e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }
}

