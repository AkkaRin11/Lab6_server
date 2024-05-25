package org.example.repository;

import org.example.model.LabWork;

import java.util.LinkedHashSet;

/**
 *
 * Реализация LabWorkRepository
 *
 */

public class LabWorkRepositoryImpl implements LabWorkRepository {
    private static LabWorkRepositoryImpl instance;

    private final LinkedHashSet<LabWork> labWorks;

    private final Parser parser;

    public static LabWorkRepositoryImpl getInstance(String fileName) {
        if (instance == null) {
            instance = new LabWorkRepositoryImpl(fileName);
        }

        return instance;
    }

    private LabWorkRepositoryImpl(String fileName) {
        parser = Parser.getInstance(fileName);
        labWorks = parser.read(fileName);
    }

    @Override
    public void add(LabWork labWork) {
        labWorks.add(labWork);
    }

    @Override
    public void clear() {
        labWorks.clear();
    }

    @Override
    public boolean removeById(int id) {
        LabWork labWork = null;

        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                labWork = to;
                flag = true;
                break;
            }
        }

        labWorks.remove(labWork);

        return flag;
    }

    @Override
    public boolean removeGreater(LabWork labWork) {
        LinkedHashSet<LabWork> lh = new LinkedHashSet<>();

        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getMinimalPoint() > labWork.getMinimalPoint()) {
                lh.add(to);
            }
        }

        for (LabWork to : lh) {
            labWorks.remove(to);
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean save() {
        return parser.save(labWorks);
    }

    @Override
    public LinkedHashSet<LabWork> getCollection() {
        return labWorks;
    }

    @Override
    public boolean updateById(LabWork labWork, int id) {
        LabWork lb = null;
        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                flag = true;
                lb = to;
                break;
            }
        }

        labWorks.remove(lb);
        labWork.setId(id);
        labWorks.add(labWork);

        return flag;
    }

    @Override
    public String getCollectionInfo() {
        return "LinkedHashSet, size: " + labWorks.size();
    }

    public boolean isExistById(int id){
        boolean flag = false;

        for (LabWork to : labWorks) {
            if (to.getId() == id) {
                flag = true;
                break;
            }
        }

        return flag;
    }

}
