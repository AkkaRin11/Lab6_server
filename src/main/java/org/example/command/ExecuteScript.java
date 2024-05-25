package org.example.command;

import org.example.controller.CommandController;
import org.example.controller.ProgramStateController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


/**
 *
 * Команда воплоняющая скрипт по имени
 *
 */
public class ExecuteScript extends Command {
    private final ProgramStateController programStateController;
    private final org.example.command_support.History history;

    public ExecuteScript() {
        programStateController = ProgramStateController.getInstance();
        history = org.example.command_support.History.getInstance();

        argSize = 1;
        name = "execute_script";
        description = "Выполнить скрипт";
    }

    @Override
    public String execute(Object object, String... args) {

        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }


        for (String string: (List<String>)object) {

            String[] str = string.replaceAll("\n", "").split("\\s+");

            String[] ar = new String[str.length - 1];
            System.arraycopy(str, 1, ar, 0, str.length - 1);


            if (CommandController.isValidCommand(str[0])){
                CommandController.getCommandByName(str[0]).execute(ar);
            }

        }

        return "Скрипт завершил своё выполнение";
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
