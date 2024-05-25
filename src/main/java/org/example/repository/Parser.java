package org.example.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.example.controller.ProgramStateController;
import org.example.model.LabWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 *
 * Класс взаимодейстующий с файлом
 *
 */

public class Parser {
    private static Parser instance;
    private final ProgramStateController programStateController;
    private final String fileName;

    public static Parser getInstance(String fileName) {
        if (instance == null) {
            instance = new Parser(fileName);
        }

        return instance;
    }

    public Parser(String fileName) {
        this.fileName = fileName;
        programStateController = ProgramStateController.getInstance();
    }

    public LinkedHashSet<LabWork> read(String fileName) {
        LinkedHashSet<LabWork> labWorks;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());

            String stringJson = "";

            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                stringJson += scanner.nextLine();
            }

            scanner.close();

            labWorks = mapper.readValue(stringJson, new TypeReference<LinkedHashSet<LabWork>>() {
            });

        } catch (JsonProcessingException e) {

            System.out.println("Файл не валиден, файл отчищается и создаётся пустая коллекция");

            try {
                FileWriter writer = new FileWriter(fileName, false);
                writer.write("[]");
                writer.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            labWorks = new LinkedHashSet<>();

        } catch (FileNotFoundException q) {
            if (!programStateController.getIsFileDev()) {
                System.out.println("Файл не существует или к нему нету доступа, создаётся пустая коллекция");
                programStateController.setIsFileValid(false);
            }

            labWorks = new LinkedHashSet<>();

        }

        return labWorks;
    }


    public boolean save(LinkedHashSet<LabWork> labWorks) {
        if (!programStateController.getIsFileValid()) {
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        String jsonStr;

        try {
            jsonStr = mapper.writeValueAsString(labWorks);
        } catch (JsonProcessingException e) {
            return false;
        }

        if (programStateController.getIsFileDev()){
            try {
                File file = new File(fileName + ".txt");
                file.createNewFile();

                FileWriter writer = new FileWriter(fileName + ".txt");
                writer.write(jsonStr);
                writer.close();
            } catch (IOException e) {
                return false;
            }
        } else {
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write(jsonStr);
                writer.close();
            } catch (IOException e) {
                return false;
            }
        }

        return true;
    }

}
