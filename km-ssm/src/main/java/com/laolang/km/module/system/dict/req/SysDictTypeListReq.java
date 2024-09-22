package com.laolang.km.module.system.dict.req;

import java.time.LocalDateTime;

import com.laolang.km.framework.common.core.BaseReq;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SysDictTypeListReq extends BaseReq {
    private Long id;
    private String groupCode;
    private LocalDateTime upDateTime;
}
