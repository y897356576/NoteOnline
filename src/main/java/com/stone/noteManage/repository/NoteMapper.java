package com.stone.noteManage.repository;

import com.stone.common.annotation.MybatisMapper;
import com.stone.core.model.Note;
import com.stone.core.model.NoteContent;
import com.stone.core.model.NoteFile;
import com.stone.core.model.NoteGenre;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 石头 on 2018/2/26.
 */
@MybatisMapper
public interface NoteMapper {

    int createNote(Note note);

    int createNoteContent(NoteContent content);

    int createNoteContents(List<NoteContent> contents);

    int createNoteFile(NoteFile file);

    int createNoteGenre(NoteGenre genre);

    NoteGenre getGenreByName(@Param("userId") String userId, @Param("name") String name);

    Note getNoteById(@Param("userId") String userId, @Param("noteId") String noteId);

    List<Note> getNotesByUserId(String userId);

}
