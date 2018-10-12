package pl.coderstrust.model;

public enum Vat {
  RATE_0(0f),
  RATE_5(0.05f),
  RATE_8(0.08f),
  RATE_23(0.23f);

  private float value;

  Vat(float value) {
    this.value = value;
  }

  public float getValue() {
    return value;
  }
}
