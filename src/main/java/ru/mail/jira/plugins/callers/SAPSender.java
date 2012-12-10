package ru.mail.jira.plugins.callers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mail.jira.plugins.SapPluginException;

/**
 * Basic client to SAP.
 * 
 * @author Andrey Markelov
 */
abstract class SAPSender
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(SAPSender.class);

    /**
     * Base URL.
     */
    private String baseURL;

    /**
     * Binding URL.
     */
    private String bindingURL;

    /**
     * Password.
     */
    private String password;

    /**
     * User.
     */
    private String user;

    /**
     * Constructor.
     */
    public SAPSender(
        String baseURL,
        String bindingURL,
        String user,
        String password)
    {
        this.baseURL = baseURL;
        this.bindingURL = bindingURL;
        this.user = user;
        this.password = password;
    }

    /**
     * Call service.
     */
    protected String call(String soap)
    {
        StringBuilder infWebSvcReplyString = new StringBuilder();

        try
        {
            URL urlForInfWebSvc = new URL(bindingURL);
            URLConnection UrlConnInfWebSvc = urlForInfWebSvc.openConnection();
            HttpURLConnection httpUrlConnInfWebSvc = (HttpURLConnection) UrlConnInfWebSvc;
            httpUrlConnInfWebSvc.setDoOutput(true);
            httpUrlConnInfWebSvc.setDoInput(true);
            httpUrlConnInfWebSvc.setAllowUserInteraction(true);
            httpUrlConnInfWebSvc.setRequestMethod("POST");
            httpUrlConnInfWebSvc.setRequestProperty("Host", baseURL); //"http://vmxhrd01.corp.mail.ru:8000"
            httpUrlConnInfWebSvc.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
            httpUrlConnInfWebSvc.setRequestProperty("Authorization", "Basic " + getAuthRealm());
            OutputStreamWriter infWebSvcReqWriter = new OutputStreamWriter(httpUrlConnInfWebSvc.getOutputStream());
            infWebSvcReqWriter.write(soap);
            infWebSvcReqWriter.flush();
            infWebSvcReqWriter.close();

            int rc = httpUrlConnInfWebSvc.getResponseCode();
            if (rc != HttpURLConnection.HTTP_OK)
            {
                BufferedReader infWebSvcReplyReader = new BufferedReader(new InputStreamReader(httpUrlConnInfWebSvc.getErrorStream(), "UTF-8"));
                String line;
                while ((line = infWebSvcReplyReader.readLine()) != null)
                {
                    infWebSvcReplyString.append(line); 
                }
                infWebSvcReqWriter.close();
                infWebSvcReplyReader.close();
                httpUrlConnInfWebSvc.disconnect();

                log.error("");
                throw new SapPluginException(true, rc, infWebSvcReplyString.toString());
            }
            else
            {
                BufferedReader infWebSvcReplyReader = new BufferedReader(new InputStreamReader(httpUrlConnInfWebSvc.getInputStream(), "UTF-8"));
                String line;
                while ((line = infWebSvcReplyReader.readLine()) != null)
                {
                    infWebSvcReplyString.append(line); 
                }
                infWebSvcReqWriter.close();
                infWebSvcReplyReader.close();
                httpUrlConnInfWebSvc.disconnect();
            }
        }
        catch (MalformedURLException mex)
        {
            log.error("SAPSender::call - Incorrect URL", mex);
            throw new SapPluginException(false, -1, mex.getMessage());
        }
        catch (IOException e)
        {
            log.error("SAPSender::call - I/O errro occurred", e);
            throw new SapPluginException(false, -1, e.getMessage());
        }

        return infWebSvcReplyString.toString();
    }

    /**
     * Get auth realm.
     */
    private String getAuthRealm()
    {
        return Base64.encodeBase64String(user.concat(":").concat(password).getBytes());
    }
}
