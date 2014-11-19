package gov.ed.fsa.drts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

@FacesConverter(value="defaultDateConverter")
public class DefaultDateConverter extends DateTimeConverter {

	private List<String> date_formats = ApplicationProperties.DATE_FORMATS.getListValue();
	
	private static final Logger logger = Logger.getLogger(DefaultDateConverter.class);
	
	public DefaultDateConverter()
	{
		setDateStyle("full");
        setType("both");
        setTimeZone(TimeZone.getTimeZone("America/New_York"));
    }
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    	throws ConverterException
	{
        Date date = null;
        
        for(String pattern : date_formats)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            
            logger.info("trying patter: " + pattern);
            
            try
            {
                date = sdf.parse(value);
                break;
            }
            catch(ParseException ignore)
            {
                // ignore
            }
        }

        if (date == null)
        {
            throw new ConverterException(new FacesMessage("Invalid date format, must match either of " + date_formats));
        }

        return date;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    	throws ConverterException
    {
        return new SimpleDateFormat(date_formats.get(0)).format((Date) value);
    }
}
