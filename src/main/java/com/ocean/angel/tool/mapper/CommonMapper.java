package com.ocean.angel.tool.mapper;

import com.ocean.angel.tool.model.Table;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 *
 * @author: jaime.yu
 * @time: 2023/5/20 16:25
 */
public interface CommonMapper {

    /**
     * @desc 获取数据库所有表的表名
     *
     * @return java.util.List<java.lang.String>
     */
    List<Table> getAllTables(@Param("database") String database);


    /**
     * @desc 获取指定表的表结构
     *
     * @param tableName
     * @return java.util.List<com.example.demo.dto.Structure>
     */
    @MapKey("field")
    List<Map<String, String>> getTableStructure(@Param("tableName") String tableName);

}
