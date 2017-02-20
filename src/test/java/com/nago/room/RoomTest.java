package com.nago.room;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class RoomTest {
  @SuppressWarnings("unchecked")
  @Test
  public void invalidRoomCannotBeCreatedAndGivesProperMessage() throws Exception {
    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    Validator validator = vf.getValidator();

    Set<ConstraintViolation<Room>> violations = validator.validate(new Room("Toilet", 1000));
    ConstraintViolation<Room>[] violationsArray = violations.toArray(new ConstraintViolation[violations.size()]);

    assertEquals("must be less than 1000", violationsArray[0].getMessage());
    assertEquals(violations.size(), 1);
  }
}