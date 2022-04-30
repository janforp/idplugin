>###<font color = 'aqua'>1.CRHelper-master-CRHelper-MDFreeMarkProcessor.java-9到14</font>
>####1.问题类型
>><font color = 'black'>建议</font>
>####2.指派给
>><font color = 'black'>王尚飞</font>
>####3.级别
>><font color = 'black'>5</font>
>####4.状态
>><font color = 'black'>未解决</font>
>####5.问题代码
>>```java 
>>    Configuration configuration = new Configuration();
>>    String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
>>    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
>>    stringTemplateLoader.putTemplate("MDTemplate", templateContent);
>>    configuration.setTemplateLoader(stringTemplateLoader);
>>    return configuration.getTemplate("MDTemplate");
>>```
>####6.建议写法
>>```java 
>>    Configuration configuration = new Configuration();
>>    String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
>>    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
>>    stringTemplateLoader.putTemplate("MDTemplate", templateContent);
>>    configuration.setTemplateLoader(stringTemplateLoader);
>>    return configuration.getTemplate("MDTemplate");
>>```
>####7.描述
>><font color = 'black'>描述一下</font>
<table bgcolor="#ffebcd" height="20"></table>



>###<font color = 'aqua'>2.CRHelper-master-CRHelper-MDFreeMarkProcessor.java-9到14</font>
>####1.问题类型
>><font color = 'black'>建议</font>
>####2.指派给
>><font color = 'black'>王尚飞</font>
>####3.级别
>><font color = 'black'>5</font>
>####4.状态
>><font color = 'black'>未解决</font>
>####5.问题代码
>>```java 
>>    Configuration configuration = new Configuration();
>>    String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
>>    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
>>    stringTemplateLoader.putTemplate("MDTemplate", templateContent);
>>    configuration.setTemplateLoader(stringTemplateLoader);
>>    return configuration.getTemplate("MDTemplate");
>>```
>####6.建议写法
>>```java 
>>    Configuration configuration = new Configuration();
>>    String templateContent = UrlUtil.loadText(MDFreeMarkProcessor.class.getResource("/template/md.ftl"));
>>    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
>>    stringTemplateLoader.putTemplate("MDTemplate", templateContent);
>>    configuration.setTemplateLoader(stringTemplateLoader);
>>    return configuration.getTemplate("MDTemplate");
>>```
>####7.描述
>><font color = 'black'>描述一下</font>
