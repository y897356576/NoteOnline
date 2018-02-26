package com.stone.noteManage.handler;

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
        final Boolean result = service.noteImport(request, genreName, file);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("result", result);
        }};
        return resultMap;
    }

}
