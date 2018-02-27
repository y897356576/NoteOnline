package com.stone.noteManage.service;

import com.stone.common.util.IdGenerator;
import com.stone.core.exception.MyException;
import com.stone.core.model.Note;
import com.stone.core.model.NoteContent;
import com.stone.core.model.NoteFile;
import com.stone.core.model.NoteGenre;
import com.stone.core.repository.NoteMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 石头 on 2018/2/27.
 */
@Component
public class NotePersistProcessor {

    @Autowired
    private NoteMapperImpl noteMapperImpl;

    Logger logger = LoggerFactory.getLogger(NotePersistProcessor.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 笔记新增
     * @return
     */
    @Transactional
    public void persistNote(Note note){
        note.setId(IdGenerator.generateId_16());
        note.setCreateTime(sdf.format(new Date()));
        note.setStatus(1);

        //生成笔记内容对象
        this.generateNoteContent(note);

        //生成笔记类别
        this.generateNoteGenre(note.getNoteGenre(), note.getCreateUserId(), note);

        //生成笔记文件数据
        this.generateNoteFile(note.getNoteFile(), note);

        try{
            noteMapperImpl.createNote(note);
        } catch (Exception e){
            logger.error("笔记新增失败", e);
            throw new MyException("笔记新增失败：" + e.getMessage());
        }
    }

    /**
     * 将Note的content内容按7000字符长度分为多个NoteContent对象
     * @return
     */
    private void generateNoteContent(Note note) {
        List<NoteContent> contents = note.getContents();
        if (StringUtils.isBlank(note.getContent())) {
            return;
        }

        String temporary = note.getContent();
        Integer index = 0;
        while (temporary.length() > 7000) {
            NoteContent contentTemp = new NoteContent();
            contentTemp.setId(IdGenerator.generateId_16());
            contentTemp.setNoteId(note.getId());
            contentTemp.setIndex(index++);
            contentTemp.setContent(temporary.substring(0, 7000));
            contents.add(contentTemp);

            temporary = temporary.substring(7000);
        }
        if (StringUtils.isNotBlank(temporary)) {
            NoteContent contentTemp = new NoteContent();
            contentTemp.setId(IdGenerator.generateId_16());
            contentTemp.setNoteId(note.getId());
            contentTemp.setIndex(index);
            contentTemp.setContent(temporary);
            contents.add(contentTemp);
        }
    }

    /**
     * 生成笔记类别
     */
    private void generateNoteGenre(NoteGenre noteGenre, String createUserId, Note note) {
        String genreTypeName = noteGenre.getTypeName();
        noteGenre = noteMapperImpl.getGenreByName(createUserId, noteGenre.getTypeName());
        if (noteGenre == null) {
            noteGenre = new NoteGenre();
            noteGenre.setId(IdGenerator.generateId_16());
            noteGenre.setUserId(createUserId);
            noteGenre.setTypeName(genreTypeName);
            noteMapperImpl.createNoteGenre(noteGenre);
        }

        note.setNoteGenreId(noteGenre.getId());
    }

    /**
     * 处理笔记文件关联字段
     * @param noteFile
     * @param note
     */
    private void generateNoteFile(NoteFile noteFile, Note note) {
        if (noteFile != null) {
            noteFile.setId(IdGenerator.generateId_16());
            noteFile.setNoteId(note.getId());
            note.setNoteFileId(noteFile.getId());
        }
    }

}
