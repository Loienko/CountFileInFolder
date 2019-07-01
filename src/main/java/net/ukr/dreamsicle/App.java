package net.ukr.dreamsicle;

import net.ukr.dreamsicle.listener.KeyListenerForPushEscapeButton;
import net.ukr.dreamsicle.thread.CreateNewThreadForEachFolder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс App позволяющий найти все существующие файлы и сделать их подсчет в папках,
 * которые приходят первым аргументом командной строки. Для каждого пути создается
 * </p>
 * В конечном итоге программа выводит данные в консоль
 * {@link net.ukr.dreamsicle.outData.outputOnDisplay.OutDataToDisplay}
 * и производит запись в файл {@link net.ukr.dreamsicle.outData.writeToFile.OutDataToFile}
 * согласно формату - выходной файл должен быть в формате CSV с разделителем точка с запятой (;).
 * Первая колонка – это исходный путь из входного файла, вторая – количество файлов в папке.
 * Вывод на экран должен быть представлен в табличном виде. Первая колонка – это порядковый номер записи,
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    public static int indexFileNumberForDisplay = 0;
    public static boolean isRunning = true;

    public static void main(String[] args) {

        System.out.println("------------ WELCOME TO COUNTING FILES ------------");
        System.out.println("Press <Enter> to exit or wait result for all path ->");

        String firstFileWithInputPathsFolder = args[0];
        String secondFileToWriteData = args[1];

        List<String> listOfInputPathsForCount = new ArrayList<>();
        List<Thread> listForCheckThreadForWorking = new ArrayList<>();

        KeyListenerForPushEscapeButton keyListenerForPushEscapeButton = new KeyListenerForPushEscapeButton();

        if (args == null && !new File(firstFileWithInputPathsFolder).isFile() && !new File(secondFileToWriteData).isFile()) {
            LOGGER.info("Not correct input data");
            throw new InvalidParameterException("Parameter is not a file. File path should be inserted");
        }


        try {
            new PrintWriter(secondFileToWriteData).close();
            Files.readAllLines(Paths.get(firstFileWithInputPathsFolder)).forEach(listOfInputPathsForCount::add);
        } catch (IOException e) {
            LOGGER.info("Problem with read the file", e);
        }

        listOfInputPathsForCount.forEach(path -> {
            Thread thread = new Thread(new CreateNewThreadForEachFolder(path, secondFileToWriteData));
            thread.start();
            listForCheckThreadForWorking.add(thread);
        });

        /**
         *
         */
        while (isRunning) {
//            Thread.sleep(1000);
            keyListenerForPushEscapeButton.getKey();
            if (!isRunning) {
                listForCheckThreadForWorking.forEach(thread -> {
                    if (!thread.isInterrupted()) {
                        thread.interrupt();
                        isRunning = false;
                        keyListenerForPushEscapeButton.getUnregisterNativeHook();

                    }
                });

            }

            if (isRunning) {
                ArrayList<Object> checkThreadForStopWorkingAppWithoutPushEscape = new ArrayList<>();
                listForCheckThreadForWorking.forEach(thread -> {
                    if (thread.getState() == Thread.State.TERMINATED) {
                        checkThreadForStopWorkingAppWithoutPushEscape.add(thread.getName());
                        if (checkThreadForStopWorkingAppWithoutPushEscape.size() == listForCheckThreadForWorking.size()) {
                            isRunning = false;
                            keyListenerForPushEscapeButton.getUnregisterNativeHook();
                        }
                    }
                });
            }
        }

    }
}
