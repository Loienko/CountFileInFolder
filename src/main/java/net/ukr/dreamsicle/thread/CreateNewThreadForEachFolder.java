package net.ukr.dreamsicle.thread;

import net.ukr.dreamsicle.App;
import net.ukr.dreamsicle.outData.OutData;

import java.io.File;
import java.util.Arrays;


/**
 * Класс {@link CreateNewThreadForEachFolder} в котором создается новый поток для каждой папки.
 * Подсчет количества файлов выполняется с помощью рекурсивного метода. В случае если найден с файл - он будет подсчитан,
 * в случае с папкой - метод вызовет сам себя и передаст в качестве параметра название поддирректории.
 */
public class CreateNewThreadForEachFolder extends Thread implements CountFolder {

    private String pathFolderForCounting;
    private String fileToWriteData;
    //
    private int numberOfFilesInTheFolder;

    /**
     * Входящие параметры для подсчета количества файлов в папке с класса {@link App}
     *
     * @param pathFolderForCounting
     * @param fileToWriteData
     */
    public CreateNewThreadForEachFolder(String pathFolderForCounting, String fileToWriteData) {
        this.pathFolderForCounting = pathFolderForCounting;
        this.fileToWriteData = fileToWriteData;
    }

    @Override
    public void run() {
        iterateThroughTheContentsOfFolderBySpecifiedPath(new File(pathFolderForCounting).listFiles());
        new OutData(fileToWriteData, pathFolderForCounting, numberOfFilesInTheFolder).outDataFromApp();
    }

    /**
     * Рекурсивный метод  который выпоняет прдсчет количества файлов. В случае если найден с файл - он будет подсчитан,
     * в случае с папкой - метод вызовет сам себя и передаст в качестве параметра название поддирректории.
     *
     * @param pathFolderForCounting список файлов в подпапке
     */
    @Override
    public void iterateThroughTheContentsOfFolderBySpecifiedPath(File... pathFolderForCounting) {
        if (!Thread.currentThread().isInterrupted()) {
            Arrays.stream(pathFolderForCounting).forEach(file -> {
                if (file.isDirectory()) {
                    iterateThroughTheContentsOfFolderBySpecifiedPath(file.listFiles());
                } else {
                    numberOfFilesInTheFolder++;
                }
            });
        }
    }
}