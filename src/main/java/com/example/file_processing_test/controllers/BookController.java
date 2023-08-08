package com.example.file_processing_test.controllers;

import com.example.file_processing_test.services.XmlFileProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
public class BookController {

    @Value("${fileprocessing.dir-path.criticalxml}")
    private String critical_test;

    @Value("${fileprocessing.dir-path.non-criticalxml}")
    private String non_critical_test;

    @Autowired
    private XmlFileProcessing xmlFileProcessing;

    @Scheduled(cron = "${fileProcessing.readFile.time}")
    public void getAllTheFileNames() throws IOException, JAXBException {
        xmlFileProcessing.getAllTheFileNames(critical_test);
    }

    @Scheduled(cron = "${fileProcessing.readFile.time}")
    public void processXmlFileForNonCritical() throws IOException, JAXBException {
        xmlFileProcessing.getAllTheFileNames(non_critical_test);
    }
}
