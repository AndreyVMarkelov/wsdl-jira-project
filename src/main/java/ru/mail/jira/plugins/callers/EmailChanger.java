package ru.mail.jira.plugins.callers;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.mail.jira.plugins.SapPluginException;
import ru.mail.jira.plugins.saphr.struct.ChangePassResult;

public class EmailChanger
    extends SAPSender
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(EmailChanger.class);

    /**
     * Constructor.
     */
    public EmailChanger(
        String baseURL,
        String bindingURL,
        String user,
        String password)
    {
        super(baseURL, bindingURL, user, password);
    }

    /**
     * Change email.
     */
    public ChangePassResult changeEmail(
        String email,
        String personId)
    {
        ChangePassResult changePassResult = new ChangePassResult();

        String data = call(getSoap(email, personId));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(data.getBytes("utf-8")));

            NodeList nodeList = dom.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node item = (Node) nodeList.item(i);
                if (item.getNodeType() != Node.ELEMENT_NODE)
                {
                    continue;
                }

                String nodeName = item.getNodeName();
                String nodeText = item.getTextContent();
                if (nodeName.equals("TYPE"))
                {
                    changePassResult.setType(nodeText);
                }
                else if (nodeName.equals("MESSAGE"))
                {
                    changePassResult.setMsg(nodeText);
                }
            }
        }
        catch(Exception ioe)
        {
            log.error("EmailChanger::changeEmail - I/O error occurred", ioe);
            throw new SapPluginException(false, -1, ioe.getMessage());
        }

        return changePassResult;
    }

    /**
     * Get SOAP body.
     */
    private String getSoap(
        String email,
        String personId)
    {
        String soap = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\"><soap:Header/><soap:Body><urn:ZHR_INTEGR_D2002_UPDATE_EMAIL><I_EMAIL>%s</I_EMAIL><I_PERSONID>%s</I_PERSONID></urn:ZHR_INTEGR_D2002_UPDATE_EMAIL></soap:Body></soap:Envelope>";
        return String.format(soap, email, personId);
    }
}
