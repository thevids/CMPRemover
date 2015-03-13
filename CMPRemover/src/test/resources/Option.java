package no.moller.carvaluation.session.domain;

import no.moller.commons.param.impl.CarOptionCode;
import no.moller.commons.param.impl.CarOptionName;

/** Domain object for Options belonging to a car. */
public class Option {

    private final CarOptionCode optionCode;
    private final CarOptionName optionName;

    public Option(final CarOptionCode carOptionCode, final CarOptionName carOptionName) {
        this.optionCode = carOptionCode;
        this.optionName = carOptionName;
    }

    /**
     * Getter method.
     * @return the optionCode
     */
    public CarOptionCode getOptionCode() {
        return optionCode;
    }

    /**
     * Getter method.
     * @return the optionName
     */
    public CarOptionName getOptionName() {
        return optionName;
    }

    @Override
    public String toString() {
        return "Option [optionCode=" + optionCode + ", optionName=" + optionName + "]";
    }
}
