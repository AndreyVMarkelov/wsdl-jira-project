package ru.mail.jira.plugins.saphr.struct;

public class SAPInputStruct
{
    private String data;

    private String type;

    private String user;

    /**
     * Constructor.
     */
    public SAPInputStruct()
    {
        this.type = "";
        this.data = "";
        this.user = "";
    }

    public String getData()
    {
        return data;
    }

    public String getType()
    {
        return type;
    }

    public String getUser()
    {
        return user;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "SAPInputStruct[data=" + data + ", type=" + type + ", user=" + user + "]";
    }
}
