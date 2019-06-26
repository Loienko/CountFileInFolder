package net.ukr.dreamsicle;

import java.io.File;


/**
 * Класс {@link CreateNewThreadForEachFolder} в котором создается новый поток для каждой папки.
 * Подсчет количества файлов выполняется с помощью рекурсивного метода. В случае если найден с файл - он будет подсчитан,
 * в случае с папкой - метод вызовет сам себя и передаст в качестве параметра название поддирректории.
 */
class CreateNewThreadForEachFolder implements Runnable {
    private String pathFolderForCounting;
    private String fileToWriteData;
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
    private void iterateThroughTheContentsOfFolderBySpecifiedPath(File[] pathFolderForCounting) {
        for (File file : pathFolderForCounting) {
            if (file.isDirectory()) {
                iterateThroughTheContentsOfFolderBySpecifiedPath(file.listFiles());
            } else {
                numberOfFilesInTheFolder++;
            }
        }
    }
}