/*
 * File: ObjToJsonTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package transformers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This is a transformer class that helps with transforming data to json format and write it.
 */
public class ObjToJsonTransformer {
    /**
     * This method converts a list of objects to JSON string
     *
     * @param obj {@link List<T>} list of T type object.
     * @param <T> {@link T} generic type T for different objects.
     * @return {@link String} converted json string
     * @implNote requires that object has a custom toString method
     */
    public <T> String transform(List<T> obj) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < obj.size(); i++) {
            jsonBuilder.append(obj.get(i).toString());
            if (i < obj.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    /**
     * This method converts a list of objects to JSON string and writes it to a file in the output folder
     *
     * @param fileName {@link String} name of the file to be saved.
     * @param obj      {@link List<T>} list of T type objects to be written.
     * @param <T>      {@link T} generic type T for different objects.
     * @implNote requires that object has a custom toString method
     */
    public <T> void transformAndWrite(String fileName, List<T> obj) {
        String content = transform(obj);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/output/" + fileName + ".json"))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
