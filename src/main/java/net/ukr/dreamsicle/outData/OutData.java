package net.ukr.dreamsicle.outData;

import net.ukr.dreamsicle.outData.outputOnDisplay.OutDataToDisplay;
import net.ukr.dreamsicle.outData.writeToFile.OutDataToFile;

/**
 * Класс адптер, служащий для вывода данных на экран и записи данных в файл
 */
public class OutData {
    private String fileToWriteData;
    private String pathFolderForCounting;
    private int numberOfFilesInTheFolder;
    private OutDataToFile outDataToFile;
    private OutDataToDisplay outDataToDisplay;


    public OutData(String fileToWriteData, String pathFolderForCounting, int numberOfFilesInTheFolder) {
        this.fileToWriteData = fileToWriteData;
        this.pathFolderForCounting = pathFolderForCounting;
        this.numberOfFilesInTheFolder = numberOfFilesInTheFolder;
        outDataToFile = new OutDataToFile();
        outDataToDisplay = new OutDataToDisplay();
    }

    public String getFileToWriteData() {
        return fileToWriteData;
    }

    public String getPathFolderForCounting() {
        return pathFolderForCounting;
    }

    public int getNumberOfFilesInTheFolder() {
        return numberOfFilesInTheFolder;
    }

    public synchronized void outDataFromApp() {
        outDataToFile.writeToFileData(getFileToWriteData(), getPathFolderForCounting(), getNumberOfFilesInTheFolder());
        outDataToDisplay.outputDataToDisplay(getPathFolderForCounting(), getNumberOfFilesInTheFolder());
    }
}
