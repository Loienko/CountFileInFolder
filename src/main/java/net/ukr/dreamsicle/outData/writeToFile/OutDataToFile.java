package net.ukr.dreamsicle.outData.writeToFile;

import net.ukr.dreamsicle.App;
import net.ukr.dreamsicle.outData.FileData;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс служащий для записи полученных данных с программы в файл по указанному пути.
 */
public class OutDataToFile implements FileData {
    private static final Logger LOGGER = Logger.getLogger(OutDataToFile.class);

    @Override
    public synchronized void writeToFileData(String fileToWrite, String path, int countFile) {
        String format = String.format("%s; %-25s", path, countFile);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            bufferedWriter.write(format + "\n");
            bufferedWriter.flush();
            App.indexFileNumberForDisplay++;
        } catch (IOException e) {
            LOGGER.info("Problem with write data to file", e);
        }
    }
}
