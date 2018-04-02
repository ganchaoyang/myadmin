package cn.gan.web.sys.bean;

import cn.gan.web.sys.bean.mapper.SysUnitMapper;
import cn.gan.web.sys.bean.mapper.SysUserMapper;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestSysUserMapper {

    @Autowired
    private SysUnitMapper sysUnitMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private Logger logger = LoggerFactory.getLogger(TestSysUserMapper.class);

    @Test
    public void testInsert(){
        List<SysUnit> units = sysUnitMapper.findAll();
        List<SysUser> users = sysUserMapper.findAll();
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("insert_test");
        sysUser.setMobile("18451117878");
        sysUser.setNickname("insert_test");
        sysUser.setEmail("ganchaoyang@xhbds.cn");
        sysUser.setUnitId(units.get(0).getId());
        sysUser.setSalt(UUID.randomUUID().toString().replace("-", ""));
        sysUser.setPassword((new Sha256Hash("1111111", sysUser.getSalt())).toHex());
        sysUser.setOpBy(users.get(0).getId());
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.insert(sysUser);
        String id = sysUser.getId();
        logger.debug("insert user's id : {}", id);
        // 验证是否插入进去。
        SysUser toCheck = sysUserMapper.findById(id);
        logger.debug("insert user is : {}", JSON.toJSONString(toCheck));
        Assert.assertEquals(sysUser.getLoginName(), toCheck.getLoginName());
        // 删除用户。
        sysUserMapper.delete(id);
        // 验证是否删除成功。
        SysUser afterDel = sysUserMapper.findById(id);
        Assert.assertNull(afterDel);
    }

}
