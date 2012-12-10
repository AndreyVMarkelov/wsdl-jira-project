package ru.mail.jira.plugins.actions;

import ru.mail.jira.plugins.saphr.struct.SAPInputStruct;

abstract class SapAction
{
    protected SAPInputStruct sis;

    /**
     * Constructor.
     */
    public SapAction(SAPInputStruct sis)
    {
        this.sis = sis;
    }

    public abstract void doAction();
}
