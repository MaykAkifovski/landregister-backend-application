package de.htw.berlin.landregisterbackendapplication.hyperledger.util;

import de.htw.berlin.landregisterbackendapplication.hyperledger.user.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    public void cleanUp() {
        String directoryPath = "users";
        File directory = new File(directoryPath);
        deleteDirectory(directory);
    }

    private boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children != null ? children : new File[0]) {
                boolean success = deleteDirectory(child);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        LOGGER.info("Deleting - {}", dir.getName());
        return dir.delete();
    }

    public UserContext readUserContext(String affiliation, String username) throws IOException, ClassNotFoundException {
        String filePath = "users/" + affiliation + "/" + username + ".ser";
        File file = new File(filePath);
        if (file.exists()) {
            // Reading the object from a file
            FileInputStream fileStream = new FileInputStream(filePath);
            UserContext uContext;
            try (ObjectInputStream in = new ObjectInputStream(fileStream)) {
                // Method for deserialization of object
                uContext = (UserContext) in.readObject();
            }
            fileStream.close();
            return uContext;
        }

        return null;

    }

    public void writeUserContext(UserContext userContext) throws IOException {
        String directoryPath = "users/" + userContext.getAffiliation();
        String filePath = directoryPath + "/" + userContext.getName() + ".ser";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();

        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(userContext);

        out.close();
        file.close();
    }
}
