package org.example.command;

import org.example.controller.ProgramStateController;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

/**
 *
 * Команда для сохранения коллекции
 *
 */

public class Save extends Command {
    private final LabWorkService labWorkService;

    public Save() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "save";
        description = "Сохраняет элементы колекции в заданный файл";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        if (labWorkService.save()){
            if (ProgramStateController.getInstance().getIsFileDev()){
                return "Коллекция успешно сохранена в дефолтный файл";
            } else {
                return "Коллекция успешно сохранена";
            }
        } else {
            return "Файл не существует или к нему нету доступа, сохранение невозможно";
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
