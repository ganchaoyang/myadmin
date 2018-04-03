package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.bean.mapper.SysUserMapper;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(value = "sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void addUser(SysUser sysUser) {
        sysUser.setSalt(UUID.randomUUID().toString().replace("-", ""));
        sysUser.setPassword((new Sha256Hash(sysUser.getPassword(), sysUser.getSalt())).toHex());
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateTime(new Date());
        System.out.println(JSON.toJSONString(sysUser));
        // 首先是插入用户。
        sysUserMapper.insert(sysUser);
        if (sysUser.getRoles() != null && !sysUser.getRoles().isEmpty())
            sysUserMapper.addRoles(sysUser, sysUser.getRoles());
    }

    @Override
    public SysUser findByLoginName(String loginName, boolean link) {
        if (!link){
            return sysUserMapper.findByLoginName(loginName);
        }
        return sysUserMapper.findByLoginNameWithLinks(loginName);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    @Override
    public void deleteById(String id) {
        sysUserMapper.deleteAllRoles(id);
        sysUserMapper.delete(id);
    }

    @Override
    public int updateIgnoreNull(SysUser sysUser) {
        // 首先查询该用户。
        SysUser tobeUpdate = sysUserMapper.findById(sysUser.getId());
        if (tobeUpdate == null)
            return 0;
        // 判断密码是否为空。
        if (sysUser.getPassword() != null && sysUser.getPassword().trim().length() > 0){
            sysUser.setPassword((new Sha256Hash(sysUser.getPassword(),
                    tobeUpdate.getSalt()).toHex()));
        }
        // 更新。
        return sysUserMapper.updateIgnoreNull(sysUser);
    }
}
