package validator;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.omnifaces.validator.MultiFieldValidator;

@Named
@ApplicationScoped
public class ValidateMultipleV implements MultiFieldValidator {

	@Override
	public boolean validateValues(FacesContext context, List<UIInput> components, List<Object> values) {
		return "UNO".equals(values.get(0)) && "DOS".equals(values.get(1)) && "TRES".equals(values.get(2));
	}
        
}
