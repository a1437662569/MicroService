package com.xxl.common.util;


import com.xxl.common.enums.CommonConsts;
import com.xxl.common.geoip.DatabaseReader;
import com.xxl.common.geoip.model.CityResponse;
import com.xxl.common.geoip.model.IspResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;

@Slf4j
@Component
public class GeoipUtil {
    private static final String GEOIP_FILE_PATH = "/maxmind-db/GeoIP2-City.mmdb";

    private static final String GEOIP_ISP_FILE_PATH = "/maxmind-db/GeoIP2-ISP.mmdb";

    private File geoipFile;

    public String readCountry(String netAddr) {
        String addr = "";
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            CityResponse response = reader.city(InetAddress.getByName(netAddr));
            addr = response.getCountry().getNames().get("en");
            return addr;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
    }

    public String readCountryByLang(String netAddr, String lang) {
        String addr = "";
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            CityResponse response = reader.city(InetAddress.getByName(netAddr));
            addr = response.getCountry().getNames().get(lang);
            return addr;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
    }

    /**
     * @param netAddr
     * @param lang    {
     *                "de": "Shenzhen",
     *                "ru": "Шэньчжэнь",
     *                "pt-BR": "Shenzhen",
     *                "ja": "深セン市",
     *                "en": "Shenzhen",
     *                "fr": "Shenzhen",
     *                "zh-CN": "深圳市",
     *                "es": "Shenzhen"
     *                }
     * @return
     */
    public String readCountryAndCityByLang(String netAddr, String lang) {
        String country = readCountryByLang(netAddr, StringUtil.isEmpty(lang) ? CommonConsts.ZH_CN : lang);
        String city = readCityByLang(netAddr, StringUtil.isEmpty(lang) ? CommonConsts.ZH_CN : lang);
        return country + "_" + city;
    }


    public String readCity(String netAddr) {
        String addr = "";
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            CityResponse response = reader.city(InetAddress.getByName(netAddr));
            addr = response.getCity().getNames().get("en");
            return addr;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
    }

    public String readCityByLang(String netAddr, String lang) {
        String addr = "";
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            CityResponse response = reader.city(InetAddress.getByName(netAddr));
            addr = response.getCity().getNames().get(lang);
            return addr;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
    }

    public CityResponse readCityResponse(String netAddr) {
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            CityResponse response = reader.city(InetAddress.getByName(netAddr));
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }

        }

    }


    public String readIsp(String netAddr) {
        String isp = "全部";
        DatabaseReader reader = null;
        try {
            URL resource = GeoipUtil.class.getResource(GEOIP_ISP_FILE_PATH);
            this.geoipFile = new File(resource.toURI());
            reader = new DatabaseReader.Builder(this.geoipFile).build();
            IspResponse response = reader.isp(InetAddress.getByName(netAddr));
            isp = response.getIsp();
            return isp;
        } catch (Exception e) {
            log.error(e.getMessage());
            return isp;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }

        }

    }


    public static void main(String[] args) {
        String ip = "177.118.188.175,191.13.205.119,143.255.189.59,191.220.94.232,189.37.68.154,187.25.54.0,191.246.2.53,191.186.192.140,45.225.86.46,179.220.210.196";
        String[] ips = ip.split(",");

        for (String itemIP : ips) {
            GeoipUtil geoipUtil = new GeoipUtil();
            System.out.println(String.format("%s", geoipUtil.readCity(itemIP)));
            //System.out.println(String.format("IP:%s,国家:%s,城市:%s",itemIP,geoipUtil.readCountry(itemIP),geoipUtil.readCity(itemIP)));
        }

    	/*System.out.println(geoipUtil.readCity(ip));
    	System.out.println(geoipUtil.readCountry(ip));
    	System.out.println(geoipUtil.readIsp(ip));
    	CityResponse response = geoipUtil.readCityResponse(ip);
		System.out.println(response);
    	System.out.println(response.getCity().getNames().get("en")); 
		System.out.println(response.getCountry().getNames().get("en"));	*/
    }


}
