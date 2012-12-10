package ru.mail.jira.plugins;

public interface Consts
{
    int PLUGIN_NOT_CONFIGURED = 1000;

    int HTTP_PARAMETERS_NOT_SET = 1001;

    int WRONG_REST_AUTH = 1002;

    int SAP_ERROR = 1003;

    int UNHANDLED_SAP_ERROR = 1004;

    int SYSTEM_ERROR = 1005;

    /**
     * HTTP parameter name of username for login to JIRA.
     */
    String jiraLogin = "jiralogin";

    /**
     * HTTP parameter name of password for login to JIRA.
     */
    String jiraPassword = "jirapassword";

    /**
     * HTTP parameter name "person".
     */
    String person = "person";

    /**
     * HTTP parameter name "email".
     */
    String email = "email";
}
