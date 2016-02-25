package com.web.core.index;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by shenzhiqiang on 16/2/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class TopIndexTest extends TestCase {

    @Resource
    TopIndex topIndex;

    @Before
    public void testBefore() throws Exception {
        topIndex.buildIndex();
    }

    @Test
    public void testGetTopIndex() throws Exception {
        System.out.println(topIndex.getTopIndex().size());
    }

    @Test
    public void testGetTopNIndex() throws Exception {
        System.out.println(topIndex.getTopNIndex(3).size());

    }


}