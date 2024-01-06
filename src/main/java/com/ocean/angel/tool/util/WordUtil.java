package com.ocean.angel.tool.util;

import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @desc: word生成工具类
 *
 * @author: jaime.yu
 * @time: 2023/5/20 14:46
 */
@Service
public class WordUtil {

    /**
     * 生成word的文件目录
     */
    public static String WORD_PATH_PREFIX = "src/main/resources/word/";

    /**
     * 生成word文档方法
     *
     * @param dataMap      要填充的数据
     * @param templateName 模版名称
     * @param fileName     要输出的文件路径
     * @throws Exception 抛出的异常
     */
    public static void generateWord(Map<String, Object> dataMap, String templateName, String fileName) throws Exception {

        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setOutputFormat(XMLOutputFormat.INSTANCE);

        // 设置FreeMarker生成Word文档所需要的模板的路径，此处把模版文件都放在resources下的templates中
        configuration.setClassForTemplateLoading(WordUtil.class, "/templates");

        // 设置FreeMarker生成Word文档所需要的模板
        Template template = configuration.getTemplate(templateName, "UTF-8");

        // 创建一个Word文档的输出流
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(WORD_PATH_PREFIX + fileName)), StandardCharsets.UTF_8));

        // FreeMarker使用Word模板和数据生成Word文档
        template.process(dataMap, out);
        out.flush();
        out.close();
    }

}
