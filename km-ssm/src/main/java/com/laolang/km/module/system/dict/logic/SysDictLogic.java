package com.laolang.km.module.system.dict.logic;

import com.laolang.km.module.system.dict.req.SysDictTypeListReq;
import com.laolang.km.module.system.dict.rsp.SysDictTypeListRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysDictLogic {

    public SysDictTypeListRsp typeList(SysDictTypeListReq req) {
        SysDictTypeListRsp rsp = new SysDictTypeListRsp();
        BeanUtils.copyProperties(req, rsp);
        rsp.setGroupCode(req.getGroupCode() + "rsp");

        return rsp;
    }
}
