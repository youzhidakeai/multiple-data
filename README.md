# multiple-data
1. 项目纯练手
2. applaction.yml 中需要配置相应的参数.
3. 本人是使用ClickHouse + Mysql做的多数据源配置. 如果需要更改成相对应的 请在spring.datasource 下面建立数据源 然后在
   DataSourceConfiguration.class 中新增数据源的DataSource, 然后注入新的DataSource 并配置.
4. 项目中的binlog监听以及对应binlog数据和Entity对应相关功能来自于慕课网https://coding.imooc.com/class/310.html 课程老师的讲解.
5. pom.xml 有许多本项目用不上的jar 例如 redis,spring-task 等可自行删除
在此感谢张勤一老师
