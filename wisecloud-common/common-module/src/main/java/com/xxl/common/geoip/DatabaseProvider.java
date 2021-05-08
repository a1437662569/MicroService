package com.xxl.common.geoip;




import com.xxl.common.geoip.exception.GeoIp2Exception;
import com.xxl.common.geoip.model.*;

import java.io.IOException;
import java.net.InetAddress;


public interface DatabaseProvider extends GeoIp2Provider
{

    AnonymousIpResponse anonymousIp(InetAddress ipAddress)
        throws IOException, GeoIp2Exception;

    /**
     * Look up an IP address in a GeoIP2 Connection Type database.
     *
     * @param ipAddress
     *            IPv4 or IPv6 address to lookup.
     * @return a ConnectTypeResponse for the requested IP address.
     * @throws
     *             if there is an error looking up the IP
     * @throws IOException
     *             if there is an IO error
     */
    ConnectionTypeResponse connectionType(InetAddress ipAddress)
        throws IOException, GeoIp2Exception;

    /**
     * Look up an IP address in a GeoIP2 Domain database.
     *
     * @param ipAddress
     *            IPv4 or IPv6 address to lookup.
     * @return a DomainResponse for the requested IP address.
     * @throws
     *             if there is an error looking up the IP
     * @throws IOException
     *             if there is an IO error
     */
    DomainResponse domain(InetAddress ipAddress)
        throws IOException, GeoIp2Exception;

    /**
     * Look up an IP address in a GeoIP2 Enterprise database.
     *
     * @param ipAddress
     *            IPv4 or IPv6 address to lookup.
     * @return an EnterpriseResponse for the requested IP address.
     *             if there is an error looking up the IP
     * @throws IOException
     *             if there is an IO error
     */
    EnterpriseResponse enterprise(InetAddress ipAddress)
        throws IOException, GeoIp2Exception;

    /**
     * Look up an IP address in a GeoIP2 ISP database.
     *
     * @param ipAddress
     *            IPv4 or IPv6 address to lookup.
     * @return an IspResponse for the requested IP address.
     *             if there is an error looking up the IP
     * @throws IOException
     *             if there is an IO error
     */
    IspResponse isp(InetAddress ipAddress)
        throws IOException, GeoIp2Exception;
}
