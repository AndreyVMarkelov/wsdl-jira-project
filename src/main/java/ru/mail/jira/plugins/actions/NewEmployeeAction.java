package ru.mail.jira.plugins.actions;

import ru.mail.jira.plugins.saphr.struct.SAPInputStruct;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.MutableIssue;

public class NewEmployeeAction
    extends SapAction
{
    public NewEmployeeAction(SAPInputStruct sis)
    {
        super(sis);
    }

    @Override
    public void doAction()
    {
        User user = ComponentManager.getInstance().getUserUtil().getUserObject("admin");
        MutableIssue missue = ComponentManager.getInstance().getIssueFactory().getIssue();
        missue.setProjectId(10000L);
        missue.setIssueTypeId("3");
        missue.setSummary(sis.toString());
        missue.setAssignee(user);
        missue.setReporter(user);
        try
        {
            ComponentManager.getInstance().getIssueManager().createIssue(user, missue);
        }
        catch (CreateException e)
        {
            e.printStackTrace();
        }
    }
}
