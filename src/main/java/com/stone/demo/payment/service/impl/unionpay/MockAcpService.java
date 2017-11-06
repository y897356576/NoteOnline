package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.core.SDKConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("MockAcpService")
public class MockAcpService implements AcpService {

    @Override
    public boolean validate(JSONObject data, String encoding) {
        return true;
    }

    @Override
    public JSONObject converMapToJSONObject(Map<String, String> mapData) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : mapData.entrySet()) {
            if (SDKConstants.param_signature.equals(entry.getKey().trim())) {
                continue;
            }
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    @Override
    public JSONObject downloadAccount(String settleDate) {
        final String content = "eJwL8GZmEWFgYOBgcIwq92zZdv5hCxMDQ5gJA4MsUNTTz8/Q3MDQ0NLCIso33sLSwtDQ0MTcyNL\n" +
                "CwNDI9O2s3X3BhgJt1Sr3HppkalyIqdFlvKi05Alrk4iKyrbGpuvpczZuFp/mePz2w8LJbzr/18+d4//4/jfLY1\n" +
                "tamVoYNnhOnh6Q/fR19amQz2weDQJKy3UF1bzy3ZgPXBJZfFGNaeck88Al1XOlTrLfqHr88vFHFv5oj0Rp3182l\n" +
                "+W+3bOz+TX97Mfz/26Eftlzf980+V6x+T3L/xf9z7hm7jDBmqHU+sj3/XrxdvPr59yv\n" +
                "+/vt76eXNeWWH3bPZ9cq+lmys/rRp\n" +
                "+/71zXX/fxeV1n991UGg9jGFj4JkWl2vxmD50pJNhwtkp7EvI0vwZJtUj/jRiGlwHvcNy6snMgYUXBgGssah2dx\n" +
                "B7MY\n" +
                "+IRF36wKVs7uWnJ7gsyGUobVR75GlT/RS0yYxeBTmpXCMO1yrgffDauGQJ2VIWsTdvqWQdRrN1eyRgtuOBQaVHU\n" +
                "+4cyWalHLEqBit31dMYIGJ7a6yTWcTDFnnfHiygmmM9o3uhoSNXLbVzU2NWq9v7dMwsp0U9jE79tPMEazG6w45P\n" +
                "2qgTNSWBRs2bF9vhuVEmI0l9xwrjuTmKQeDxRsrYl3XccRcSzmFMdPhcQstWbTuVKRwUuYtwEVLtj7euqKs8nGo\n" +
                "j5A20MNwAr1In6WhZiIfgpd7iaWIPcpnIUHyG\n" +
                "+59OjdBHugIlnRnyI7lRIkk3bbVy3zmDQl7ZzSbL9enQN7mtZySAgG2Gs5PTlRuKwJZEf6xHkHw1jWtH424NvN\n" +
                "+ejI5wSgt7kN7p\n" +
                "+MF9zwTcL3gNynzo3nWv4sC1ZmNvjSe02UUZsn8FIg/2tVP9t1l8HOOZ/gzaQmzt3DNa3gux0o4EtvvG1Yyzljw\n" +
                "UQ31u5ELbBHVnvMV/offqCOOQCefLsnm3jUHPTQDGQE8jgZGOSAokEuRgaGBpAUbGCIkX5DbnsddhCojb9R/0J8\n" +
                "yl3VrEnzP74+kjQ/6XLLhGtuzDvDZi3LVQpyXdHaJPVqtppGmaZTUq6Jgqf8nO03LecYp8+4GfW2X\n" +
                "+nDlBv1lvYbZz2rjt78+1Vh\n" +
                "+FfnUjG/1uUmfEwcGaE9+nIV05INj51hfBjRcvJe/tYzO62F/yg80A2vF6vhmOP526NXtDU9wsXAaUXC5ykXZkl\n" +
                "ct3GskE88s7b6yuRpB9nDny1ed3LSl1XZ13rXfX2unF+w2uTu5hgv67ybml5NKwo5XgRssX5vxLs0v\n" +
                "+nxntrEBefvLhXJnTqhXdn2fviLkulXbmequAo8Sb58qjtHT+3oK\n" +
                "+kGU46mw7KcHIcKBfiFKnfcVFd8yXN18+mWVzyT4/9rps+YO\n" +
                "+vwluSccwvWWDpcyK54fOCk1v/7zjc3/pouJrv5+o9zNZLVCmq3r2Sz3ux6vlr6Rc5Vfce7O8onv9oTvrdyfuBX\n" +
                "/y+MAd6MTCIMuEsTEGBkUICy8JYtyEZhi1kIABm1n4lQPAd4s7KBVDMB4XQg7cMC4gEAY9UH3w==";
        return new JSONObject() {{
            put("respCode", "00");
            put("fileContent", content);
            put("fileName", "700000000000001_20170119.zip");
        }};
    }
}
