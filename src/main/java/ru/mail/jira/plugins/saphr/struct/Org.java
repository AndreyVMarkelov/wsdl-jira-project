package ru.mail.jira.plugins.saphr.struct;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ru.mail.jira.plugins.saphr.Utils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Org
{
    @XmlElement
    private String managerId;

    @XmlElement
    private String objId;

    @XmlElement
    private String otype;

    @XmlElement
    private String parentObjId;

    @XmlElement
    private String sText;

    @XmlElement
    private String workplace;

    /**
     * Constructor.
     */
    public Org() {}

    public String getManagerId()
    {
        return managerId;
    }

    public String getObjId()
    {
        return objId;
    }

    public String getOtype()
    {
        return otype;
    }

    public String getParentObjId()
    {
        return parentObjId;
    }

    public String getsText()
    {
        return sText;
    }

    public String getWorkplace()
    {
        return workplace;
    }

    public void setManagerId(String managerId)
    {
        this.managerId = managerId;
    }

    public void setObjId(String objId)
    {
        this.objId = objId;
    }

    public void setOtype(String otype)
    {
        this.otype = otype;
    }

    public void setParentObjId(String parentObjId)
    {
        this.parentObjId = parentObjId;
    }

    public void setsText(String sText)
    {
        this.sText = sText;
    }

    public void setWorkplace(String workplace)
    {
        this.workplace = workplace;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"otype\":").append(Utils.weakTrim(otype)).append(",");
        sb.append("\"objId\":").append(Utils.weakTrim(objId)).append(",");
        sb.append("\"parentObjId\":").append(Utils.weakTrim(parentObjId)).append(",");
        sb.append("\"sText\":").append(Utils.weakTrim(sText)).append(",");
        sb.append("\"managerId\":").append(Utils.weakTrim(managerId)).append(",");
        sb.append("\"workplace\":").append(Utils.weakTrim(workplace));
        sb.append("}");
        return sb.toString();
    }
}
