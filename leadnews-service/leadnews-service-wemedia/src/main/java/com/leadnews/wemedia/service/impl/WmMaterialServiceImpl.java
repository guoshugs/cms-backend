package com.leadnews.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.wemedia.dto.WmMaterialPageRequestDto;
import com.leadnews.wemedia.pojo.WmMaterial;
import com.leadnews.wemedia.mapper.WmMaterialMapper;
import com.leadnews.wemedia.service.WmMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @description <p>自媒体图文素材信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.service.impl
 */
@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    /* 读取配置文件中的内容用value注解
    * 既然配置文件配置了，这里的冒号以及后面就可以不写，这里是多写了 */
    @Value("${fastdfs-server:你没有配置fastDFS的地址}") // :的意思：如果没有值，就适用冒号后面的值来代替
    private String fastDFSServer;

    /**
     * 素材分页查询
     * @param dto
     * @return
     */
    @Override
    public PageResultVo findPage(WmMaterialPageRequestDto dto) {
        //1. 设置分页参数
        IPage<WmMaterial> pageInfo = new Page<>(dto.getPage(),dto.getSize());
        //2. 构建查询条件
        LambdaQueryWrapper<WmMaterial> lqw = new LambdaQueryWrapper<>();
        lqw.eq(null != dto.getIsCollection()&&dto.getIsCollection()==1, WmMaterial::getIsCollection, dto.getIsCollection());
        //3. 查询
        page(pageInfo, lqw);
        // 获取查询后结果集，给图片地址补全路径
        pageInfo.getRecords().stream().forEach(m->m.setUrl(fastDFSServer+m.getUrl()));
        //4. 封装结果返回
        return PageResultVo.pageResult(dto.getPage(),dto.getSize(),pageInfo.getTotal(), pageInfo.getRecords());
    }
}
