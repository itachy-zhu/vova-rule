package com.vova.rule.common.configuration;

import com.google.common.collect.Maps;
import com.vova.rule.common.vo.BlockReasonVo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * block-list store
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */

@Component
public class BlockListStore<T> {

    private final Map<Integer, BlockReasonVo<T>> blockReasonMap = Maps.newConcurrentMap();

    public boolean isBlocked(Integer userId) {
        return blockReasonMap.containsKey(userId);
    }

    public void setBlockReason(Integer key, BlockReasonVo<T> blockReasonVo) {
        blockReasonMap.put(key, blockReasonVo);
    }

    public BlockReasonVo<T> getBlockReason(Integer userId) {
        return blockReasonMap.get(userId);
    }
}
