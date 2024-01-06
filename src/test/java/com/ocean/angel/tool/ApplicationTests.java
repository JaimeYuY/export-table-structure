package com.ocean.angel.tool;

import com.ocean.angel.tool.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Resource
    private CommonService commonService;

    @Test
    void generateTableStructure() {
        // 导出配置的表，生成word表结构文档，文档在/resources/word目录下
        commonService.generateTableStructure();
    }

}
