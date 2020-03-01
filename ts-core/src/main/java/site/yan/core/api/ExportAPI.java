package site.yan.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import site.yan.core.cache.TraceCache;


public class ExportAPI extends AbstractExportAPI {

    @GetMapping("/trace")
    public RespModel export() {
        RespModel respModel = null;
        try {
            respModel = RespModel.ok(TraceCache.getTraceRecord());
        } catch (Exception e) {
            respModel = RespModel.error("this a api for export data about trace record. ");
        } finally {
            return respModel;
        }
    }
}
