package org.example.command;

import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

/**
 *
 * Команда возвращающая информацию о коллекции
 *
 */
public class Info extends Command {
    private final LabWorkService labWorkService;

    public Info() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "info";
        description = "Выводит информацию о колекции";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }


        return labWorkService.getCollectionInfo();
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
