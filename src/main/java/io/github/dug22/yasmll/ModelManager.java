package io.github.dug22.yasmll;

import io.github.dug22.yasmll.models.IModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ModelManager {

    public static void saveModel(Object model, String filePath) {
        if (!filePath.endsWith(".ser")) {
            System.out.println("You can only save models as a serializable file.");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object loadModel(String filePath) {
        if (!filePath.endsWith(".ser")) {
            System.out.println("We can only load models that are serializable!");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (!(obj instanceof IModel)) {
                throw new IllegalArgumentException("Invalid File!");
            }
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
