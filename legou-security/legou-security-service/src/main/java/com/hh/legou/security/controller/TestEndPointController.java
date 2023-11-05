//package com.hh.legou.security.controller;
//
//import com.hh.legou.security.utils.BPwdEncoderUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//public class TestEndPointController {
//
//    Logger logger = LoggerFactory.getLogger(TestEndPointController.class);
//
///*    @Autowired
//    private UserDao userRepository;*/
//
//    @GetMapping("/product/{id}")
//    public String getProduct(@PathVariable String id) {
//
//        String dbpasswor = "$2a$10$HBX6q6TndkgMxhSEdoFqWOUtctaJEMoXe49NWh8Owc.4MTunv.wXa";
//
//        logger.info("判断两个密码是否相等 " + (BPwdEncoderUtil.matches("123456", dbpasswor)));
//
//        return "product id : " + id;
//    }
//
//    @GetMapping("/order/{id}")
//    public String getOrder(@PathVariable String id) {
//        return "order id : " + id;
//    }
//
//    @GetMapping("/book/{id}")
//    public String getBook(@PathVariable String id) {
//        return "book id : " + id;
//    }
//
//    @GetMapping("/anno/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String getAnno(@PathVariable String id) {
//        return "admin id : " + id;
//    }
//
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    @RequestMapping("/hello")
//    public String hello() {
//        return "hello you";
//    }
//
//
//    @GetMapping("/getPrinciple")
//    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
//        logger.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
//        logger.info(oAuth2Authentication.toString());
//        logger.info("principal.toString() " + principal.toString());
//        logger.info("principal.getName() " + principal.getName());
//        logger.info("authentication: " + authentication.getAuthorities().toString());
//
//        return oAuth2Authentication;
//    }
//
//}
