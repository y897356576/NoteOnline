package com.stone.core.model;

/**
 * Created by 石头 on 2018/2/26.
 */
public class NoteContent {

    private String id;  //主键

    private String noteId;  //笔记主键

    private Integer index;  //序号

    private String content;  //笔记部分内容

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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
