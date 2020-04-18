package site.yan.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import site.yan.core.cache.TraceCache;

/**
 * 上报数据API
 */
public class ExportAPI extends AbstractExportAPI {

    @GetMapping("/trace")
    public RespModel export() {
        RespModel respModel = null;
        try {
            respModel = RespModel.ok(TraceCache.getTraceRecord());
        } catch (Exception exc) {
            respModel = RespModel.error();
        } finally {
            return respModel;
        }
    }
}
