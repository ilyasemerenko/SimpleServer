package lesson.two;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SemerenkoIlya on 09.03.2016.
 */
public class FileSupport {

    Map<Object, Object> filePath;

    public FileSupport(){
        filePath = new HashMap<>();
        filePath.put("/readme", "src/content/readme.txt");
        filePath.put("/content/text", "src/content/textFile.txt");
        filePath.put("/content/image","src/content/testsImage.jpg");
    }
    public static void main(String[] args){
        FileSupport fs = new FileSupport();
        fs.getFile("/readme");
    }

    public byte[] getFile(String path){
        FileInputStream fileInStream = null;
        byte[] inputStream = new byte[0];

        try {
            fileInStream = new FileInputStream(String.valueOf(filePath.get(path)));

            int content;
            int i = 0;
            inputStream = new byte[fileInStream.available()];
            while ((content = fileInStream.read()) != -1) {
                inputStream[i] = (byte)content;
                i++;
            }
            fileInStream.close();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
