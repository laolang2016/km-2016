package com.laolang.km.module.system.dict.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laolang.km.framework.common.core.R;
import com.laolang.km.module.system.dict.logic.SysDictLogic;
import com.laolang.km.module.system.dict.req.SysDictTypeListReq;
import com.laolang.km.module.system.dict.rsp.SysDictTypeListRsp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("admin/system/dict")
@RestController
public class SysDictController {

    private final SysDictLogic sysDictLogic;

    @PostMapping(value = "type/list")
    public R<SysDictTypeListRsp> typeList(@RequestBody SysDictTypeListReq req) {
        return R.ok(sysDictLogic.typeList(req));
    }
}
