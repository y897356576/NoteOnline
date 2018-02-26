package com.stone.core.factory;

import com.stone.core.model.Note;
import com.stone.core.model.NoteFile;
import com.stone.core.model.NoteGenre;
import com.stone.core.repository.NoteMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 石头 on 2017/6/22.
 */
public class NoteFactory {

    private static NoteMapperImpl noteMapperImpl;
    public void setNoteMapperImpl(NoteMapperImpl noteMapperImpl) {
        this.noteMapperImpl = noteMapperImpl;
    }

    public static Note generateNote() {
        Note note = new Note();
        note.setNoteGenre(new NoteGenre());
        note.setNoteFile(new NoteFile());
        note.setNoteMapperImpl(noteMapperImpl);
        return note;
    }

    public static void standardNote(Note note){
        note.setNoteMapperImpl(noteMapperImpl);
    }
}
