package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.bean.mapper.SysRoleMapper;
import cn.gan.web.sys.bean.mapper.SysUserMapper;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

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
        }else {
            sysUser.setPassword(tobeUpdate.getPassword());
        }
        // 更新。
        return sysUserMapper.updateIgnoreNull(sysUser);
    }

    @Override
    public List<SysPerm> findPermsOfUser(String userId, boolean tree) {
        // 查询这个用户的角色信息。
        List<SysRole> sysRoles = sysRoleMapper.findByUserId(userId);
        Map<String, SysPerm> temp = new HashMap<>();
        sysRoles.stream().forEach(one -> {
            if (one.getPerms() != null && !one.getPerms().isEmpty()){
                one.getPerms().stream().forEach(perm -> {
                    temp.put(perm.getId(), perm);
                });
            }
        });
        List<SysPerm> result = new ArrayList<>();
        if (!temp.isEmpty()){
            result = temp.entrySet().stream().map(one -> one.getValue()).collect(Collectors.toList());
        }
        if (tree)
            result = SysPerm.toTrees(result);
        return result;
    }
}
