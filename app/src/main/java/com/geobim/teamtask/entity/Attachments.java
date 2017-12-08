package com.geobim.teamtask.entity;

import org.bson.types.ObjectId;

import java.net.URL;

/**
 *  附件信息
 * Created by Joseph on 2017/11/30.
 */

public class Attachments {
    private ObjectId fsId;            //文件Id
    private String fileName;        //文件名称
    private URL fileUrl;            //文件链接

    public ObjectId getFsId() {
        return fsId;
    }

    public void setFsId(ObjectId fsId) {
        this.fsId = fsId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public URL getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(URL fileUrl) {
        this.fileUrl = fileUrl;
    }
}
