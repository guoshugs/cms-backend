package com.leadnews.behaviour.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description <p>APP行为实体,一个行为实体可能是用户或者设备，或者其它 Mapper 接口</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.mapper
 */
@Repository
public interface ApBehaviorEntryMapper extends BaseMapper<ApBehaviorEntry> {

    @Select("select * from `ap_behavior_entry` where entry_id=#{userId} and type=#{type}")
    ApBehaviorEntry findByEntryIdAndType(@Param("userId") Long userId, @Param("type") int type);
}
