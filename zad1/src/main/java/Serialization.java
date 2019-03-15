import java.io.*;

public class Serialization {

    public static void Serialize(Serializable object, String filepath) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filepath));

        stream.writeObject(object);
        stream.close();
    }

    public static Object Deserialize(String filepath) throws ClassNotFoundException, IOException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filepath));

        Object object = stream.readObject();

        stream.close();
        return object;
    }
}
