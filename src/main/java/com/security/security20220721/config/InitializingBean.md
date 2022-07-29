SpringBoot - InitializingBean的作用是什么？

cloneme01

于 2021-12-30 21:20:34 发布

930
收藏 5
分类专栏： SpringBoot 文章标签： spring java spring boot
版权

SpringBoot
专栏收录该内容
52 篇文章0 订阅
订阅专栏
作用
InitializingBean的作用是Bean注入到Spring容器且初始化后，执行特定业务化的操作。Spring允许容器中的Bean，在Bean初始化完成后或者Bean销毁前，执行特定业务化的操作，常用的实现方式有以下三种：

通过实现InitializingBean/DisposableBean接口来处理初始化后/销毁前的操作；
通过标签的init-method/destroy-method属性处理初始化后/销毁前的操作；
在指定方法上加上@PostConstruct或@PreDestroy注解来处理初始化后/销毁前的操作。
实现
如果采用实现InitializingBean接口的方式去执行特定业务化的操作，则需要重写afterPropertiesSet这仅有的一个方法。

代码
package com.hadoopx.drools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

@Slf4j
@Data
public class KieAccessor implements InitializingBean {
private String path;
private String mode;
private Long update;
private String listener;
private String verify;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (StringUtils.isEmpty(path)) {
            log.error("PLEASE SET THE RULE'S PATH: (spring.drools.path = XXX).");
        }
    }
}
————————————————
版权声明：本文为CSDN博主「cloneme01」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/goodjava2007/article/details/122245457