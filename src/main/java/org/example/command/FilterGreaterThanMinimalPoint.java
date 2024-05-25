package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.NameUtil;

import java.util.LinkedHashSet;

import static org.example.utils.Validation.checkIntNumber;


/**
 *
 * Команда возвращающая все элементы коллекции у которых minimal_point больше заданного зачения
 *
 */
public class FilterGreaterThanMinimalPoint extends Command {
    private final LabWorkService labWorkService;

    public FilterGreaterThanMinimalPoint() {
        NameUtil nameUtil = NameUtil.getInstance();
        labWorkService = new LabWorkServiceImpl(nameUtil.getName());

        argSize = 1;
        name = "filter_greater_than_minimal_point";
        description = "Выводит элемены со значением поля minimalPoint больше заданного";
    }

    @Override
    public String execute(Object object, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;

        }

        if (checkIntNumber(args[0])) {
            int minimalPoint = Integer.parseInt(args[0]);

            LinkedHashSet<LabWork> filteredCollection =
                    labWorkService.getCollectionByGreaterMinimalPoint(minimalPoint);

            String stringFilteredCollection = "";

            for (LabWork labWork: filteredCollection){
                stringFilteredCollection += labWork;
            }

            return stringFilteredCollection;
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
