package org.example.service;

import org.example.model.LabWork;
import org.example.repository.LabWorkRepository;
import org.example.repository.LabWorkRepositoryImpl;

import java.util.LinkedHashSet;


/**
 *
 * Реализация LabWorkService
 *
 */

public class LabWorkServiceImpl implements LabWorkService {
    private final LabWorkRepository labWorkRepository;

    public LabWorkServiceImpl(String fileName) {
        labWorkRepository = LabWorkRepositoryImpl.getInstance(fileName);
    }

    @Override
    public void add(LabWork lb) {
        labWorkRepository.add(lb);
    }

    @Override
    public void clear() {
        labWorkRepository.clear();
    }

    @Override
    public boolean removeById(int id) {
        return labWorkRepository.removeById(id);
    }

    @Override
    public boolean removeGreater(LabWork lb) {
        return labWorkRepository.removeGreater(lb);
    }

    @Override
    public boolean save() {
        return labWorkRepository.save();
    }

    @Override
    public LinkedHashSet<LabWork> getCollection() {
        return labWorkRepository.getCollection();
    }

    @Override
    public boolean addIfMax(LabWork labWork) {
        long max = Integer.MIN_VALUE;

        var collection = getCollection();

        for (LabWork to : collection) {
            max = Math.max(max, to.getMinimalPoint());
        }

        if (labWork.getMinimalPoint() > max) {
            add(labWork);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCollectionInfo() {
        return labWorkRepository.getCollectionInfo();
    }

    @Override
    public LinkedHashSet<LabWork> getCollectionByContainsName(String nameFilter) {
        var collection = getCollection();

        LinkedHashSet<LabWork> filteredCollection = new LinkedHashSet<>();

        for (LabWork to : collection) {
            if (to.getName().contains(nameFilter)) {
                filteredCollection.add(to);
            }
        }

        return filteredCollection;
    }

    @Override
    public LinkedHashSet<LabWork> getCollectionByGreaterMinimalPoint(int minimalPoint) {
        var collection = getCollection();

        LinkedHashSet<LabWork> filteredCollection = new LinkedHashSet<>();

        for (LabWork to : collection) {
            if (to.getMinimalPoint() > minimalPoint) {
                filteredCollection.add(to);
            }
        }

        return filteredCollection;
    }

    @Override
    public boolean updateById(LabWork labWork, int id) {
        return labWorkRepository.updateById(labWork, id);
    }

    public long getSumOfAveragePoint() {
        long sum = 0;

        var collection = getCollection();

        for (LabWork to : collection) {
            sum += to.getAveragePoint();
        }

        return sum;
    }

    public boolean isExistById(int id){
        return labWorkRepository.isExistById(id);
    }

}
