package org.example.service;

import org.example.model.LabWork;

import java.util.LinkedHashSet;

/**
 *
 * Слой-менеждер для разделения логики взаимодействия с данными от команд
 *
 */

public interface LabWorkService {

    void add(LabWork lb);

    void clear();

    boolean removeById(int id);

    boolean removeGreater(LabWork lb);

    boolean save();

    LinkedHashSet<LabWork> getCollection();

    boolean addIfMax(LabWork labWork);

    String getCollectionInfo();

    LinkedHashSet<LabWork> getCollectionByContainsName(String nameFilter);

    LinkedHashSet<LabWork> getCollectionByGreaterMinimalPoint(int minimalPoint);

    boolean updateById(LabWork labWork, int id);

    long getSumOfAveragePoint();

    boolean isExistById(int id);
}
