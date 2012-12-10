package ru.mail.jira.plugins.saphr.struct;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mail.jira.plugins.saphr.Utils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChangePassResult
{
    @XmlElement
    private String msg;

    @XmlElement
    private String type;

    /**
     * Constructor.
     */
    public ChangePassResult() {}

    public String getMsg()
    {
        return msg;
    }

    public String getType()
    {
        return type;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":").append(Utils.weakTrim(type)).append(",");
        sb.append("\"msg\":").append(Utils.weakTrim(msg));
        sb.append("}");
        return sb.toString();
    }
}
