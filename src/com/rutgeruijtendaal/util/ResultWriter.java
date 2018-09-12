package com.rutgeruijtendaal.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultWriter {

    private FileWriter fw;
    private PrintWriter pw;

    public ResultWriter(String dbType) {

        File dir = new File("results/");
        if (!dir.exists()) dir.mkdirs();

        try {
            fw = new FileWriter("results" + File.separator + dbType + ".txt");
            pw = new PrintWriter(fw);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public void write(String txt) {
        System.out.println(txt);
        pw.print(txt);
        newLine();
    }

    public void newLine() {
        pw.println("");
    }

    public void closeWriter() {
        pw.close();
    }
}
