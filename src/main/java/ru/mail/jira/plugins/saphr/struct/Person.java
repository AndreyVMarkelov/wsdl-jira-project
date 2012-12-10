package ru.mail.jira.plugins.saphr.struct;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.mail.jira.plugins.saphr.Utils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person
{
    @XmlElement
    private List<Absence> absense; //(Типы ABSENCE - Отпуск и отсутствия к ним приравненные, BUSTRIP - Командировка, OTHER - Другое)

    @XmlElement
    private String balanceUnit;

    @XmlElement
    private String birthDay;

    @XmlElement
    private List<PersonChild> children;

    @XmlElement
    private String city;

    @XmlElement
    private String country;

    @XmlElement
    private String email;

    @XmlElement
    private String fioEnglish;

    @XmlElement
    private String fioLocal;

    @XmlElement
    private String fioRussian;

    @XmlElement
    private String firehDay;

    @XmlElement
    private String group;

    @XmlElement
    private String hireDay;

    @XmlElement
    private String managerId;

    @XmlElement
    private String maritalStatus;

    @XmlElement
    private String orgUnit;

    @XmlElement
    private String personalData;

    @XmlElement
    private String personId;

    @XmlElement
    private String position;

    @XmlElement
    private String probationaryPeriod;

    @XmlElement
    private String subPersonalData;

    /**
     * Constructor.
     */
    public Person() {}

    public List<Absence> getAbsense()
    {
        return absense;
    }

    public String getBalanceUnit()
    {
        return balanceUnit;
    }

    public String getBirthDay()
    {
        return birthDay;
    }

    public List<PersonChild> getChildren()
    {
        return children;
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFioEnglish()
    {
        return fioEnglish;
    }

    public String getFioLocal()
    {
        return fioLocal;
    }

    public String getFioRussian()
    {
        return fioRussian;
    }

    public String getFirehDay()
    {
        return firehDay;
    }

    public String getGroup()
    {
        return group;
    }

    public String getHireDay()
    {
        return hireDay;
    }

    public String getManagerId()
    {
        return managerId;
    }

    public String getMaritalStatus()
    {
        return maritalStatus;
    }

    public String getOrgUnit()
    {
        return orgUnit;
    }

    public String getPersonalData()
    {
        return personalData;
    }

    public String getPersonId()
    {
        return personId;
    }

    public String getPosition()
    {
        return position;
    }

    public String getProbationaryPeriod()
    {
        return probationaryPeriod;
    }

    public String getSubPersonalData()
    {
        return subPersonalData;
    }

    public void setAbsense(List<Absence> absense)
    {
        this.absense = absense;
    }

    public void setBalanceUnit(String balanceUnit)
    {
        this.balanceUnit = balanceUnit;
    }

    public void setBirthDay(String birthDay)
    {
        this.birthDay = birthDay;
    }

    public void setChildren(List<PersonChild> children)
    {
        this.children = children;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setFioEnglish(String fioEnglish)
    {
        this.fioEnglish = fioEnglish;
    }

    public void setFioLocal(String fioLocal)
    {
        this.fioLocal = fioLocal;
    }

    public void setFioRussian(String fioRussian)
    {
        this.fioRussian = fioRussian;
    }

    public void setFirehDay(String firehDay)
    {
        this.firehDay = firehDay;
    }

    public void setGroup(String group) 
    {
        this.group = group;
    }

    public void setHireDay(String hireDay)
    {
        this.hireDay = hireDay;
    }

    public void setManagerId(String managerId)
    {
        this.managerId = managerId;
    }

    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    public void setOrgUnit(String orgUnit)
    {
        this.orgUnit = orgUnit;
    }

    public void setPersonalData(String personalData)
    {
        this.personalData = personalData;
    }

    public void setPersonId(String personId)
    {
        this.personId = personId;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void setProbationaryPeriod(String probationaryPeriod)
    {
        this.probationaryPeriod = probationaryPeriod;
    }

    public void setSubPersonalData(String subPersonalData)
    {
       this.subPersonalData = subPersonalData;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"absense\":").append(absense).append(",");
        sb.append("\"children\":").append(children).append(",");
        sb.append("\"balanceUnit\":").append(Utils.weakTrim(balanceUnit)).append(",");
        sb.append("\"birthDay\":").append(Utils.weakTrim(birthDay)).append(",");
        sb.append("\"city\":").append(Utils.weakTrim(city)).append(",");
        sb.append("\"country\":").append(Utils.weakTrim(country)).append(",");
        sb.append("\"fioEnglish\":").append(Utils.weakTrim(fioEnglish)).append(",");
        sb.append("\"fioLocal\":").append(Utils.weakTrim(fioLocal)).append(",");
        sb.append("\"fioRussian\":").append(Utils.weakTrim(fioRussian)).append(",");
        sb.append("\"firehDay\":").append(Utils.weakTrim(firehDay)).append(",");
        sb.append("\"group\":").append(Utils.weakTrim(group)).append(",");
        sb.append("\"hireDay\":").append(Utils.weakTrim(hireDay)).append(",");
        sb.append("\"managerId\":").append(Utils.weakTrim(managerId)).append(",");
        sb.append("\"maritalStatus\":").append(Utils.weakTrim(maritalStatus)).append(",");
        sb.append("\"orgUnit\":").append(Utils.weakTrim(orgUnit)).append(",");
        sb.append("\"personalData\":").append(Utils.weakTrim(personalData)).append(",");
        sb.append("\"personId\":").append(Utils.weakTrim(personId)).append(",");
        sb.append("\"position\":").append(Utils.weakTrim(position)).append(",");
        sb.append("\"probationaryPeriod\":").append(Utils.weakTrim(probationaryPeriod)).append(",");
        sb.append("\"subPersonalData\":").append(Utils.weakTrim(subPersonalData));
        sb.append("\"email\":").append(Utils.weakTrim(email));
        sb.append("}");
        return sb.toString();
    }
}
