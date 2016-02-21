package com.web.core.tool;

import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.Bucket;
import com.sina.cloudstorage.services.scs.model.PutObjectResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by shenzhiqiang on 16/2/16.
 */
@Service
public class SCSTool {
    private static String accessKey = "wp1malxNvsW7Q1vpWdNY";
    private static String secretKey = "057c08e5cfa585677d27ad1447bc53950af56840";
    private static String bucketName = "mzx-img";
    private static SCS conn;

    public SCSTool() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        conn = new SCSClient(credentials);
    }

    public static SCS getConn() {
        return conn;
    }

    public static void setConn(SCS conn) {
        SCSTool.conn = conn;
    }

    public static PutObjectResult putObject(String filename, String path){
        PutObjectResult putObjectResult;
        try {
            putObjectResult = conn.putObject(bucketName, filename, new File(path));
        } catch (Exception e) {
            return null;
        }

        return putObjectResult;
    }

    public static boolean delObject(String filename) {
        try {
            conn.deleteObject(bucketName, filename);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
