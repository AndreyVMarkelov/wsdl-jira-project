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
import ru.mail.jira.plugins.saphr.struct.Absence;
import ru.mail.jira.plugins.saphr.struct.Person;
import ru.mail.jira.plugins.saphr.struct.PersonChild;

/**
 * This service gets personal data from SAP HR.
 * 
 * @author Andrey Markelov
 */
public class PersonalService
    extends SAPSender
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(PersonalService.class);

    /**
     * Constructor.
     */
    public PersonalService(
        String baseURL,
        String bindingURL,
        String user,
        String password)
    {
        super(baseURL, bindingURL, user, password);
    }

    /**
     * Parse <code>T_ABSENCES</code> node.
     */
    private List<Absence> getAbsences(Node childrenNode)
    {
        List<Absence> absences = new ArrayList<Absence>();

        NodeList children = childrenNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node childItem = (Node) children.item(i);
            if (childrenNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }

            Absence absence = new Absence();
            NodeList absenceData = childItem.getChildNodes();
            for (int j = 0; j < absenceData.getLength(); j++)
            {
                Node absenceDataNode = (Node) absenceData.item(j);
                if (absenceDataNode.getNodeType() != Node.ELEMENT_NODE)
                {
                    continue;
                }

                if (absenceDataNode.getNodeName().equals("BEGDA"))
                {
                    absence.setStartDate(absenceDataNode.getTextContent());
                }
                else if (absenceDataNode.getNodeName().equals("ENDDA"))
                {
                    absence.setEndDate(absenceDataNode.getTextContent());
                }
                else if (absenceDataNode.getNodeName().equals("TYPE"))
                {
                    absence.setCause(absenceDataNode.getTextContent());
                }
            }
            absences.add(absence);
        }

        return absences;
    }

    /**
     * Parse <code>T_CHILDREN</code> node.
     */
    private List<PersonChild> getPersonChildren(Node childrenNode)
    {
        List<PersonChild> childList = new ArrayList<PersonChild>();

        NodeList children = childrenNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node childItem = (Node) children.item(i);
            if (childrenNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }

            PersonChild personChild = new PersonChild();
            NodeList childrenData = childItem.getChildNodes();
            for (int j = 0; j < childrenData.getLength(); j++)
            {
                Node childrenDataNode = (Node) childrenData.item(j);
                if (childrenDataNode.getNodeType() != Node.ELEMENT_NODE)
                {
                    continue;
                }

                if (childrenDataNode.getNodeName().equals("BGDAT"))
                {
                    personChild.setBirthday(childrenDataNode.getTextContent());
                }
                else if (childrenDataNode.getNodeName().equals("FIO"))
                {
                    personChild.setFio(childrenDataNode.getTextContent());
                }
            }
            childList.add(personChild);
        }

        return childList;
    }

    public List<Person> getPersons(String personId)
    {
        if (personId == null)
        {
            personId = "";
        }

        String xmldata =
            String.format("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\"><soap:Header/><soap:Body><urn:ZHR_INTEGR_D2002_GET_PERS_DATA><I_PERSONID>%s</I_PERSONID></urn:ZHR_INTEGR_D2002_GET_PERS_DATA></soap:Body></soap:Envelope>", personId);
        String data = call(xmldata);

        List<Person> persons = new ArrayList<Person>();
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

                if (item.getParentNode() != null &&
                    (item.getParentNode().getNodeName().equals("T_ABSENCES") || item.getParentNode().getNodeName().equals("T_CHILDREN")))
                {
                    continue;
                }

                Person person = new Person();
                NodeList itemChildren = item.getChildNodes();
                for (int j = 0; j < itemChildren.getLength(); j++)
                {
                    Node itemChild = (Node) itemChildren.item(j);
                    if (itemChild.getNodeType() != Node.ELEMENT_NODE)
                    {
                        continue;
                    }

                    String nodeName = itemChild.getNodeName();
                    if (nodeName.equals("PERSONID"))
                    {
                        person.setPersonId(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("FIO_RUSSIAN"))
                    {
                        person.setFioRussian(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("FIO_ENGLISH"))
                    {
                        person.setFioEnglish(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("FIO_LOCAL"))
                    {
                        person.setFioLocal(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("GBDAT")) //--> date
                    {
                        person.setBirthDay(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("HIREDATE")) //--> date
                    {
                        person.setHireDay(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("FIREDATE")) //--> date
                    {
                        person.setFirehDay(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("ORGEH"))
                    {
                        person.setOrgUnit(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("PLANS"))
                    {
                        person.setPosition(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("COUNTRY"))
                    {
                        person.setCountry(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("CITY"))
                    {
                        person.setCity(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("BUTXT"))
                    {
                        person.setBalanceUnit(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("PBTXT"))
                    {
                        person.setPersonalData(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("BTEXT"))
                    {
                        person.setSubPersonalData(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("PTEXT"))
                    {
                        person.setGroup(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("MANAGER_ID"))
                    {
                        person.setManagerId(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("T_ABSENCES"))
                    {
                        person.setAbsense(getAbsences(itemChild));
                    }
                    else if (nodeName.equals("PROB_END")) //--> date
                    {
                        person.setProbationaryPeriod(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("FATXT"))
                    {
                        person.setMaritalStatus(itemChild.getTextContent());
                    }
                    else if (nodeName.equals("T_CHILDREN"))
                    {
                        person.setChildren(getPersonChildren(itemChild));
                    }
                    else if (nodeName.equals("E_MAIL"))
                    {
                        person.setEmail(itemChild.getTextContent());
                    }
                }
                persons.add(person);
            }
        }
        catch(Exception ioe)
        {
            log.error("PersonalService::getPersons - I/O error occurred", ioe);
            throw new SapPluginException(false, -1, ioe.getMessage());
        }

        return persons;
    }
}
