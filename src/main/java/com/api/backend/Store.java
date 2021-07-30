package com.api.backend;

import java.io.File;

public class Store {
    // enter the path/to/your/news-app
    File dir = new File(File.separator+"home"+File.separator+"amos"+File.separator+"dev"+File.separator+"sports backend api");

    public File getDir() {
        createFpDir();
        return dir;
    }
    public static String [] accountTypes;

    public static String [] accountTypes_id;

    public static String[] getAccountTypes() {
        return accountTypes;
    }

    public static void setAccountTypes(String[] accountTypes) {
        Store.accountTypes = accountTypes;
    }

    public static String[] getAccountTypes_id() {
        return accountTypes_id;
    }

    public static void setAccountTypes_id(String[] accountTypes_id) {
        Store.accountTypes_id = accountTypes_id;
    }
    private void createFpDir(){

        if (!dir.exists()){
            try {
                // create dir
                dir.mkdirs();

                // give permissions
                dir.setReadable(true, false);
                dir.setWritable(true, false);
                dir.setExecutable(true, false);

            }
            catch (Exception e){
                System.out.println("An error in creating dir");
                e.printStackTrace();
            }
        }
    }
}
