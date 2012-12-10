package ru.mail.jira.plugins.saphr.struct;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import ru.mail.jira.plugins.saphr.Utils;

/**
 * Child of person.
 * 
 * @author Andrey Markelov
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonChild
{
    @XmlElement
    private String birthday;

    @XmlElement
    private String fio;

    /**
     * Constructor.
     */
    public PersonChild() {}

    public String getBirthday()
    {
        return birthday;
    }

    public String getFio()
    {
        return fio;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public void setFio(String fio)
    {
        this.fio = fio;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"birthday\":").append(Utils.weakTrim(birthday)).append(",");
        sb.append("\"fio\":").append(Utils.weakTrim(fio));
        sb.append("}");
        return sb.toString();
    }
}
