package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author 17983
 * @date 2022/8/10 10:13
 * @ SJY
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class mailTest
{
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail()
    {
        mailClient.sendMail("1772399843@qq.com", "test", "hello spring mail");
    }

    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","sunday");

        String process = templateEngine.process("/mail/demo", context);

        System.out.println(process);
    }
}
