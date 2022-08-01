package com.security.security20220721.Common;

import cn.hutool.db.ds.pooled.DbConfig;
import com.security.security20220721.config.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.io.File;

@Slf4j
public class StringUtis extends org.apache.commons.lang3.StringUtils{
    private static boolean ipLocal=false;
    private static File file=null;
    private static DbConfig config;
    private static final char SEPARATOR ='_';
    private static final String UNKNOWN="unknown";

    static {
        SpringContextHolder.addCallBacks(()->{
            StringUtis.ipLocal= SpringContextHolder.getProperties("ip.local-parsing",false,Boolean.class);

            if (ipLocal) {
                //此文件为独享，不必关闭
                String path="ip2region/ip2region.db";
                String name="ip2region.db";


                try {
                    config=new DbConfig();
                    file=FileUtil.inputStreamToFile(new ClassPathResource(path).getInputStream(),name);
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }

            }
        });
    }



}
