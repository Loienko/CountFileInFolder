package net.ukr.dreamsicle.outData.outputOnDisplay;

import net.ukr.dreamsicle.App;

/**
 *
 */
public class OutDataToDisplay implements DisplayData {
    @Override
    public synchronized void outputDataToDisplay(String path, int countFile) {
        String format = String.format("%d | %-5s | %-10s", App.indexFileNumberForDisplay,  countFile, path);
        System.out.println(format);
    }
}
