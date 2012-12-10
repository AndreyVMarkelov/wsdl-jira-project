package ru.mail.jira.plugins;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

public class AdminSettingsAction
    extends JiraWebActionSupport
{
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = -3355410810569632065L;

    /**
     * Application properties.
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Is saved?
     */
    private boolean isSaved = false;

    /**
     * Plugin data.
     */
    private final PluginData pluginData;

    private String restPassword;

    private String restUser;

    private String sapBaseUrl;

    private String sapChangeMailUrl;

    private String sapOrgsUrl;

    private String sapPassword;

    private String sapPersonsUrl;

    private String sapUser;

    /**
     * Constructor.
     */
    public AdminSettingsAction(
        PluginData pluginData,
        ApplicationProperties applicationProperties)
    {
        this.pluginData = pluginData;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public String doDefault()
    throws Exception
    {
        this.sapBaseUrl = pluginData.getSapBaseUrl();
        this.sapUser = pluginData.getSapUser();
        this.sapPassword = pluginData.getSapPassword();
        this.sapOrgsUrl = pluginData.getSapOrgsUrl();
        this.sapPersonsUrl = pluginData.getSapPersonsUrl();
        this.sapChangeMailUrl = pluginData.getSapChangeMailUrl();
        this.restUser = pluginData.getRestUser();
        this.restPassword = pluginData.getSapPassword();

        return SUCCESS;
    }

    @Override
    protected String doExecute()
    throws Exception
    {
        pluginData.setSapBaseUrl(sapBaseUrl);
        pluginData.setSapUser(sapUser);
        pluginData.setSapPassword(sapPassword);
        pluginData.setSapOrgsUrl(sapOrgsUrl);
        pluginData.setSapPersonsUrl(sapPersonsUrl);
        pluginData.setSapChangeMailUrl(sapChangeMailUrl);
        pluginData.setRestUser(restUser);
        pluginData.setRestPassword(restPassword);

        setSaved(true);
        return getRedirect("AdminSettingsAction!default.jspa?saved=true");
    }

    @Override
    protected void doValidation()
    {
        if (sapBaseUrl.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (sapUser.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (sapPassword.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (sapOrgsUrl.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (sapPersonsUrl.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (sapChangeMailUrl.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (restUser.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        if (restPassword.isEmpty())
        {
            addErrorMessage("saphr.admin.configure.requiredfield.error");
            return;
        }

        super.doValidation();
    }

    /**
     * Get context path.
     */
    public String getBaseUrl()
    {
        return applicationProperties.getBaseUrl();
    }

    public String getRestPassword()
    {
        return restPassword;
    }

    public String getRestUser()
    {
        return restUser;
    }

    public String getSapBaseUrl()
    {
        return sapBaseUrl;
    }

    public String getSapChangeMailUrl()
    {
        return sapChangeMailUrl;
    }

    public String getSapOrgsUrl()
    {
        return sapOrgsUrl;
    }

    public String getSapPassword()
    {
        return sapPassword;
    }

    public String getSapPersonsUrl()
    {
        return sapPersonsUrl;
    }

    public String getSapUser()
    {
        return sapUser;
    }

    /**
     * Check administer permissions.
     */
    public boolean hasAdminPermission()
    {
        User user = getLoggedInUser();
        if (user == null)
        {
            return false;
        }

        if (getPermissionManager().hasPermission(Permissions.ADMINISTER, getLoggedInUser()))
        {
            return true;
        }

        return false;
    }

    public boolean isSaved()
    {
        return isSaved;
    }

    public void setRestPassword(String restPassword)
    {
        this.restPassword = restPassword;
    }

    public void setRestUser(String restUser)
    {
        this.restUser = restUser;
    }

    public void setSapBaseUrl(String sapBaseUrl)
    {
        this.sapBaseUrl = sapBaseUrl;
    }

    public void setSapChangeMailUrl(String sapChangeMailUrl)
    {
        this.sapChangeMailUrl = sapChangeMailUrl;
    }

    public void setSapOrgsUrl(String sapOrgsUrl)
    {
        this.sapOrgsUrl = sapOrgsUrl;
    }

    public void setSapPassword(String sapPassword)
    {
        this.sapPassword = sapPassword;
    }

    public void setSapPersonsUrl(String sapPersonsUrl)
    {
        this.sapPersonsUrl = sapPersonsUrl;
    }

    public void setSapUser(String sapUser)
    {
        this.sapUser = sapUser;
    }

    public void setSaved(boolean isSaved)
    {
        this.isSaved = isSaved;
    }
}
