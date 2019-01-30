package pl.kkp.core.controller.util;

import org.apache.commons.lang3.StringUtils;

public class EndpointPathBuilder {

    private String hostUrlFormat;
    private String endpointPathFormat;

    public EndpointPathBuilder(String hostUrl, String endpointPath) {
        this.hostUrlFormat = buildHostUrlFormat(hostUrl);
        this.endpointPathFormat = buildEndpointBasePathFormat(hostUrlFormat, endpointPath);
    }

    public String buildEndpointPath(String path, int port) {
        path = appendSlashIfMissing(path);

        return String.format(endpointPathFormat, port, path);
    }

    public String buildServerPath(String path, int port) {
        String hostUrl = String.format(hostUrlFormat, port);
        path = appendSlashIfMissing(path);

        return hostUrl.concat(path);
    }

    private String buildEndpointBasePathFormat(String hostUrlFormat, String endpointPath) {
        endpointPath = appendSlashIfMissing(endpointPath);

        return hostUrlFormat
                .concat(endpointPath)
                .concat("%s");
    }

    private String buildHostUrlFormat(String hostUrl) {
        return hostUrl.concat(":")
                .concat("%d");
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
