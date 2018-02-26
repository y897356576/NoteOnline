package com.stone.core.model;

/**
 * Created by 石头 on 2018/2/26.
 */
public class NoteFile {

    private String id;  //主键

    private String noteId;  //笔记表主键

    private String fileName;    //文件名称

    private String filePath;    //文件路径

    private String fileContentType;    //文件类型 如text/plain

    private String fileType;    //文件名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
