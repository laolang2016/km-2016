package com.laolang.km.module.system.dict.rsp;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SysDictTypeListRsp {

    private Long id;
    private String groupCode;
    private LocalDateTime upDateTime;

}
