package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

import java.util.LinkedHashSet;

/**
 *
 * Команда возвращающая все элементы содержащие данную строку в имени
 *
 */

public class FilterContainsName extends Command {
    private final LabWorkService labWorkService;

    public FilterContainsName() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 1;
        name = "filter_contains_name";
        description = "Выводит элемены с вхождением подстроки в имени";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        LinkedHashSet<LabWork> filteredCollection = labWorkService.getCollectionByContainsName(args[0]);

        String stringFilteredCollection = "";

        for (LabWork labWork: filteredCollection){
            stringFilteredCollection += labWork;
        }

        return stringFilteredCollection;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
