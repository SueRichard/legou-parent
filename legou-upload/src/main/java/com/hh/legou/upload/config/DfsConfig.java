package com.hh.legou.upload.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * 配置类
 *
 * @author hh
 * @version 1.0
 * @time 2023年9月2日14:36:49
 */
@Configuration
@Import(FdfsClientConfig.class)
//jmx重复注册bean问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class DfsConfig {
}
