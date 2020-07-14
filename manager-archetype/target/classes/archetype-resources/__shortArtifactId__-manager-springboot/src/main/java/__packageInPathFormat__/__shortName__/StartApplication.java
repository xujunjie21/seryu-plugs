#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${shortName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: ${PROJECT_NAME}
 * @description: 启动器
 * @author: ${USER}
 * @create: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"org.seryu.framework.*","${package}.${shortName}.*"})
public class StartApplication
{
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);
    }
}
