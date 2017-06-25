package com.scherule.calendaring;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.TreeMap;

@DataObject(generateConverter = true)
public class Calendar {

  private Map<String, Integer> shares = new TreeMap<>();

  private double cash;

  public Calendar() {

  }

  public Calendar(Calendar other) {
    this.shares = new TreeMap<>(other.shares);
    this.cash = other.cash;
  }

  public Calendar(JsonObject json) {
    // A converter is generated to easy the conversion from and to JSON.
    CalendarConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    CalendarConverter.toJson(this, json);
    return json;
  }

  /**
   * @return the owned shared (name -> number)
   */
  public Map<String, Integer> getShares() {
    return shares;
  }

  /**
   * Sets the owned shares. Method used by the converter.
   *
   * @param shares the shares
   * @return the current {@link Calendar}
   */
  public Calendar setShares(Map<String, Integer> shares) {
    this.shares = shares;
    return this;
  }

  /**
   * @return the available cash.
   */
  public double getCash() {
    return cash;
  }

  /**
   * Sets the available cash. Method used by the converter.
   *
   * @param cash the cash
   * @return the current {@link Calendar}
   */
  public Calendar setCash(double cash) {
    this.cash = cash;
    return this;
  }

  // -- Additional method

  /**
   * This method is just a convenient method to get the number of owned shares of the specify company (name of the
   * company).
   *
   * @param name the name of the company
   * @return the number of owned shares, {@literal 0} is none.
   */
  public int getAmount(String name) {
    Integer current = shares.get(name);
    if (current == null) {
      return 0;
    }
    return current;
  }
}
