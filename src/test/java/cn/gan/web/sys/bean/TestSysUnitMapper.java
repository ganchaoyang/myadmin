package cn.gan.web.sys.bean;

import cn.gan.web.sys.bean.mapper.SysUnitMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestSysUnitMapper {

    @Autowired
    SysUnitMapper sysUnitMapper;

    @Test
    public void testCount(){
        List<SysUnit> units = sysUnitMapper.findAll();
        if (units != null && !units.isEmpty())
            Assert.assertTrue(sysUnitMapper
                    .count(units.get(0).getId())> 0);
    }

}
