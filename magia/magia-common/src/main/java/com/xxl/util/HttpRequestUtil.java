package com.xxl.util;

import com.xxl.common.enums.CommonConsts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpRequestUtil
{
    private Logger logger = LogManager.getLogger(HttpRequestUtil.class);

    private static HttpRequestUtil httpRequestUtil = new HttpRequestUtil();

    public static HttpRequestUtil getInstance()
    {
        return httpRequestUtil;
    }

    public String executePostAndGetString(String targetUrl, Map<String, String> params)
    {
        HttpClient httpClient = null;
        try
        {
            httpClient = getHttpClient(CommonConsts.THREE_THOUSAND_STR);

            HttpEntity entity = executePost(targetUrl, params, httpClient);

            return EntityUtils.toString(entity, CommonConsts.UTF8);
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            return null;
        }
        catch (Exception e)
        {

            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public String executePostByStream(String targetUrl, String request)
    {
        HttpClient httpClient = null;
        try
        {

            if (request != null)
            {
                httpClient = getHttpClient(CommonConsts.THREE_THOUSAND_STR);
                HttpEntity entity = executePostInStream(targetUrl, request, httpClient);
                return EntityUtils.toString(entity, CommonConsts.UTF8);
            }
            return null;
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public String executePostByStreamLong(String targetUrl, String request)
    {
        HttpClient httpClient = null;
        try
        {

            if (request != null)
            {
                httpClient = getHttpClient(CommonConsts.THIRTRY_THOUSAND_STR);
                HttpEntity entity = executePostInStream(targetUrl, request, httpClient);
                return EntityUtils.toString(entity, CommonConsts.UTF8);
            }
            return null;
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public String executePostAndGetStringTwo(String targetUrl, Map<String, String> params)
    {
        HttpClient httpClient = null;
        try
        {
            httpClient = getHttpClient(CommonConsts.THIRTRY_THOUSAND_STR);
            HttpEntity entity = executePost(targetUrl, params, httpClient);
            String content = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null)
            {
                stringBuffer.append(str);
            }
            content = stringBuffer.toString();
            return content;
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            return null;
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }

    private HttpEntity executePostInStream(String targetUrl, String params, HttpClient httpClient)
        throws ClientProtocolException, IOException
    {
        this.logger.debug("url:" + targetUrl);

        HttpPost httpPost = new HttpPost(targetUrl);

        httpPost.addHeader("Content-Type", "application/json");

        StringEntity se = new StringEntity(params, CommonConsts.UTF8);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200)
        {
            throw new RuntimeException(String.valueOf(statusCode));
        }
        HttpEntity entity = response.getEntity();
        return entity;

    }

    private HttpEntity executePost(String targetUrl, Map<String, String> params,
                                   HttpClient httpClient)
        throws ClientProtocolException, IOException
    {
        this.logger.debug("url:" + targetUrl);
        HttpPost httpPost = new HttpPost(targetUrl);

        setParams(httpPost, params);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200)
        {
            throw new RuntimeException(String.valueOf(statusCode));
        }
        HttpEntity entity = response.getEntity();
        return entity;

    }

    private void setParams(HttpPost httpPost, Map<String, String> params)
        throws UnsupportedEncodingException
    {
        List nvps = new ArrayList();
        if (params != null)
        {
            for (String key : params.keySet())
            {
                nvps.add(new BasicNameValuePair(key, (String)params.get(key)));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, CommonConsts.UTF8));
    }

    private HttpClient getHttpClient(String time)
    {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);
        params.setParameter("http.connection.timeout", Integer.valueOf(time));
        params.setParameter("http.socket.timeout", Integer.valueOf(time));
        HttpClient httpClient = new DefaultHttpClient(params);
        try
        {
            // Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            // Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager()
            {

                public void checkClientTrusted(X509Certificate[] xcs, String string)
                    throws CertificateException
                {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String string)
                    throws CertificateException
                {}

                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] {tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            // register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
        }
        catch (Exception e)
        {
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e);
        }

        return httpClient;
    }
    
    public String executeGetByStream(String targetUrl, String request)
    {
        HttpClient httpClient = null;
        try
        {

            if (request != null)
            {
                httpClient = getHttpClient(CommonConsts.THREE_THOUSAND_STR);
                HttpEntity entity = executeGetInStream(targetUrl, request, httpClient);
                return EntityUtils.toString(entity, CommonConsts.UTF8);
            }
            return null;
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    public String executeGetWithHeadByStream(String targetUrl, String request , Map headers )
    {
        HttpClient httpClient = null;
        try
        {

            if (request != null)
            {
                httpClient = getHttpClient(CommonConsts.THREE_THOUSAND_STR);
                HttpEntity entity = executeGetWithHeadInStream(targetUrl, request, httpClient,headers);
                return EntityUtils.toString(entity, CommonConsts.UTF8);
            }
            return null;
        }
        catch (HttpHostConnectException e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    
    
    private HttpEntity executeGetInStream(String targetUrl, String params, HttpClient httpClient)
            throws ClientProtocolException, IOException
        {
            this.logger.debug("url:" + targetUrl);

            HttpGet httpGet = new HttpGet(targetUrl);

            httpGet.addHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException(String.valueOf(statusCode));
            }
            HttpEntity entity = response.getEntity();
            return entity;

        }
    
    
    private HttpEntity executeGetWithHeadInStream(String targetUrl, String params, HttpClient httpClient ,Map headers)
            throws ClientProtocolException, IOException
        {
            this.logger.debug("url:" + targetUrl);

            HttpGet httpGet = new HttpGet(targetUrl);

            httpGet.addHeader("Content-Type", "application/json");
            
            if (null!=headers)
            {
                Iterator<Map.Entry<String, String>> entries = headers.entrySet().iterator(); 
                while (entries.hasNext()) { 
                  Map.Entry<String, String> entry = entries.next(); 
                  httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException(String.valueOf(statusCode));
            }
            HttpEntity entity = response.getEntity();
            return entity;

        }
    
	private HttpEntity executeGet(String targetUrl, HttpClient httpClient)
			throws ClientProtocolException, IOException {
		this.logger.debug("url:" + targetUrl);
		HttpGet httpGet = new HttpGet(targetUrl);


		HttpResponse response = httpClient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException(String.valueOf(statusCode));
		}
		HttpEntity entity = response.getEntity();
		return entity;

	}

}