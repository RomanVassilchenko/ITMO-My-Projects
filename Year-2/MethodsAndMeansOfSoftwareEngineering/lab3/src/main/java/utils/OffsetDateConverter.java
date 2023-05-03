package utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@FacesConverter("OffsetDateConverter")
public class OffsetDateConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        OffsetDateTime newDate = null;
        String offset = OffsetDateTime.now().getOffset().toString();
        try {
            LocalDate localDate = LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDateTime parsed = localDate.atStartOfDay();
            String toParseFinally = parsed.toString() + offset;
            newDate = OffsetDateTime.parse(toParseFinally);
        } catch (DateTimeParseException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Convert Error!", "Date must be of format DD/MM/YYYY");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("date", message);
        }
        return newDate;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String toReturn;
        try {
            OffsetDateTime dateTime = (OffsetDateTime) o;
            toReturn = dateTime.toString().substring(0,9);
            toReturn.replace("-","/");
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Error converting date",
                    "Date must be of format DD/MM/YYYY");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        return toReturn;
    }
}
