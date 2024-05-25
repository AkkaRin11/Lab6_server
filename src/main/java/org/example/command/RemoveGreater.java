package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

/**
 *
 * Команда удаляющая все элементы больше данного
 *
 */

public class RemoveGreater extends Command {
    private final LabWorkService labWorkService;
    public RemoveGreater() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "remove_greater";
        description = "Удаляет все элементы элементы из коллекции, перевыщающие заданный";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return"Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        LabWork labWork = (LabWork) object;

        boolean flag = labWorkService.removeGreater(labWork);

        if (flag){
            return "Подходящие по условию объекты были удалены";
        } else {
            return "Подходящие по условию объекты не были найдены";
        }

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
