package mall;

import java.io.*;

public class DataIO {

    public static Object input(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        try {
            return in.readObject();
        } finally {
            in.close();
        }
    }

    public static void output(String fileName, Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        try {
            out.writeObject(obj);
        } finally {
            out.close();
        }
    }

}
