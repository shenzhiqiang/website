package com.web.core.tool;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shenzhiqiang on 16/2/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class ToolClassTest extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @Test
    public void testGetImgFilename() throws Exception {
        System.out.println(ToolClass.getImgFilename("http://mzx-img.sinacloud.net/f3b0fede4ef7abb3f23019af4c76c69b1456224660966.png"));
    }
}