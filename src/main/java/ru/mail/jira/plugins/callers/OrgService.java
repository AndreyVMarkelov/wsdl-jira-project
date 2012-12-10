package ru.mail.jira.plugins.callers;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.mail.jira.plugins.SapPluginException;
import ru.mail.jira.plugins.saphr.struct.Org;

/**
 * This service receives organizational units from SAP HR.
 * 
 * @author Andrey Markelov
 */
public class OrgService
    extends SAPSender
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(OrgService.class);

    /**
     * Constructor.
     */
    public OrgService(
        String baseURL,
        String bindingURL,
        String user,
        String password)
    {
        super(baseURL, bindingURL, user, password);
    }

    /**
     * Get organizational units.
     */
    public List<Org> getOrgs()
    {
        String data = call(getSoap());

        List<Org> orgs = new ArrayList<Org>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(data.getBytes("utf-8")));
            NodeList nodeList = dom.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node item = (Node) nodeList.item(i);
                if (item.getNodeType() != Node.ELEMENT_NODE)
                {
                    continue;
                }

                Org org = new Org();
                NodeList itemChildren = item.getChildNodes();
                for (int j = 0; j < itemChildren.getLength(); j++)
                {
                    Node itemChild = (Node) itemChildren.item(j);
                    if (itemChild.getNodeType() != Node.ELEMENT_NODE)
                    {
                        continue;
                    }

                    String nodeName = itemChild.getNodeName();
                    String nodeText = itemChild.getTextContent();
                    if (nodeName.equals("OTYPE"))
                    {
                        org.setOtype(nodeText);
                    }
                    else if (nodeName.equals("OBJID"))
                    {
                        org.setObjId(nodeText);
                    }
                    else if (nodeName.equals("PARENT_OBJID"))
                    {
                        org.setParentObjId(nodeText);
                    }
                    else if (nodeName.equals("STEXT"))
                    {
                        org.setsText(nodeText);
                    }
                    else if (nodeName.equals("MANAGER_ID"))
                    {
                        org.setManagerId(nodeText);
                    }
                    else if (nodeName.equals("WORKPLACE"))
                    {
                        org.setWorkplace(nodeText);
                    }
                }
                orgs.add(org);
            }
        }
        catch(Exception ioe)
        {
            log.error("OrgService::getOrgs - I/O error occurred", ioe);
            throw new SapPluginException(false, -1, ioe.getMessage());
        }

        return orgs;
    }

    /**
     * Get SOAP body.
     */
    private String getSoap()
    {
        return "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"><env:Header/><env:Body><ns1:ZHR_INTEGR_D2002_GET_ORG_DATA xmlns:ns1=\"urn:sap-com:document:sap:rfc:functions\"/></env:Body></env:Envelope>";
    }
}
