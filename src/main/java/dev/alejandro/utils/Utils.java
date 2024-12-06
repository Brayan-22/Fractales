package dev.alejandro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class Utils {

    private static Properties properties;
    private static Path tempDir;
    static {
        try {
            tempDir = Files.createTempDirectory("temp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Utils() {
    }


    public static void deleteFolderContent(String path){
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolderContent(file.getAbsolutePath());
                    file.delete();
                } else {
                    file.delete();
                }
            }
        }

    }

    public static double normalizar(
            double x,
            double min,
            double max,
            double newMin,
            double newMax
    ){
        return (x-min)/(max-min)*(newMax-newMin)+newMin;
    }

    public static int getColor(int code){
        int rgb;
        if (code == 8){
            rgb = 0x404040;
        }else{
            int i = 0x7f | ((code & 8)<<4);
            rgb=(((code&4)*i)<<(16-2))+(((code&2)*i)<<(8-1))+((code&1)*i);
        }
        return rgb;
    }

    public static Properties getProperties(){
        if (properties == null){
            properties = new Properties();
            try(InputStream input = new FileInputStream("src/main/resources/app.properties")){
                properties.load(input);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return properties;
    }
    public static String getResourceAbsolutePath(String resourceName) {
        URL resourceUrl = Utils.class.getClassLoader().getResource(resourceName);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        try{
            return Paths.get(resourceUrl.toURI()).toAbsolutePath().toString();
        }catch (Exception e){
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
    }

    public static Path getTempDir() {
        return tempDir;
    }
}
