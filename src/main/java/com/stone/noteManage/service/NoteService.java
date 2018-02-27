package com.stone.noteManage.service;

import com.stone.common.util.UserInfoUtil;
import com.stone.core.exception.MyException;
import com.stone.core.model.Note;
import com.stone.core.model.NoteFile;
import com.stone.core.model.NoteGenre;
import com.stone.core.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by 石头 on 2018/2/24.
 */
@Service
public class NoteService {

    @Autowired
    private NotePersistProcessor persistProcessor;

    Logger logger = LoggerFactory.getLogger(NoteService.class);

    /**
     * 笔记文件导入
     * @param request
     * @param genreName
     * @param file
     * @return
     */
    public Boolean noteImport(HttpServletRequest request, String genreName, MultipartFile file) {
        if (file == null || file.getSize() <= 0) {
            throw new MyException("文件上传失败：上传的文件不存在");
        }
        try {
            User user = UserInfoUtil.getUserFromRedis(request);

            Note note = new Note();
            NoteGenre noteGenre = note.getNoteGenre();
            NoteFile noteFile = note.getNoteFile();

            String originalName = file.getOriginalFilename();
            note.setNoteName(originalName.substring(0, originalName.lastIndexOf(".")));
            note.setCreateUserId(user.getId());
            note.setContent(new String(file.getBytes()));

            noteGenre.setTypeName(StringUtils.isBlank(genreName) ? "默认" : genreName.trim());

            noteFile.setFileName(note.getNoteName());
            noteFile.setFileContentType(file.getContentType());
            noteFile.setFileType(originalName.substring(originalName.lastIndexOf(".") + 1));

            this.fileTransfer(user, file, noteFile);

            //数据入库
            persistProcessor.persistNote(note);

        } catch (Exception e) {
            logger.error("文件上传异常", e);
            throw new MyException("文件上传异常；" + e.getMessage());
        }
        return true;
    }

    /**
     * 文件转储至服务器
     * @param user
     * @param file
     * @param noteFile
     * @throws IOException
     */
    private void fileTransfer(User user, MultipartFile file, NoteFile noteFile) throws IOException {
        String realPath = getClass().getResource("/").getFile().toString();
        realPath += "files" + File.separator + user.getId() + File.separator;
        String filePath = realPath + noteFile.getFileName() + "." + noteFile.getFileType();

        if (!new File(realPath).exists()) {
            new File(realPath).mkdirs();
        }
        file.transferTo(new File(filePath));
        noteFile.setFilePath(filePath);
    }

}
