package com.example.file_processing_test.services;

import com.example.file_processing_test.dtos.Catalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.*;

@Slf4j
@Service
public class XmlFileProcessing {

   /* @Value("${fileprocessing.dir-path.criticalxml}")
    private String critical_test;
*/
    @Value("${fileprocessing.dir-path.archievexml}")
    private String archieve_test;

    public void getAllTheFileNames(String critical_test) throws IOException {
        File file = new File( critical_test);
        log.info("Looking names of files in the directory :{}", file.getName());

        String[] fileNames = file.list();

        for (int i = 0; i < fileNames.length; i++) {
            String currNameOfTheFile = fileNames[i];
            log.info("File name is :{} ", currNameOfTheFile);
        }
        //file-name and it creation-time table.
        Map<String, FileTime> fileNamesAndItsCreationTimeMap = new HashMap<>();
        for (int i = 0; i < fileNames.length; i++) {
            String currFileName = fileNames[i];

            Path path = Paths.get(critical_test + "\\" + currFileName);

            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
            log.info("Creation time of {} is {} ", currFileName, creationTime);

            fileNamesAndItsCreationTimeMap.put(currFileName, creationTime);
        }
        log.info(" file-name and its creation time is : {} " ,fileNamesAndItsCreationTimeMap);
        Set<Map.Entry<String, FileTime>> set = fileNamesAndItsCreationTimeMap.entrySet();
        List<Map.Entry<String, FileTime>> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, FileTime>>() {
            @Override
            public int compare(Map.Entry<String, FileTime> o1, Map.Entry<String, FileTime> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        processXmlFileAndPushToKafka(list, critical_test);

    }

    private void processXmlFileAndPushToKafka(List<Map.Entry<String, FileTime>> list,String critical_test ) throws IOException {
        for (int i = 0; i< list.size(); i++){
            Map.Entry<String, FileTime> curr = list.get(i);
            String fileName = curr.getKey();

            Catalog catalog = convertXmlFileToPojo(critical_test+"\\"+fileName);
            log.info("Converted xml to POJO : {} with size : {}",catalog.toString(), catalog.getBook().size());

            sendToKafkaTopic(catalog);
            moveFileFromCriticalToArchiveDirectory(fileName, critical_test);
        }
    }

    private void moveFileFromCriticalToArchiveDirectory(String fileName, String critical_test) throws IOException {
        String source = critical_test+"\\"+fileName;
        String destination = archieve_test+"\\"+fileName;
        Files.move(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        log.info("File moved successfully");
    }

    private void sendToKafkaTopic(Catalog catalog) {
        log.info("logging to the kafka : {}", catalog.toString());
    }

    private Catalog convertXmlFileToPojo(String path) {
        try {
            File file = new File(path);
            log.info("Going to process {}", file.getName());
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Catalog) unmarshaller.unmarshal(file);
        } catch (Exception ex) {
            log.error("Error happened while reading the file and pushing to the kafka");
            return null;
        }
    }
}
