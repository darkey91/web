package ru.itmo.wm4.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wm4.domain.Notice;

@Component
public class NoticeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Notice.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            Notice notice = (Notice) target;
            if (notice.getContent().length() < 3) {
                errors.rejectValue("content", "field.min.length", "Content must be >=  3");
            }
        }
    }
}
