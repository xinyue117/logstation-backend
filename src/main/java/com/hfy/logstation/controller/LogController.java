package com.hfy.logstation.controller;

import com.google.gson.reflect.TypeToken;
import com.hfy.logstation.entity.QueryBean;
import com.hfy.logstation.entity.Response;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.service.interfaces.LogService;
import com.hfy.logstation.util.JsonUtil;
import com.hfy.logstation.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("logs")
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 根据index获取相应的日志
     *
     * @param index
     * @param from
     * @param size
     * @return Response
     */
    @GetMapping()
    public Response<Object> getAll(@RequestParam("index") String index,
                                @RequestParam("from") int from,
                                @RequestParam("size") int size) throws IOException {
        if (from < 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        if (size <= 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }

        return ResponseUtil.success(logService.getAll(index, from, size));
    }

    @GetMapping("condition")
    public Response<Object> getByCondition(@RequestParam("index") String index,
                                @RequestParam("queries") String queries,
                                @RequestParam("from") int from,
                                @RequestParam("size") int size) throws IOException {
        if (from < 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        if (size <= 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        List<QueryBean> queryList = JsonUtil.toList(queries, new TypeToken<List<QueryBean>>(){}.getType());
        return ResponseUtil.success(logService.getByCondition(index, queryList, from, size));
    }
}
