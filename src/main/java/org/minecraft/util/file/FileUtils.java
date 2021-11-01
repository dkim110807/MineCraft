package org.minecraft.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class FileUtils {

    private FileUtils() {
    }

    public static String loadAsString(String file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer = "";

            while ((buffer = reader.readLine()) != null) {

                if (buffer.startsWith("#include ")) {
                    String include = buffer.substring(10, buffer.length() - 1);

                    BufferedReader br = new BufferedReader(new FileReader("assets/shaders/" + include));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        result.append(line).append('\n');
                    }
                } else {
                    result.append(buffer).append('\n');
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}