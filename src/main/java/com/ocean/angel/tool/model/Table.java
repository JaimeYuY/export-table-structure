package com.ocean.angel.tool.model;

import lombok.Data;
import java.util.List;

/**
 * @desc:
 * @author: jaime.yu
 * @time: 2023/5/20 15:51
 */
@Data
public class Table {

    /**
     *  表名
     */
    private String name;

    /**
     *  表备注信息
     */
    private String comment;

    /**
     *  表字段信息
     */
    private List<Structure> structure;

}
