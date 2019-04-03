import org.nuxeo.onedrive.client.*;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        String access_token = getToken("token.properties");
        OneDriveAPI api = new OneDriveBasicAPI(access_token);
        OneDriveFolder root = OneDriveFolder.getRoot(api);

        printRootFilesFromDirectory(root);

        String filename = "doc.txt";
        String outFilePath = "./aa.txt";
        saveFile(root, filename, outFilePath);

    }

    private static String getToken(String propertiesFile){
        Properties prop = new Properties();
        String filePath = Thread.currentThread().getContextClassLoader().getResource(propertiesFile).getPath();
        try {
            prop.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("access_token");
    }

    private static void printRootFilesFromDirectory(OneDriveFolder root){

        Iterator<OneDriveItem.Metadata> iterator = root.getChildren().iterator();

        while (iterator.hasNext()){
            OneDriveItem.Metadata metadata = iterator.next();
            System.out.println(Entity.toString(metadata));
        }
    }

    private static void saveFile(OneDriveFolder root, String srcFilename, String outFilePath){

        Iterator<OneDriveItem.Metadata> itemsIterator = root.search(srcFilename).iterator();
        OneDriveItem.Metadata metadata = itemsIterator.next();

        if (metadata.isFile() && metadata.getName().equals(srcFilename)){
            try {
                InputStream is = metadata.asFile().getResource().download();
                File file = new File(outFilePath);

                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, read);
                }
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
