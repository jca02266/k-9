package com.fsck.k9.logcat;

import java.io.*;

public class Logcat {
    public static void print(PrintWriter ps) throws IOException {
        String[] commandLine = {
                "logcat"
                ,"-d"
                ,"-v"
                ,"threadtime"
//                ,"-s"
//                ,K9.LOG_TAG + ":V"
        };
        Process process = Runtime.getRuntime().exec(commandLine);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            ps.println(line);
        }
        ps.println("----- end");
    }
}
