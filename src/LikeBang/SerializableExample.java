package LikeBang;

import java.io.*;
import java.sql.SQLOutput;

public class SerializableExample implements Serializable {
    private int id;
    private String name;
    SerializableExample(){}
    SerializableExample(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SerializableExample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        SerializableExample demo = new SerializableExample(27, "ADC");
        String filename = "data.ser";
        try{
            FileOutputStream outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(demo);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        SerializableExample deserializedObject = null;
        try{
            FileInputStream inputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            deserializedObject = (SerializableExample) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        if(deserializedObject != null) System.out.println(deserializedObject.toString());
        // Output: SerializableExample{id=27, name='ADC'}
    }
}
