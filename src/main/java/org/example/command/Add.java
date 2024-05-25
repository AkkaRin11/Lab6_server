package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

/**
 *
 * Команда добавлющая элемент
 *
 */

public class Add extends Command {
    private final LabWorkService labWorkService;

    public Add() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "add";
        description = "Добавляет элемент в колекцию";
    }

    @Override
    public String execute(Object object, String... args) {

        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        LabWork lb = (LabWork) object;


        labWorkService.add(lb);

        return "Коллекция успешно добавлена";

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
