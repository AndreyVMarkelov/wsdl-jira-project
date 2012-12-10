package ru.mail.jira.plugins.saphr.struct;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import ru.mail.jira.plugins.saphr.Utils;

@XmlAccessorType(XmlAccessType.FIELD)
public class Absence
{
    @XmlElement
    private String cause;

    @XmlElement
    private String endDate;

    @XmlElement
    private String startDate;

    /**
     * Constructor.
     */
    public Absence() {}

    public String getCause()
    {
        return cause;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setCause(String cause)
    {
        this.cause = cause;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"startDate\":").append(Utils.weakTrim(startDate)).append(",");
        sb.append("\"cause\":").append(Utils.weakTrim(cause)).append(",");
        sb.append("\"endDate\":").append(Utils.weakTrim(endDate));
        sb.append("}");
        return sb.toString();
    }
}
