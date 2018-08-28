package com.kiilin.genterator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动生成代码的配置
 *
 * @author wangkai
 */
@Data
@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfigEntity extends AutoGenerator {

}
