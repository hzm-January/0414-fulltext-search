# 全文检索笔记
lucene &amp; solr &amp; elasticsearch
# 全文检索
## 什么是全文检索
>百度百科：全文数据库是全文检索系统的主要构成部分。所谓全文数据库是将一个完整的信息源的全部内容
转化为计算机可以识别、处理的信息单元而形成的数据集合。全文数据库不仅存储了信息，而且还有对全文数据进行
词、字、段落等更深层次的编辑、加工的功能，而且所有全文数据库无一不是海量信息数据库。
## 全文检索流程
* 索引流程：即采集数据构建文档对象分析文档（分词）创建索引。  
* 搜索流程：即用户通过搜索界面创建查询执行搜索，搜索器从索引库搜索渲染搜索结果。
