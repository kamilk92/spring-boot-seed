package pl.kkp.core.controller.util;

import org.apache.commons.lang3.StringUtils;

public class EndpointPathBuilder {

    private String endpointPathFormat;

    public EndpointPathBuilder(String hostUrl, String endpointPath) {
        this.endpointPathFormat = buildEndpointBasePathFormat(hostUrl, endpointPath);
    }

    public String buildEndpointPath(String path, int port) {
        path = appendSlashIfMissing(path);

        return String.format(endpointPathFormat, port, path);
    }

    private String buildEndpointBasePathFormat(String hostUrl, String endpointPath) {
        endpointPath = appendSlashIfMissing(endpointPath);

        return hostUrl
                .concat(":")
                .concat("%d")
                .concat(endpointPath)
                .concat("%s");
    }

    private String appendSlashIfMissing(String path) {
        if (path.startsWith("/")) {
            return path;
        } else if (StringUtils.isNotEmpty(path)) {
            return "/".concat(path);
        }

        return "";
    }
}
