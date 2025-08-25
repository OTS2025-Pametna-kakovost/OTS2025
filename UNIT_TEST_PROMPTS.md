## Tabnine
- **File:** `backend/src/test/java/si/um/feri/measurements/vao/ProductNullSafetyTest.java`
- **Package:** `si.um.feri.measurements.vao`
- **Prompt:**  
  Write a JUnit 5 unit test for `Product` that checks `new Product(null)` and `updateFrom(null)` throw `NullPointerException`.

---

## GitHub Copilot
- **File:** `backend/src/test/java/si/um/feri/measurements/vao/MeasurementToDtoTest.java`
- **Package:** `si.um.feri.measurements.vao`
- **Prompt:**  
  Write a JUnit 5 unit test for `Measurement.toDto()`: use a `Product` with id 77 and a `PostMeasurement` with value 12.34, set a fixed date and `isOk=false`, then assert the DTO fields.

---

## ChatGPT
- **File:** `backend/src/test/java/si/um/feri/measurements/dto/ProductRecordEqualityTest.java`
- **Package:** `si.um.feri.measurements.dto`
- **Prompt:**  
  Write a JUnit 5 unit test that checks two `Product` records with identical fields are equal and that a record with a different name is not equal.