package com.ocean.angel.tool.enums;

/**
 * @desc: 导出类型
 *
 * @author: jaime.yu
 * @time: 2023/5/20 16:25
 */
public enum ExportTypeEnum {

    ALL(0, "导出所有的表"),

    NEEDED(1, "只导出配置的表"),

    UNNEEDED(2, "导出除了配置之外的表");

    private String msg;

    private Integer code;

    ExportTypeEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
