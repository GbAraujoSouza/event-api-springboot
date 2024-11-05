package com.gbaraujosouza.api.service;

import com.gbaraujosouza.api.domain.event.Event;
import com.gbaraujosouza.api.domain.event.EventRequestDTO;
import com.gbaraujosouza.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private EventRepository eventRepository;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if (data.image() != null) {
            imgUrl = this.uploadImageToS3(data.image());
        }

        Event event = new Event();
        event.setTitle(data.title());
        event.setDescription(data.description());
        event.setEventUrl(data.eventUrl());
        event.setDate(new Date(data.date()));
        event.setImgUrl(imgUrl);
        event.setRemote(data.remote());

        eventRepository.save(event);

        return event;
    }

    private String uploadImageToS3(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
        try {
            File newFile = this.convertMultipartFileToFile(multipartFile);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            RequestBody requestBody = RequestBody.fromFile(newFile);
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);
            newFile.delete();
            return getPublicUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String getPublicUrl(String keyName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, awsRegion, keyName);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename(), "cannot find file"));
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }
}
