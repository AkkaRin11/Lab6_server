package org.example.repository;

import org.example.model.LabWork;

import java.util.LinkedHashSet;

/**
 *
 * Класс для взаимодейстия со структурой, одной для программы
 *
 */

public interface LabWorkRepository {
    void add(LabWork labWork);

    void clear();

    boolean removeById(int id);

    boolean removeGreater(LabWork labWork);

    boolean save();

    LinkedHashSet<LabWork> getCollection();

    boolean updateById(LabWork labWork, int id);

    String getCollectionInfo();

    boolean isExistById(int id);
}
