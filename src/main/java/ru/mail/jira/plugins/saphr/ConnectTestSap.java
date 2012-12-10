package ru.mail.jira.plugins.saphr;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mail.jira.plugins.actions.NewEmployeeAction;
import ru.mail.jira.plugins.saphr.struct.SAPInputStruct;

public class ConnectTestSap
    extends HttpServlet
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(ConnectTestSap.class);

    /**
     * Unique ID.
     */
    private static final long serialVersionUID = 8848313287087573154L;

    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp)
    throws ServletException, IOException
    {
        String url = Utils.getBaseUrl(req) + "/plugins/servlet/saphr/wsdl";
        String docUrl = Utils.getBaseUrl(req) + "/plugins/servlet/saphr/wsdl?a=doc";

        String wsdl = String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?><wsdl:definitions targetNamespace=\"urn:sap-com:document:sap:rfc:functions\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:wsoap12=\"http://schemas.xmlsoap.org/wsdl/soap12/\" xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" xmlns:tns=\"urn:sap-com:document:sap:rfc:functions\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsdl:documentation><sidl:sidl xmlns:sidl=\"http://www.sap.com/2007/03/sidl\"/></wsdl:documentation><wsp:UsingPolicy wsdl:required=\"true\"/><wsp:Policy wsu:Id=\"BN_BN_binding\"><saptrnbnd:OptimizedXMLTransfer uri=\"http://xml.sap.com/2006/11/esi/esp/binxml\" xmlns:saptrnbnd=\"http://www.sap.com/webas/710/soap/features/transportbinding/\" wsp:Optional=\"true\"/><saptrnbnd:OptimizedMimeSerialization xmlns:saptrnbnd=\"http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization\" wsp:Optional=\"true\"/><sapattahnd:Enabled xmlns:sapattahnd=\"http://www.sap.com/710/features/attachment/\">false</sapattahnd:Enabled><wsp:ExactlyOne xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"/><wsaw:UsingAddressing xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" wsp:Optional=\"true\"/></wsp:Policy><wsp:Policy wsu:Id=\"BN_BN_binding_SOAP12\"><saptrnbnd:OptimizedXMLTransfer uri=\"http://xml.sap.com/2006/11/esi/esp/binxml\" xmlns:saptrnbnd=\"http://www.sap.com/webas/710/soap/features/transportbinding/\" wsp:Optional=\"true\"/><saptrnbnd:OptimizedMimeSerialization xmlns:saptrnbnd=\"http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization\" wsp:Optional=\"true\"/><sapattahnd:Enabled xmlns:sapattahnd=\"http://www.sap.com/710/features/attachment/\">false</sapattahnd:Enabled><wsp:ExactlyOne xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"/><wsaw:UsingAddressing xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" wsp:Optional=\"true\"/></wsp:Policy><wsp:Policy wsu:Id=\"IF_IF_JIRA_SAP_INTEGRATOR\"><sapsession:Session xmlns:sapsession=\"http://www.sap.com/webas/630/soap/features/session/\"><sapsession:enableSession>false</sapsession:enableSession></sapsession:Session><sapcentraladmin:CentralAdministration xmlns:sapcentraladmin=\"http://www.sap.com/webas/700/soap/features/CentralAdministration/\" wsp:Optional=\"true\"><sapcentraladmin:BusinessApplicationID>D8D3856A025C1EE09983F20E61005D0F</sapcentraladmin:BusinessApplicationID></sapcentraladmin:CentralAdministration></wsp:Policy><wsp:Policy wsu:Id=\"OP_IF_OP_ZHR_TEST\"><sapcomhnd:enableCommit xmlns:sapcomhnd=\"http://www.sap.com/NW05/soap/features/commit/\">false</sapcomhnd:enableCommit><sapblock:enableBlocking xmlns:sapblock=\"http://www.sap.com/NW05/soap/features/blocking/\">true</sapblock:enableBlocking><saptrhnw05:required xmlns:saptrhnw05=\"http://www.sap.com/NW05/soap/features/transaction/\">no</saptrhnw05:required><saprmnw05:enableWSRM xmlns:saprmnw05=\"http://www.sap.com/NW05/soap/features/wsrm/\">false</saprmnw05:enableWSRM></wsp:Policy><wsdl:types><xsd:schema attributeFormDefault=\"qualified\" targetNamespace=\"urn:sap-com:document:sap:rfc:functions\"><xsd:element name=\"ZHR_TEST\"><xsd:complexType><xsd:sequence><xsd:element name=\"IN1\" type=\"xsd:string\"/><xsd:element name=\"IN2\" type=\"xsd:string\"/><xsd:element name=\"IN3\" type=\"xsd:string\"/></xsd:sequence></xsd:complexType></xsd:element><xsd:element name=\"ZHR_TESTResponse\"><xsd:complexType><xsd:sequence><xsd:element name=\"FIREEVENTRETURN\" type=\"xsd:int\"/></xsd:sequence></xsd:complexType></xsd:element></xsd:schema></wsdl:types><wsdl:message name=\"ZHR_TEST\"><wsdl:part name=\"parameters\" element=\"tns:ZHR_TEST\"/></wsdl:message><wsdl:message name=\"ZHR_TESTResponse\"><wsdl:part name=\"parameter\" element=\"tns:ZHR_TESTResponse\"/></wsdl:message><wsdl:portType name=\"JIRA_SAP_INTEGRATOR\"><wsdl:documentation><sapdoc:sapdoc xmlns:sapdoc=\"urn:sap:esi:documentation\"><sapdoc:docitem docURL=\"%s\"/></sapdoc:sapdoc></wsdl:documentation><wsp:Policy><wsp:PolicyReference URI=\"#IF_IF_JIRA_SAP_INTEGRATOR\"/></wsp:Policy><wsdl:operation name=\"ZHR_TEST\"><wsp:Policy><wsp:PolicyReference URI=\"#OP_IF_OP_ZHR_TEST\"/></wsp:Policy><wsdl:input message=\"tns:ZHR_TEST\"/><wsdl:output message=\"tns:ZHR_TESTResponse\"/></wsdl:operation></wsdl:portType><wsdl:binding name=\"binding\" type=\"tns:JIRA_SAP_INTEGRATOR\"><wsp:Policy><wsp:PolicyReference URI=\"#BN_BN_binding\"/></wsp:Policy><soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\"/><wsdl:operation name=\"ZHR_TEST\"><soap:operation soapAction=\"urn:sap-com:document:sap:rfc:functions:JIRA_SAP_INTEGRATOR:ZHR_TESTRequest\" style=\"document\"/><wsdl:input><soap:body use=\"literal\"/></wsdl:input><wsdl:output><soap:body use=\"literal\"/></wsdl:output></wsdl:operation></wsdl:binding><wsdl:binding name=\"binding_SOAP12\" type=\"tns:JIRA_SAP_INTEGRATOR\"><wsp:Policy><wsp:PolicyReference URI=\"#BN_BN_binding_SOAP12\"/></wsp:Policy><wsoap12:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\"/><wsdl:operation name=\"ZHR_TEST\"><wsoap12:operation soapAction=\"urn:sap-com:document:sap:rfc:functions:JIRA_SAP_INTEGRATOR:ZHR_TESTRequest\" style=\"document\"/><wsdl:input><wsoap12:body use=\"literal\"/></wsdl:input><wsdl:output><wsoap12:body use=\"literal\"/></wsdl:output></wsdl:operation></wsdl:binding><wsdl:service name=\"service\"><wsdl:port name=\"binding\" binding=\"tns:binding\"><soap:address location=\"%s\"/></wsdl:port><wsdl:port name=\"binding_SOAP12\" binding=\"tns:binding_SOAP12\"><wsoap12:address location=\"%s\"/></wsdl:port></wsdl:service></wsdl:definitions>", docUrl, url, url);
        String doc = "<HTML> <HEAD>  <style type=\"text/css\"> <!-- body { background-color : #fefeee;  font-family : arial; font-style : normal; font-size : 0.8em; color :  #000000; } table, td, tr { background-color : #fefeee; font-family :  arial; font-style : normal; font-size : 100%; color : #000000; } h1, h2,  h3, h4, h5 { color : #000080; } a:link { color : #0063a4;  text-decoration : none; } a:visited { color : #807f75; text-decoration :  none; } a:active { color : #807f75; text-decoration : none; } a:hover {  color : #f00000; text-decoration : none; } .button { font-family :  arial, sans-serif; } .inactive { font-family : arial, sans-serif; color  : #919186; } .text { font-family : arial, sans-serif; } .tab {  font-family : arial, sans-serif; } .groupbox { font-family : arial,  sans-serif; color : #42423d; } .tableheader { font-family : arial,  sans-serif; color : #42423d; } .nonedit { font-family : arial,  sans-serif; color : #42423d; } .input { font-family : arial, sans-serif;  background : #fefeee; border : 0; padding-left : 5px; padding-top : 2px;  padding-bottom : 2px; padding-right : 5px; } .pulldown { font-family :  arial, sans-serif; background : #fefeee; border : 0; } --> </style> <META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-16le\"> <TITLE>ESD_WSD_TEXT</TITLE> </HEAD> <BODY> <H3>Определение веб-сервиса JIRA_SAP_INTEGRATOR</H3> <H3>Краткий текст</H3> <P>TEST WSDL</P> <H3>Описание</H3> <P></P> </BODY> </HTML>";

        if (req.getParameter("a") != null)
        {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(doc);
            resp.getWriter().flush();
        }
        else
        {
            resp.setContentType("text/xml;charset=utf-8");
            resp.getWriter().write(wsdl);
            resp.getWriter().flush();
        }
    }

    @Override
    protected void doPost(
        HttpServletRequest req,
        HttpServletResponse resp)
    throws ServletException, IOException
    {
        StringWriter writer = new StringWriter();
        IOUtils.copy(req.getInputStream(), writer, "UTF-8");
        String theString = writer.toString();

        SAPInputStruct sapStruct = new SAPInputStruct();

        int res = 0;

        if (Utils.isValid(theString))
        {
            int sin1 = theString.indexOf("<IN1>");
            int ein1 = theString.indexOf("</IN1>");
            int sin2 = theString.indexOf("<IN2>");
            int ein2 = theString.indexOf("</IN2>");
            int sin3 = theString.indexOf("<IN3>");
            int ein3 = theString.indexOf("</IN3>");

            if (sin1 > 0 && ein1 > 0)
            {
                sapStruct.setType(theString.substring(sin1 + 5, ein1));

                if (sin2 > 0 && ein2 > 0)
                {
                    sapStruct.setData(theString.substring(sin2 + 5, ein2));
                }

                if (sin3 > 0 && ein3 > 0)
                {
                    sapStruct.setUser(theString.substring(sin3 + 5, ein3));
                }
            }
            else
            {
                log.error("ConnectTestSap::doPost - Incorrect XML");
                res = 1; //--> incorrect data
                writeResponse(resp, res);
                return;
            }
        }
        else
        {
            log.error("ConnectTestSap::doPost - Incorrect XML");
            res = 1; //--> incorrect data
            writeResponse(resp, res);
            return;
        }

        if (sapStruct.getType().equals("NEW_EMPLOYEE") && Utils.isValid(sapStruct.getData()))
        {
            NewEmployeeAction act = new NewEmployeeAction(sapStruct);
            act.doAction();
        }
        else if (sapStruct.getType().equals("FIRE_EMPLOYEE") && Utils.isValid(sapStruct.getData()))
        {
            NewEmployeeAction act = new NewEmployeeAction(sapStruct);
            act.doAction();
        }
        else if (sapStruct.getType().equals("RETURN_EMPLOYEE") && Utils.isValid(sapStruct.getData()))
        {
            NewEmployeeAction act = new NewEmployeeAction(sapStruct);
            act.doAction();
        }
        else if (sapStruct.getType().equals("CHANGE_EMPLOYEE") && Utils.isValid(sapStruct.getData()))
        {
            NewEmployeeAction act = new NewEmployeeAction(sapStruct);
            act.doAction();
        }
        else if (sapStruct.getType().equals("ORGUNITS_CHANGED"))
        {
            NewEmployeeAction act = new NewEmployeeAction(sapStruct);
            act.doAction();
        }
        else
        {
            res = 2; //--> incorrect type
        }

        writeResponse(resp, res);
    }

    /**
     * Write response.
     */
    private void writeResponse(
        HttpServletResponse resp,
        int res)
    throws IOException
    {
        String ans = String.format("<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header/><env:Body><n0:ZHR_TESTResponse xmlns:n0=\"urn:sap-com:document:sap:rfc:functions\"><FIREEVENTRETURN>%s</FIREEVENTRETURN></n0:ZHR_TESTResponse></env:Body></env:Envelope>", res);
        resp.setContentType("text/xml;charset=utf-8");
        resp.setContentLength(ans.length());
        resp.setHeader("accept", "application/soap+xml");
        resp.getWriter().write(ans);
        resp.getWriter().flush();
    }
}
