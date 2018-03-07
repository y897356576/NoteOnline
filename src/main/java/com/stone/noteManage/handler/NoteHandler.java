package com.stone.noteManage.handler;

import com.alibaba.fastjson.JSONObject;
import com.stone.common.util.UserInfoUtil;
import com.stone.core.exception.MyException;
import com.stone.core.model.Note;
import com.stone.core.model.User;
import com.stone.noteManage.service.NoteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    private static final Logger logger = LoggerFactory.getLogger(NoteHandler.class);

    @RequestMapping(value = "noteImport", method = RequestMethod.POST)
    @ResponseBody
    public Map noteImport(HttpServletRequest request, String genreName,
                          @RequestParam("noteUpload") MultipartFile file) {
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        final String noteId = service.noteImport(user, genreName, file);

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("noteId", noteId);
        }};
        return resultMap;
    }

    @RequestMapping(value = "noteList", method = RequestMethod.GET)
    @ResponseBody
    public Map noteList(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        List<Note> notes = new ArrayList<>();
        try {
            notes = service.noteList(user);
            result.put("flag", true);
        } catch (Exception e) {
            logger.error("笔记列表查询失败", e);
            result.put("flag", false);
        }

        result.put("notes", notes);
        return result;
    }

    @RequestMapping(value = "noteList", method = RequestMethod.PUT)
    @ResponseBody
    public Map noteIndexModify(HttpServletRequest request, @RequestBody JSONObject paramJson) {
        Map<String, Object> result = new HashMap<>();
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        String noteId = paramJson.getString("noteId");
        Integer indexModify = paramJson.getInteger("indexModify");

        if (StringUtils.isBlank(noteId) || indexModify == null) {
            throw new MyException("BadRequest", "笔记顺序调整失败");
        }

        try {
            service.noteIndexModify(user, noteId, indexModify);
            result.put("flag", true);
        } catch (Exception e) {
            logger.error("笔记顺序调整失败", e);
            result.put("flag", false);
            result.put("errMsg", "笔记顺序调整失败");
        }

        return result;
    }

    @RequestMapping(value = "noteDetail/{noteId}", method = RequestMethod.GET)
    @ResponseBody
    public Map noteDetail(HttpServletRequest request, @PathVariable String noteId) {
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        if (StringUtils.isBlank(noteId)) {
            throw new MyException("BadRequest", "笔记内容加载失败");
        }

        Note note = service.noteDetail(request, user, noteId);

        Map<String, Object> result = new HashMap<>();
        result.put("note", note);
        return result;
    }

    @RequestMapping(value = "noteDetailImg", method = RequestMethod.GET)
    @ResponseBody
    public void noteDetailImg(HttpServletResponse response, @RequestParam String filePath) {
        service.noteDetailImg(response, filePath);
    }

    @RequestMapping(value = "noteDetail/{noteId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map noteDelete(HttpServletRequest request, @PathVariable String noteId) {
        User user = UserInfoUtil.getUserFromRedis(request);
        this.checkUserAvailable(user);

        if (StringUtils.isBlank(noteId)) {
            throw new MyException("BadRequest", "笔记删除失败");
        }

        Map<String, Object> result = new HashMap<>();
        try {
            service.noteDelete(user, noteId);
            result.put("flag", true);
        } catch (Exception e) {
            logger.error("笔记删除失败", e);
            result.put("flag", false);
            result.put("errMsg", "笔记删除失败");
        }
        return result;
    }

    /**
     * 验证用户
     * @param user
     */
    private void checkUserAvailable(User user) {
        if (user == null) {
            throw new MyException("BadRequest", "当前用户不可用，请重新登录");
        }
    }

}
