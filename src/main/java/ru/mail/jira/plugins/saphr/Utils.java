package ru.mail.jira.plugins.saphr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.mail.jira.plugins.Consts;
import ru.mail.jira.plugins.saphr.struct.ErrorStruct;
import ru.mail.jira.plugins.saphr.struct.SapError;
import com.atlassian.sal.api.auth.LoginUriProvider;

/**
 * Utility methods.
 *
 * @author Andrey Markelov
 */
public class Utils
{
    /**
     * Check parameters for requests.
     */
    public static boolean checkParameters(
        String restUser,
        String restPass,
        String baseUrl,
        String url,
        String sapUSer,
        String sapPass,
        HttpServletRequest req,
        HttpServletResponse resp)
    throws IOException
    {
        if (!Utils.isValid(restUser) || !Utils.isValid(restPass) ||
            !Utils.isValid(baseUrl) || !Utils.isValid(url) ||
            !Utils.isValid(sapUSer) || !Utils.isValid(sapPass))
        {
            ErrorStruct err = new ErrorStruct();
            err.setCode(Consts.PLUGIN_NOT_CONFIGURED);
            err.setMessage("Plugin is not configured");
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(err.toString());
            resp.getWriter().flush();

            return false;
        }

        String userName = req.getParameter(Consts.jiraLogin);
        String password = req.getParameter(Consts.jiraPassword);
        if (!Utils.isValid(userName) || !Utils.isValid(password))
        {
            ErrorStruct err = new ErrorStruct();
            err.setCode(Consts.HTTP_PARAMETERS_NOT_SET);
            err.setMessage("Auth parameters are not set");
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(err.toString());
            resp.getWriter().flush();

            return false;
        }

        if (!userName.equals(restUser) || !password.equals(restPass))
        {
            ErrorStruct err = new ErrorStruct();
            err.setCode(Consts.WRONG_REST_AUTH);
            err.setMessage("Wrong auth parameters");
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(err.toString());
            resp.getWriter().flush();

            return false;
        }

        return true;
    }

    /**
     * Get base URL from request.
     */
    public static String getBaseUrl(
        HttpServletRequest req)
    {
        return (req.getScheme() + "://" + req.getServerName() + ":" +
            req.getServerPort() + req.getContextPath());
    }

    /**
     * Get URI from request.
     */
    private static URI getUri(
        HttpServletRequest request)
    {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null)
        {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }

    /**
     * Check that string is not null and not empty.
     */
    public static boolean isValid(String str)
    {
        if (str != null && str.length() > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * Parse SOAP SAP error.
     */
    public static SapError parseSapError(String sapErrorStr)
    throws Exception
    {
        SapError sapError = new SapError();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(new ByteArrayInputStream(sapErrorStr.getBytes("utf-8")));
        NodeList nodeList = dom.getElementsByTagName("env:Fault");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node item = (Node) nodeList.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }

            NodeList itemChildren = item.getChildNodes();
            for (int j = 0; j < itemChildren.getLength(); j++)
            {
                Node itemChild = (Node) itemChildren.item(j);
                if (itemChild.getNodeType() != Node.ELEMENT_NODE)
                {
                    continue;
                }

                String nodeName = itemChild.getNodeName();
                if (nodeName.equals("env:Code"))
                {
                    Node firstChildNode = itemChild.getFirstChild();
                    if (firstChildNode.getNodeName().equals("env:Value"))
                    {
                        sapError.setCode(firstChildNode.getTextContent());
                    }
                }
                else if (nodeName.equals("env:Reason"))
                {
                    Node firstChildNode = itemChild.getFirstChild();
                    if (firstChildNode.getNodeName().equals("env:Text"))
                    {
                        sapError.setReason(firstChildNode.getTextContent());
                    }
                }
            }
        }

        return sapError;
    }

    /**
     * Redirect to login page.
     */
    public static void redirectToLogin(
        LoginUriProvider loginUriProvider,
        HttpServletRequest request,
        HttpServletResponse response)
    throws IOException
    {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    /**
     * Empty string or string.
     */
    public static String weakTrim(String str)
    {
        if(str == null || str.length() == 0)
        {
            return "";
        }
        else
        {
            return ("\"" + str + "\"");
        }
    }

    /**
     * Private constructor.
     */
    private Utils() {}
}
