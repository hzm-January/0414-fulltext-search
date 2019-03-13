# solr
## solr介绍
+ core :
## solr安装
环境要求：
+ Apache Solr在Java 8或更高版本上运行
+ 建议始终使用Java VM的最新更新版本。
+ 对于所有Java版本，强烈建议不要使用实验性-XXJVM选项
+ CPU，磁盘和内存要求基于实现Solr的许多选择（文档大小，文档数量和检索的命中数等等）
### solr 
1. 下载官方安装包
2. 解压 `unzip -q solr-7.7.0.zip`
3. 启动 `bin/solr start`  
    3.1 Solr UI **http://ip:port/solr/index.html**
    > http://ip:port/solr 不能访问，暂时不知如何修改
    
    3.2 add core
    >instanceDir and dataDir need to exist before you can create the core  
    需要先创建instanceDir和dataDir目录  
    如果直接创建会报错
    ```
    Error CREATEing SolrCore 'quickstart-first-core': Unable to create core [quickstart-first-core] Caused by: Can't find resource 'solrconfig.xml' in classpath or '/usr/local/develop/solr-7.7.0/server/solr/quickstart-first-core'
    ```
4. 重启 `bin/solr restart`    
5. 停止 `bin/solr stop`  
### solr cloud 
1. 下载官方安装包
2. 解压 `unzip -q solr-7.7.0.zip`
3. `bin/solr start -e cloud`  
3.1 询问需要运行多少个节点，默认即可
``` 
To begin, how many Solr nodes would you like to run in your local cluster? (specify 1-4 nodes) [2]:
```
3.2 询问第一个节点运行的端口，如果8983没有被占用那么回车即可，如果8983已被占用，会要求选择其他端口。
```
Ok, let's start up 2 Solr nodes for your example SolrCloud cluster.
Please enter the port for node1 [8983]: 
```
3.3 询问第二个节点运行的端口，如果7574没有被占用那么回车即可，如果7574已被占用，会要求选择其他端口。
```
Please enter the port for node2 [7574]: 
```
3.4 solr初始化自己
出现错误（无操作权限）
```
ERROR: Failed to start Solr using command: "/usr/local/develop/solr/bin/solr" start -cloud -p 8983 -s "/usr/local/develop/solr/example/cloud/node1/solr" Exception : org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
```
解决方案： 使用`bin/solr start -e cloud -force`重新强制启动
出现错误（此时只启动了8983节点，命令终止运行，暂时没处理该问题）
```
ERROR: Did not see Solr at http://localhost:8983/solr come online within 30

```
## 配置中文解析器
1. 下载jar包
2. 将jar包复制到solr-7.4.0/server/solr-webapp/webapp/WEB-INF/lib
3. 在managed-schema中添加以下配置
```
<fieldType name="worddata_ik" class="solr.TextField">      
    <analyzer type="index">          
        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="false"/>          
        <filter class="solr.LowerCaseFilterFactory"/>     
    </analyzer>      
    <analyzer type="query">          
        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="true"/>         
        <filter class="solr.LowerCaseFilterFactory"/>      
    </analyzer>  
</fieldType>
```
## DIH (Data Import Handler数据导入处理)
从xml、pdf、关系型数据库中导入数据并建立索引，达到快速搜索的目的
### DIH 数据库导入配置
DIH的配置对应core，每个core都应该有自己的配置文件
1. solrconfig.xml
```
<requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
  <lst name="defaults">
    <str name="config">db-data-config.xml</str>
  </lst>
</requestHandler>
```
1.1 找不到dataimportHandler问题  
` Caused by: java.lang.ClassNotFoundException: org.apache.solr.handler.dataimportHandler `  
+ 问题原因：找不到包solr-dataimporthandler-7.7.0.jar
+ 解决方案
    + 从solr-7.7.0/dist目录中拷贝 solr-dataimporthandler-7.7.0.jar 到lib下（如果是自带容器启动该lib在server/solr-webapp/webapp/WEB-INF下）
    + 编辑solrconfig.xml，添加以下内容（确保以下dir目录中有该包）
    >\<lib dir="../../../contrib/dataimporthandler/lib" regex=".\*\\.jar" />  
     \<lib dir="../../../dist/" regex="solr-dataimporthandler-.\*\\.jar" />
2. db-data-config.xml （如果没有可以从solr-7.7.0/example/example-DIH/solr/db/conf目录拷贝）
```
<dataConfig>
    <dataSource driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://ip:3306/houzm" user="root" password="root" />
    <document name="sentence">
      <entity name="sentence" pk="id" query="select * from test">
        <field column="id" name="id" />
        <field column="author" name="author" />
        <field column="sentence" name="sentence" />
        <field column="creator" name="creator" />
        <field column="create_time" name="create_time" />
        <field column="modifier" name="modifier"/>
        <field column="modify_time" name="modify_time"/>
      </entity>
    </document>
</dataConfig>
```
2.1 找不到
+ 问题原因：找不到mysql-connector-java-8.0.12.jar包
+ 解决方案：
    + 将添加到lib目录中
    + 编辑solrconfig.xml，添加一下内容（确保以下dir目录中有该包）
    >\<lib dir="../../../lib/" regex="mysql-connector-java-8.0.12.jar" />
## 参考
solr 配置文件说明： https://blog.csdn.net/vtopqx/article/details/73224510  
solr core配置：https://www.jianshu.com/p/4e86df9532d9
https://blog.csdn.net/u010510107/article/details/81051795  
找不到handler   
https://blog.csdn.net/qq_26975307/article/details/79073029
https://blog.csdn.net/gisredevelopment/article/details/50443781

https://blog.csdn.net/b_evan/article/details/79754491



