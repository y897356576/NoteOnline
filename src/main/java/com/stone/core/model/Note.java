package com.stone.core.model;

import com.stone.common.model.DataStatus;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 石头 on 2018/2/26.
 */
public class Note {

    private String id;  //主键

    private String noteName;    //笔记名称

    private String noteGenreId;    //笔记分类

    private String noteFileId;  //笔记文件信息ID

    private String createUserId;    //创建人ID

    private String createTime;    //创建时间

    private String modifyTime;    //修改时间

    private DataStatus status;    //文件状态 1启用 0删除

    @Transient
    private String createUserName;  //创建人名称

    @Transient
    private NoteGenre noteGenre;    //笔记分类对象

    @Transient
    private String content; //笔记内容

    @Transient
    private NoteFile noteFile;  //笔记文件信息

    @Transient
    private List<NoteContent> contents; //笔记内容

    public Note() {
        this.noteGenre = new NoteGenre();
        this.noteFile = new NoteFile();
        this.contents = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteGenreId() {
        return noteGenreId;
    }

    public void setNoteGenreId(String noteGenreId) {
        this.noteGenreId = noteGenreId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteGenre getNoteGenre() {
        return noteGenre;
    }

    public void setNoteGenre(NoteGenre noteGenre) {
        this.noteGenre = noteGenre;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<NoteContent> getContents() {
        return contents;
    }

    public void setContents(List<NoteContent> contents) {
        this.contents = contents;
    }

    public String getNoteFileId() {
        return noteFileId;
    }

    public void setNoteFileId(String noteFileId) {
        this.noteFileId = noteFileId;
    }

    public NoteFile getNoteFile() {
        return noteFile;
    }

    public void setNoteFile(NoteFile noteFile) {
        this.noteFile = noteFile;
    }
}
