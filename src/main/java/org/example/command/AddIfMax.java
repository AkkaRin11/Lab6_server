package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;


/**
 *
 * Команда добавляющая элемент если он наибольшй
 *
 */
public class AddIfMax extends Command {
    private final LabWorkService labWorkService;

    public AddIfMax() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 0;
        name = "add_if_max";
        description = "Добавить элемент если он наибольший из всей колекции";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        if (object == null){
            return "Неверные аргументы";
        }

        LabWork labWork = (LabWork) object;

        boolean res = labWorkService.addIfMax(labWork);

        if (res) {
            return "Элемент наибольший, добавлен";
        } else {
            return "Элемент не наибольший, не добавлен";
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
