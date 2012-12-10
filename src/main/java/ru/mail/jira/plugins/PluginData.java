package ru.mail.jira.plugins;

public interface PluginData
{
    String getRestPassword();

    String getRestUser();

    String getSapBaseUrl();

    String getSapChangeMailUrl();

    String getSapOrgsUrl();

    String getSapPassword();

    String getSapPersonsUrl();

    String getSapUser();

    void setRestPassword(String restPassword);

    void setRestUser(String restUser);

    void setSapBaseUrl(String sapBaseUrl);

    void setSapChangeMailUrl(String sapChangeMailUrl);

    void setSapOrgsUrl(String sapOrgsUrl);

    void setSapPassword(String sapPassword);

    void setSapPersonsUrl(String sapPersonsUrl);

    void setSapUser(String sapUser);
}
