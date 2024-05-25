package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

import static org.example.utils.Validation.checkIntNumber;

/**
 *
 * Команда обновления элемента по id
 *
 */

public class Update extends Command {
    private final LabWorkService labWorkService;

    public Update() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 1;
        name = "update";
        description = "Обновление элемента по id";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        if (checkIntNumber(args[0])) {

            int id = Integer.parseInt(args[0]);

            if(!labWorkService.isExistById(id)){
                return "Элемента с таким id не существует";
            }

            LabWork labWork = (LabWork) object;

            boolean result = labWorkService.updateById(labWork, id);

            if (result) {
                return "Объект успешно обновлённ";
            } else {
                return "Элемента с таким id не существует";
            }

        } else {
            return "Введённый аргумент не является целым числом";
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
