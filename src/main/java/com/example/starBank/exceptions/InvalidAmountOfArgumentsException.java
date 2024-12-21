package com.example.starBank.exceptions;

import com.example.starBank.model.RuleRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Chowo
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidAmountOfArgumentsException extends IndexOutOfBoundsException {
    public InvalidAmountOfArgumentsException(RuleRequirements ruleRequirements) {
        super("Invalid amount of arguments for rule %s".formatted(ruleRequirements.getQuery()));
    }
}
