package com.stone.noteManage.handler;

import com.stone.common.util.ObjMapTransUtil;
import com.stone.common.util.UserInfoUtil;
import com.stone.core.model.Note;
import com.stone.core.model.User;
import com.stone.noteManage.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

        final Boolean result = service.noteImport(user, genreName, file);

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("result", result);
        }};
        return resultMap;
    }

    @RequestMapping(value = "noteDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map noteDetail(HttpServletRequest request, String noteId) {
        User user = UserInfoUtil.getUserFromRedis(request);
        Note note = service.noteDetail(user, noteId);
        return ObjMapTransUtil.objToMap(note);
    }

}
