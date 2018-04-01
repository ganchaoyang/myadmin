package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysUnit;
import cn.gan.web.sys.bean.mapper.SysUnitMapper;
import cn.gan.web.sys.service.SysUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysUnitService")
public class SysUnitServiceImpl implements SysUnitService {

    @Autowired
    private SysUnitMapper sysUnitMapper;

    @Override
    public List<SysUnit> findAll(boolean tree) {
        List<SysUnit> units = sysUnitMapper.findAll();
        if (tree){
            units = SysUnit.toTrees(units);
        }
        return units;
    }
}
