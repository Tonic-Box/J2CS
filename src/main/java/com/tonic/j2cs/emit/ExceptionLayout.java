package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.j2cs.model.LoweredMethod;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps the dispatcher state machine used to emit try/catch. Each handler block gets a 1-based
 * state; each original block gets a region id identifying its ordered list of applicable
 * handlers (handlers, in table order, whose protected block set contains it). Blocks sharing
 * an identical handler list share a region; uncovered blocks are region -1.
 */
public final class ExceptionLayout {

    private final Map<IRBlock, Integer> stateOf = new LinkedHashMap<>();
    private final Map<IRBlock, Integer> regionOf = new LinkedHashMap<>();
    private final List<List<ExceptionHandler>> regions = new ArrayList<>();

    public ExceptionLayout(LoweredMethod lowered) {
        List<ExceptionHandler> handlers = lowered.ir().getExceptionHandlers();
        int state = 1;
        for (ExceptionHandler handler : handlers) {
            IRBlock handlerBlock = handler.getHandlerBlock();
            if (handlerBlock != null && !stateOf.containsKey(handlerBlock)) {
                stateOf.put(handlerBlock, state++);
            }
        }
        Map<List<ExceptionHandler>, Integer> regionByHandlers = new LinkedHashMap<>();
        for (IRBlock block : lowered.blockOrder()) {
            if (!lowered.originalBlocks().contains(block)) {
                continue;
            }
            List<ExceptionHandler> applicable = new ArrayList<>();
            for (ExceptionHandler handler : handlers) {
                if (handler.getTryBlocks() != null && handler.getTryBlocks().contains(block)) {
                    applicable.add(handler);
                }
            }
            if (applicable.isEmpty()) {
                regionOf.put(block, -1);
                continue;
            }
            Integer region = regionByHandlers.get(applicable);
            if (region == null) {
                region = regions.size();
                regions.add(applicable);
                regionByHandlers.put(applicable, region);
            }
            regionOf.put(block, region);
        }
    }

    public boolean hasHandlers() {
        return !stateOf.isEmpty();
    }

    public Map<IRBlock, Integer> stateOf() {
        return stateOf;
    }

    public int regionOf(IRBlock block) {
        return regionOf.getOrDefault(block, -1);
    }

    public List<List<ExceptionHandler>> regions() {
        return regions;
    }
}
