package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {

        String env =
                System.getProperty(
                        "env",
                        "staging"
                );

        loadProperties(env);
    }

    public static void loadProperties(
            String env
    ) {

        properties = new Properties();

        String path =
                System.getProperty("user.dir")
                        + "/src/test/resources/config/"
                        + env
                        + ".properties";

        try (FileInputStream fis =
                     new FileInputStream(path)) {

            properties.load(fis);

            System.out.println(
                    "LOAD CONFIG: " + path
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load properties file: "
                            + path,
                    e
            );
        }
    }

    public static String getProperty(
            String key
    ) {

        return properties.getProperty(key);
    }
}
