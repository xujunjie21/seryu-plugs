#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${shortName}.dubbo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @program: ${PROJECT_NAME}
 * @description: dubbo配置
 * @author: ${USER}
 * @create: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 **/
@Configuration
@ImportResource({"classpath*:dubbo/${shortArtifactId}/reference/dubbo.xml"})
public class DemoDubboReferenceConfig
{

}
