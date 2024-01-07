# 生成表结构 Word 文档的步骤

## 基本思路
- 通过 SQL 获取数据库的所有表名称
- 通过 SQL 获取表的表结构数据
- 使用 Freemarker 模板将表结构数据生成 Word 文档

## 基本流程
1. 新建数据表 Word 模板文件，如：`templates/tables.docx`
2. 将 `tables.docx` 文件重命名为 2003 版的 XML 文件 `tables.xml`
3. 使用 Notepad++ 打开 `tables.xml`，点击 `插件 -> XMl tools -> Pretty print(libXML)` 进行格式化
4. 变量替换，将 "用户表" 替换为 `${table.name}` 等
5. 执行 `com/ocean/angel/tool/ApplicationTests.java` 中的 `generateTableStructure()` 方法，生成数据库表设计 Word 文档
6. 数据库表设计 Word 文档位置：`/resources/words` 目录下

## 使用指南
1. 根据需要修改 `application.yml` 的配置，包括表导出配置和数据库连接配置
2. 执行 `com/ocean/angel/tool/ApplicationTests.java` 中的 `generateTableStructure()` 方法
3. 到 `/resources/words` 目录下查看生成的数据库表设计 Word 文档

## 本文使用的 Freemarker 语句
- 遍历表名称  
    ```
    <#list tables as table>
        ${table.name}
    </#list>
    ```

- 遍历表结构数据  
    ```
    <#list table.structure as item>
        ${item.code}
        ${item.field}
        ${item.type}
        ${item.isNull}
        ${item.comment}
    </#list>
    ```
    注意：Freemarker 模板不能传递 null，如果数据为 null，可以转换为空字符串。
