package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

/**
 *
 * Команда вывода всех элементов коллекции
 *
 */

public class Show extends Command {
    private final LabWorkService labWorkService;

    public Show() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "show";
        description = "Выводит все элементы коллекции";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        String stringCollection = "";

        for (LabWork labWork: labWorkService.getCollection()){
            stringCollection += labWork;
        }

        return stringCollection;
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
