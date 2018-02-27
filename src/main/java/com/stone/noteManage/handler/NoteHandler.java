package com.stone.noteManage.handler;

import com.stone.common.util.ObjMapTransUtil;
import com.stone.common.util.UserInfoUtil;
import com.stone.core.exception.MyException;
import com.stone.core.model.Note;
import com.stone.core.model.User;
import com.stone.noteManage.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 石头 on 2018/2/24.
 */
@Controller
@RequestMapping("/noteHandler")
public class NoteHandler {

    @Autowired
    private NoteService service;

    @RequestMapping(value = "noteImport", method = RequestMethod.POST)
    @ResponseBody
    public Map noteImport(HttpServletRequest request, String genreName,
                          @RequestParam("noteUpload") MultipartFile file) {
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        final Boolean result = service.noteImport(user, genreName, file);

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("result", result);
        }};
        return resultMap;
    }

    @RequestMapping(value = "noteList", method = RequestMethod.GET)
    @ResponseBody
    public Map noteList(HttpServletRequest request) {
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        List<Note> notes = service.noteList(user);

        Map<String, Object> result = new HashMap<>();
        result.put("notes", notes);
        return result;
    }

    @RequestMapping(value = "noteDetail/{noteId}", method = RequestMethod.GET)
    @ResponseBody
    public Map noteDetail(HttpServletRequest request, @PathVariable String noteId) {
        User user = UserInfoUtil.getUserFromRedis(request);
        Note note = service.noteDetail(user, noteId);

        Map<String, Object> result = new HashMap<>();
        result.put("note", note);
        return result;
    }

    /**
     * 验证用户
     * @param user
     */
    private void checkUserAvailable(User user) {
        if (user == null) {
            throw new MyException("当前用户不可用，请重新登录。");
        }
    }

}
