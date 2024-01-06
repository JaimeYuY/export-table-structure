package com.ocean.angel.tool.model;

import lombok.Data;

/**
 * @desc:
 * @author: jaime.yu
 * @time: 2023/5/20 15:52
 */
@Data
public class Structure {

    /**
     * 序号
     */
    private Integer code;

    /**
     * 表字段
     */
    private String field;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否可以为空
     */
    private String isNull;

    /**
     * 备注
     */
    private String comment;

}
