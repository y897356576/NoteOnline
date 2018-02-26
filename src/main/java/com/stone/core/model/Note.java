package com.stone.core.model;

import com.stone.common.util.IdGenerator;
import com.stone.core.exception.MyException;
import com.stone.core.repository.NoteMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private Integer status;    //文件状态 1启用 0删除

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

    private NoteMapperImpl noteMapperImpl;
    public void setNoteMapperImpl(NoteMapperImpl noteMapperImpl) {
        this.noteMapperImpl = noteMapperImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(Note.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 笔记新增
     * @return
     */
    @Transactional
    public void persistNote(){
        id = IdGenerator.generateId_16();
        createTime = sdf.format(new Date());
        status = 1;

        //生成笔记内容对象
        this.generateNoteContent();

        //生成笔记类别
        this.generateNoteGenre();

        //生成笔记文件数据
        this.generateNoteFile();

        try{
            noteMapperImpl.createNote(this);
        } catch (Exception e){
            logger.error("笔记新增失败", e);
            throw new MyException("笔记新增失败：" + e.getMessage());
        }
    }

    /**
     * 将Note的content内容按7000字符长度分为多个NoteContent对象
     * @return
     */
    private void generateNoteContent() {
        contents = new ArrayList<>();
        if (StringUtils.isBlank(content)) {
            return;
        }

        String temporary = content;
        Integer index = 0;
        while (temporary.length() > 7000) {
            NoteContent contentTemp = new NoteContent();
            contentTemp.setId(IdGenerator.generateId_16());
            contentTemp.setNoteId(id);
            contentTemp.setIndex(index++);
            contentTemp.setContent(temporary.substring(7000));
            contents.add(contentTemp);

            temporary = temporary.substring(7000, temporary.length());
        }
        if (StringUtils.isNotBlank(temporary)) {
            NoteContent contentTemp = new NoteContent();
            contentTemp.setId(IdGenerator.generateId_16());
            contentTemp.setNoteId(id);
            contentTemp.setIndex(index);
            contentTemp.setContent(temporary);
            contents.add(contentTemp);
        }
    }

    /**
     * 生成笔记类别
     */
    private void generateNoteGenre() {
        String genreTypeName = noteGenre.getTypeName();
        noteGenre = noteMapperImpl.getGenreByName(createUserId, noteGenre.getTypeName());
        if (noteGenre == null) {
            noteGenre = new NoteGenre();
            noteGenre.setId(IdGenerator.generateId_16());
            noteGenre.setUserId(createUserId);
            noteGenre.setTypeName(genreTypeName);
            noteMapperImpl.createNoteGenre(noteGenre);
        }

        noteGenreId = noteGenre.getId();
    }

    private void generateNoteFile() {
        if (noteFile != null) {
            noteFile.setId(IdGenerator.generateId_16());
            noteFile.setNoteId(id);
            noteFileId = noteFile.getId();
        }
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
