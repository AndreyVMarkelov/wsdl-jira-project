package ru.mail.jira.plugins.saphr.struct;

import ru.mail.jira.plugins.saphr.Utils;

public class SapError
{
    private String code;

    private String reason;

    /**
     * Constructor.
     */
    public SapError() {}

    public String getCode()
    {
        return code;
    }

    public String getReason()
    {
        return reason;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"reason\":").append(Utils.weakTrim(reason)).append(",");
        sb.append("\"code\":").append(Utils.weakTrim(code));
        sb.append("}");
        return sb.toString();
    }
}
