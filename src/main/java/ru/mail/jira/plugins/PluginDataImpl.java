package ru.mail.jira.plugins;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class PluginDataImpl
    implements PluginData
{
    /**
     * PlugIn key.
     */
    private static final String PLUGIN_KEY = "RU_MAIL_SAPHR_PLUGIN";

    /**
     * Plug-In settings.
     */
    private final PluginSettings pluginSettings;

    /**
     * Constructor.
     */
    public PluginDataImpl(
        PluginSettingsFactory pluginSettingsFactory)
    {
        this.pluginSettings = pluginSettingsFactory.createSettingsForKey(PLUGIN_KEY);
    }

    private synchronized PluginSettings getPluginSettings()
    {
        return pluginSettings;
    }

    @Override
    public String getRestPassword()
    {
        return (String)getPluginSettings().get("restPassword");
    }

    @Override
    public String getRestUser()
    {
        return (String)getPluginSettings().get("restUser");
    }

    @Override
    public String getSapBaseUrl()
    {
        return (String)getPluginSettings().get("sapBaseUrl");
    }

    @Override
    public String getSapChangeMailUrl()
    {
        return (String)getPluginSettings().get("sapChangeMailUrl");
    }

    @Override
    public String getSapOrgsUrl()
    {
        return (String)getPluginSettings().get("sapOrgsUrl");
    }

    @Override
    public String getSapPassword()
    {
        return (String)getPluginSettings().get("sapPassword");
    }

    @Override
    public String getSapPersonsUrl()
    {
        return (String)getPluginSettings().get("sapPersonsUrl");
    }

    @Override
    public String getSapUser()
    {
        return (String)getPluginSettings().get("sapUser");
    }

    @Override
    public void setRestPassword(String restPassword)
    {
        getPluginSettings().put("restPassword", restPassword);
    }

    @Override
    public void setRestUser(String restUser)
    {
        getPluginSettings().put("restUser", restUser);
    }

    @Override
    public void setSapBaseUrl(String sapBaseUrl)
    {
        getPluginSettings().put("sapBaseUrl", sapBaseUrl);
    }

    @Override
    public void setSapChangeMailUrl(String sapChangeMailUrl)
    {
        getPluginSettings().put("sapChangeMailUrl", sapChangeMailUrl);
    }

    @Override
    public void setSapOrgsUrl(String sapOrgsUrl)
    {
        getPluginSettings().put("sapOrgsUrl", sapOrgsUrl);
    }

    @Override
    public void setSapPassword(String sapPassword)
    {
        getPluginSettings().put("sapPassword", sapPassword);
    }

    @Override
    public void setSapPersonsUrl(String sapPersonsUrl)
    {
        getPluginSettings().put("sapPersonsUrl", sapPersonsUrl);
    }

    @Override
    public void setSapUser(String sapUser)
    {
        getPluginSettings().put("sapUser", sapUser);
    }
}
