《生成表结构word文档的步骤》

1. 基本思路：通过sql获取数据库的所有表名称 -> 通过sql获取表的表结构数据 -> 通过freemarker模板将表结构数据生成word文档

2. 基本流程
1） 新建数据表word模板文件，如：templates/tables.docx
2） tables.docx文件重命名为2003版的xml文件tables.xml
3） 用notepad++打开tables.xml，点击 插件 -> XMl tools-> Pretty print(libXML)，格式化xml文件
4） 变量替换，"用户表" -> ${table.name} 等
5） 执行com/ocean/angel/tool/ApplicationTests.java generateTableStructure() 方法，生成数据库表设计word文档
6） 数据库表设计word文档位置：/resources/words目录下

3. 使用指南
1）根据需要，修改application.yml的配置，表导出配置和数据库连接配置
2）执行com/ocean/angel/tool/ApplicationTests.java generateTableStructure() 方法
3） 到/resources/words目录下，查看生成的数据库表设计word文档

4. 本文使用的freemarker语句：

1）遍历表名称
<#list tables as table>
    ${table.name}
</#list>

2）遍历表结构数据
<#list table.structure as item>
    ${item.code}
    ${item.field}
    ${item.type}
    ${item.isNull}
    ${item.comment}
</#list>

注意:freemarker模板不能传null, 如果数据为null，可以转换成空字符串。



