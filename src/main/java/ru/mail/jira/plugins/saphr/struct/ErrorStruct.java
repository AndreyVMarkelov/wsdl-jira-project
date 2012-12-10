package ru.mail.jira.plugins.saphr.struct;

import ru.mail.jira.plugins.saphr.Utils;

public class ErrorStruct
{
    private int code;

    private String message;

    /**
     * Constructor.
     */
    public ErrorStruct() {}

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{\"error\":{");
        sb.append("\"message\":").append(Utils.weakTrim(message)).append(",");
        sb.append("\"code\":").append("\"").append(code).append("\"");
        sb.append("}}");
        return sb.toString();
    }
}
