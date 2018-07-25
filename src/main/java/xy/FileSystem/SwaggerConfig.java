package xy.FileSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("xy.FileSystem.Controller")) //对此包中api生成文档
                .apis(RequestHandlerSelectors.any()) //对所有包监控
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xyFS私有文件云存储OSS服务")
                .description("私有文件云存储软件OSS，高可用File System文件存储微服务系统，对文件上传、下载、分类、共享、分组、审计、统计等操作进行业务剥离,支持企业内多项目，提供统一的oss私有文件服务")
                .termsOfServiceUrl("https://gitee.com/475660/xyFS")
                .contact("475660@qq.com")
                .version("1.0")
                .build();
    }

}