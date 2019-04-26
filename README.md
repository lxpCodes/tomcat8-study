# tomcat8-study
<h3>阅读Tomcat8源码而建，主要是添加了日志和注释，学习版本为8.5.12</h3>

<h4>目录结构说明</h4>
<ul>
<li> bin     各种命令，主要是启动和关闭</li>
<li> conf    配置文件</li>
<li> java    源码文件夹
    <ul>
    <li>javax  &nbsp; Java中标准的Servelt规范</li>
    <li>org.apache.catalina &nbsp; Tomcat中的Servlet容器</li>
    <li>org.apache.coyote &nbsp; Tomcat中的连接器</li>
    <li>org.apache.el &nbsp; 正则表达式解析规范</li>
    <li>org.apache.jasper &nbsp; JSP文件解析规范</li>
    <li>org.apache.juli &nbsp; Tomcat的日志系统</li>
    <li>org.apache.naming &nbsp; JNDI的实现</li>
    <li>org.apache.tomcat &nbsp; Tomcat的工具包</li>
    </ul>
</li>
<li> webapps 应用程序</li>
<li> work    运行时的编译后文件，例如jsp编译后的文件，清空work目录，重启Tomcat可以达到清除缓存的作用<li>
</ul>
