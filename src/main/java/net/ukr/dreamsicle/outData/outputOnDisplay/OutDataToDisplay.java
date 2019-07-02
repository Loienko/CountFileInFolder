package net.ukr.dreamsicle.outData.outputOnDisplay;

import net.ukr.dreamsicle.App;
import net.ukr.dreamsicle.outData.DisplayData;

/**
 * Класс служащий для вывода полученных данных с программы на дисплей.
 */
public class OutDataToDisplay implements DisplayData {
    @Override
    public synchronized void outputDataToDisplay(String path, int countFile) {
        System.out.println(String.format("%d | %-5s | %-10s", App.indexFileNumberForDisplay,  countFile, path));
    }
}
