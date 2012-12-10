package ru.mail.jira.plugins;

public class SapPluginException
    extends RuntimeException
{
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = -1900351057051796625L;

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    private int httpCode;

    private boolean isSOAPError;

    private String message;

    public SapPluginException(
        boolean isSOAPError,
        int httpCode,
        String message)
    {
        super();
        this.isSOAPError = isSOAPError;
        this.httpCode = httpCode;
        this.message = message;
    }

    public SapPluginException(
        boolean isSOAPError,
        int httpCode,
        String message,
        Throwable thr)
    {
        super(thr);
        this.isSOAPError = isSOAPError;
        this.httpCode = httpCode;
        this.message = message;
    }

    public int getHttpCode()
    {
        return httpCode;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isSOAPError()
    {
        return isSOAPError;
    }

    @Override
    public String toString()
    {
        return "SapPluginException[isSOAPError=" + isSOAPError + ", httpCode=" + httpCode + ", message=" + message + "]";
    }
}
