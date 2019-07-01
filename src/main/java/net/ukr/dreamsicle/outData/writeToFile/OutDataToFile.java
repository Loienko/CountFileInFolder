package net.ukr.dreamsicle.outData.writeToFile;

import net.ukr.dreamsicle.App;
import net.ukr.dreamsicle.outData.FileData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class OutDataToFile implements FileData {

    @Override
    public synchronized void writeToFileData(String fileToWrite, String path, int countFile) {
        String format = String.format("%s; %-25s", path, countFile);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            bufferedWriter.write(format + "\n");
            bufferedWriter.flush();
            App.indexFileNumberForDisplay++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
