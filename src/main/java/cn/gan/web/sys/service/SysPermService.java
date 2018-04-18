package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysPerm;

import java.util.List;

public interface SysPermService {

    public List<SysPerm> findAll(boolean tree);

}
