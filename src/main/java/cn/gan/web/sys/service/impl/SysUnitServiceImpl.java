package cn.gan.web.sys.service.impl;

import cn.gan.framework.util.Strings;
import cn.gan.web.sys.bean.SysUnit;
import cn.gan.web.sys.bean.mapper.SysUnitMapper;
import cn.gan.web.sys.service.SysUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public SysUnit findById(String id) {
        return sysUnitMapper.findById(id);
    }

    @Override
    public SysUnit findByPidAndName(String parentId, String name) {
        return sysUnitMapper.findByPidAndName(parentId, name);
    }

    @Override
    public boolean isExistById(String id) {
        int count = sysUnitMapper.count(id);
        if (count > 0)
            return true;
        return false;
    }

    @Override
    public int addUnit(SysUnit unit) {
        String path = "";
        // 首先判断是否有父级单位。
        String parentId = unit.getParentId();
        // 不同的计算code的方式。
        if (parentId != null && parentId.length() > 0){
            SysUnit parent = sysUnitMapper.findById(parentId);
            path = parent.getUnitCode() + Strings.leftComplement(
                    (sysUnitMapper.countChildren(parentId)+1)+"", '0', 4);
            parent.setHasChildren(true);
            updateIgnoreNull(parent);
        }else {
            path = Strings.leftComplement((sysUnitMapper.countRootUnit()+1)+"", '0', 4);
        }
        unit.setPath(path);
        unit.setUnitCode(path);
        unit.setCreateTime(new Date());
        unit.setUpdateTime(new Date());
        return sysUnitMapper.insert(unit);
    }

    @Override
    public int updateIgnoreNull(SysUnit unit) {
        return sysUnitMapper.updateIgnoreNull(unit);
    }

    @Override
    public int deleteWithAllChildren(SysUnit unit) {
        return sysUnitMapper.deleteLikeWithPath(unit.getPath() + "%");
    }

    @Override
    public int countChildrenNumber(String id) {
        return sysUnitMapper.countChildren(id);
    }
}
