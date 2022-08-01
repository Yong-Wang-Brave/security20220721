package com.security.security20220721.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil extends cn.hutool.core.io.FileUtil{
    public static final String SYS_TEM_DIR =System.getProperty("java.io.temdir")+File.separator;
    public static File inputStreamToFile(InputStream ins, String name) throws Exception{
        File file = new File(SYS_TEM_DIR + name);
        if (file.exists()) {
            return  file;
        }

        try(OutputStream os =new FileOutputStream(file)){
            int bytesRead;
            int len=8192;
            byte[] buffer =new byte[len];
            while(( bytesRead=ins.read(buffer,0,len))!=-1){
    os.write(buffer,0,bytesRead);
            }
        }

        ins.close();
        return file;
    }
}
