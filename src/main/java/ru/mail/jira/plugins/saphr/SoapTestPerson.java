package ru.mail.jira.plugins.saphr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import ru.mail.jira.plugins.Consts;
import ru.mail.jira.plugins.PluginData;
import ru.mail.jira.plugins.SapPluginException;
import ru.mail.jira.plugins.callers.PersonalService;
import ru.mail.jira.plugins.saphr.struct.ErrorStruct;
import ru.mail.jira.plugins.saphr.struct.Person;
import ru.mail.jira.plugins.saphr.struct.SapError;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.templaterenderer.RenderingException;
import com.atlassian.templaterenderer.TemplateRenderer;

public class SoapTestPerson
    extends HttpServlet
{
    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(SoapTestPerson.class);

    /**
     * Unique ID.
     */
    private static final long serialVersionUID = -1909195767843263113L;

    /**
     * Login URI provider.
     */
    private final LoginUriProvider loginUriProvider;

    /**
     * Template renderer.
     */
    private final TemplateRenderer renderer;

    /**
     * Plugin data.
     */
    private final PluginData pluginData;

    /**
     * Constructor.
     */
    public SoapTestPerson(
        TemplateRenderer renderer,
        LoginUriProvider loginUriProvider,
        PluginData pluginData)
    {
        this.renderer = renderer;
        this.loginUriProvider = loginUriProvider;
        this.pluginData = pluginData;
    }

    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp)
    throws ServletException, IOException
    {
        JiraAuthenticationContext authenticationContext = ComponentAccessor.getJiraAuthenticationContext();
        User user = authenticationContext.getLoggedInUser();
        if (user == null)
        {
            Utils.redirectToLogin(loginUriProvider, req, resp);
            return;
        }

        String personID = req.getParameter("person");

        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("lang", req.getLocale().getLanguage());
        parms.put("baseUrl", Utils.getBaseUrl(req));
        parms.put("i18n", authenticationContext.getI18nHelper());

        PersonalService personalService = new PersonalService(
            pluginData.getSapBaseUrl(),
            pluginData.getSapPersonsUrl(),
            pluginData.getSapUser(),
            pluginData.getSapPassword());
        List<Person> persons;
        try
        {
            persons = personalService.getPersons(personID);
        }
        catch (SapPluginException spe)
        {
            
            return;
        }
        parms.put("persons", persons);

        resp.setHeader("Expires", "0");
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("text/html;charset=utf-8");
        try
        {
            renderer.render("/templates/persons.vm", parms, resp.getWriter());
        }
        catch (RenderingException rex)
        {
            log.error("SoapTestPerson::doGet - Template is invalid", rex);
            resp.sendError(500, "Internal error. Template is invalid.");
        }
    }

    @Override
    protected void doPost(
        HttpServletRequest req,
        HttpServletResponse resp)
    throws ServletException, IOException
    {
        String restUser = pluginData.getRestUser();
        String restPass = pluginData.getRestPassword();
        String baseUrl = pluginData.getSapBaseUrl();
        String personUrl = pluginData.getSapPersonsUrl();
        String sapUser = pluginData.getSapUser();
        String sapPass = pluginData.getSapPassword();

        if (!Utils.checkParameters(restUser, restPass, baseUrl, personUrl, sapUser, sapPass, req, resp))
        {
            return;
        }

        String personID = req.getParameter(Consts.person);

        PersonalService personalService = new PersonalService(baseUrl, personUrl, sapUser, sapPass);
        List<Person> persons;
        try
        {
            persons = personalService.getPersons(personID);
        }
        catch (SapPluginException spe)
        {
            resp.setContentType("application/json;charset=utf-8");
            ErrorStruct es = new ErrorStruct();
            if (spe.isSOAPError())
            {
                try
                {
                    SapError se = Utils.parseSapError(spe.getMessage());
                    es.setMessage(se.getReason());
                    es.setCode(Consts.SAP_ERROR);
                }
                catch (Exception e)
                {
                    es.setMessage(spe.getMessage());
                    es.setCode(Consts.UNHANDLED_SAP_ERROR);
                }
            }
            else
            {
                es.setMessage(spe.getMessage());
                es.setCode(Consts.SYSTEM_ERROR);
            }
            resp.getWriter().write(es.toString());
            resp.getWriter().flush();
            return;
        }

        JSONArray json = new JSONArray(persons);
        try
        {
            resp.setContentType("application/json;charset=utf-8");
            json.write(resp.getWriter());
            resp.getWriter().flush();
        }
        catch (JSONException e)
        {
            log.error("SoapTestPerson::doPost - Parse JSON exception", e);
            e.printStackTrace();
        }
    }
}
