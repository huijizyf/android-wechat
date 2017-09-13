package com.donute.wechat.utils;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.beans.WechatBaseInfo;
import com.donute.wechat.helpers.FileHelper;
import com.donute.wechat.helpers.UrlManager;
import com.donute.wechat.helpers.WechatManager;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.Utf8;

/**
 * Created by zhouyufei on 2017/9/10.
 */

public class FileUploadUtil {

    private byte[] fbytes;
    private JSONObject uploadMediaData=new JSONObject();
    private LoginInfo loginInfo;
    private WechatBaseInfo baseInfo;
    private String fileMd5;
    private String toUser;
    private File file;
    private String mimeType;
    private String modifyDate;

    private UrlManager urlManager;

    private int chunks;
    private int blockSize = 524288;

    private static final String[] FILE_SYMBOL = {"pic", "video", "doc", ""};

    public FileUploadUtil(File file, LoginInfo loginInfo, WechatBaseInfo baseInfo, String toUser, UrlManager urlManager) {

        SimpleDateFormat dateFormat=new SimpleDateFormat("EEE MMM d yyyy HH:mm:ss", Locale.ENGLISH);
        modifyDate=dateFormat.format(new Date())+" GMT+0800 (CST)";
        mimeType=FileHelper.getMimeType(file.getName());
        mimeType= TextUtils.isEmpty(mimeType)?"application/octet-stream":mimeType;
        this.file=file;
        this.toUser=toUser;
        this.urlManager=urlManager;
        this.loginInfo=loginInfo;
        this.baseInfo=baseInfo;

       if (file.length()<blockSize){
           chunks=1;
       } else if (file.length() % blockSize == 0) {
            chunks = (int) file.length() / blockSize;
        } else {
            chunks = (int) file.length() / blockSize + 1;
        }
        try {
            this.fbytes= FileHelper.getFileBytes(file);
            this.fileMd5=Md5Util.getFileMD5String(file);
            uploadMediaData.put("UploadType", 2)
                    .put("BaseRequest", loginInfo.getBaseObject())
                    .put("ClientMediaId",System.currentTimeMillis()/1000*1e4)
                    .put("TotalLen", fbytes.length)
                    .put("StartPos",0)
                    .put("DataLen", fbytes.length)
                    .put("MediaType", 4)
                    .put("FromUserName", baseInfo.getUser().getUserName())
                    .put("ToUserName",toUser)
                    .put("FileMd5", fileMd5);
            upload();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String upload() throws JSONException, IOException {
        String url=urlManager.uploadMediaUrl();
        String result = "";
        for (int i = 0; i < chunks; i++) {
            byte[] mBlock = getBlock(i*blockSize,file,blockSize);

            MultipartBody.Builder builder=new MultipartBody.Builder();

            builder.addFormDataPart("id",  "WU_FILE_0");
            builder.addFormDataPart("name",  file.getName());
            builder.addFormDataPart("type", mimeType);
            builder.addFormDataPart("lastModifiedDate",  modifyDate);
            builder.addFormDataPart("size",  fbytes.length + "");
            if (chunks > 1) {
                builder.addFormDataPart("chunks", chunks + "");
                builder.addFormDataPart("chunk",  i + "");
            }
            builder.addFormDataPart("mediatype",  FILE_SYMBOL[FileHelper.getFileSymbol(file)]);
            builder.addFormDataPart("uploadmediarequest",  uploadMediaData.toString());
            builder.addFormDataPart("webwx_data_ticket", loginInfo.getPassTicket());
            builder.addFormDataPart("pass_ticket",  loginInfo.getPassTicket());
            builder.setType(MediaType.parse("multipart/form-data"));

            RequestBody requestBody=RequestBody.create(MediaType.parse("application/octet-stream"),mBlock);
            builder.addFormDataPart("filename", file.getName(), requestBody);
            RequestBody body=builder.build();

            Response response=OkGo.<String>post(url)
                    .isMultipart(true)
                    .upRequestBody(body)
                    .headers("Connection","keep-alive")
                    .removeHeader("Content-Length")
                    .execute();
            result=response.body().string();
        }


        return result;
    }


    public static byte[] getBlock(long offset, File file, int blockSize) {
        byte[] result = new byte[blockSize];
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(file, "r");
            accessFile.seek(offset);
            int readSize = accessFile.read(result);
            if (readSize == -1) {
                return null;
            } else if (readSize == blockSize) {
                return result;
            } else {
                byte[] tmpByte = new byte[readSize];
                System.arraycopy(result, 0, tmpByte, 0, readSize);
                return tmpByte;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }
}
