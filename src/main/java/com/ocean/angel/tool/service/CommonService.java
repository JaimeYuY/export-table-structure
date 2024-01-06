package com.ocean.angel.tool.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ocean.angel.tool.model.Structure;
import com.ocean.angel.tool.model.Table;
import com.ocean.angel.tool.enums.ExportTypeEnum;
import com.ocean.angel.tool.mapper.CommonMapper;
import com.ocean.angel.tool.util.WordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 表结构获取Service
 *
 * @author: jaime.yu
 * @time: 2023/5/20 16:47
 */
@Slf4j
@Service
public class CommonService {

    @Value("${database}")
    private String database;

    @Value("${table.export.type}")
    private Integer exportType;

    @Value("${table.export.list}")
    private String exportList;

    // 字段名称
    static String STRUCTURE_FIELD = "Field";
    // 字段类型
    static String STRUCTURE_TYPE = "Type";
    // 字段是否为空
    static String STRUCTURE_NULL = "Null";
    // 注释
    static String STRUCTURE_COMMENT = "Comment";
    // 字段不为空
    static String STRUCTURE_ISNULL_NO = "NO";

    @Resource
    private CommonMapper commonMapper;

    /**
     * @desc 生成数据库所有表的结构word文件
     *
     * @return void
     */
    public void generateTableStructure() {

        log.info("导出数据库表结构开始...");
        long startTime = System.currentTimeMillis();

        Map<String, Object> dataMap = new HashMap<>();
        List<Table> tableList = commonMapper.getAllTables(database);

        // 数据过滤，获取需要导出表的表名
        List<Table> needExportTables = dataHandler(exportType, exportList, tableList);

        if(CollUtil.isNotEmpty(needExportTables)) {
            for (Table table: needExportTables) {
                String tableName = table.getName();
                List<Structure> structures = getTableStructure(tableName);
                table.setStructure(structures);
                if(StrUtil.isNotEmpty(table.getComment())) {
                    tableName = tableName + "(" + table.getComment() + ")";
                    table.setName(tableName);
                }
            }

            try {
                // 生成需要导出表结构的word文件
                dataMap.put("tables", needExportTables);
                WordUtil.generateWord(dataMap, "tables.ftl", "tables.doc");
            } catch (Exception e) {
                log.error("generateTableStructure() error, {}", e.getMessage(), e);
            }
        }

        long endTime = System.currentTimeMillis();

        log.info("导出数据库表结构完成，总共{}张表， 耗时：{}秒", needExportTables.size(), (endTime - startTime) / 1000);
    }

    /**
     * 数据过滤
     * @param exportType 0-导出所有表 1-导出配置中的表 2-导出配置之外的其他表
     * @param exportList
     * @param tables
     * @return
     */
    private List<Table> dataHandler(Integer exportType, String exportList, List<Table> tables) {

        // exportType = 0, 导出所有表
        if(ExportTypeEnum.ALL.getCode().equals(exportType)) {
            return tables;
        }

        // exportType = 1, 导出exportList配置的表，表名以分号隔开
        if(ExportTypeEnum.NEEDED.getCode().equals(exportType)) {
            List<Table> result = new ArrayList<>();
            if(StrUtil.isEmpty(exportList)) {
                return null;
            }
            for(Table table: tables) {
                exportList = exportList + ",";
                if(exportList.contains(table.getName() + ",")) {
                    result.add(table);
                }
            }
            return result;
        }

        // exportType = 2, 导出除了exportList配置外的其他表，表名以分号隔开
        if(ExportTypeEnum.UNNEEDED.getCode().equals(exportType)) {
            List<Table> result = new ArrayList<>();
            for(Table table: tables) {
                if(StrUtil.isEmpty(exportList)) {
                    return tables;
                }
                exportList = exportList + ",";
                if(!exportList.contains(table.getName() + ",")) {
                    result.add(table);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 获取表结构数据
     * @param tableName
     * @return
     */
    private List<Structure> getTableStructure(String tableName) {
        List<Structure> result = new ArrayList<>();
        List<Map<String, String>> dataMap = commonMapper.getTableStructure(tableName);
        if(CollUtil.isNotEmpty(dataMap)) {
            for(int i=0; i < dataMap.size(); i++) {
                Structure structure = new Structure();
                structure.setCode(i + 1);
                structure.setField(dataMap.get(i).get(STRUCTURE_FIELD));
                structure.setType(dataMap.get(i).get(STRUCTURE_TYPE));
                structure.setIsNull(dataMap.get(i).get(STRUCTURE_NULL));
                if(STRUCTURE_ISNULL_NO.equals(structure.getIsNull())) {
                    structure.setIsNull("是");
                } else {
                    structure.setIsNull("否");
                }
                structure.setComment(dataMap.get(i).get(STRUCTURE_COMMENT));
                result.add(structure);
            }
        }
        return result;
    }

}
