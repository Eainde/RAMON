package com.db.ramon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "_order")
public class OrderEntity {
  @Id
  @Column(name = "order_id")
  private long orderId;

  @Column(name = "_level")
  private int level;

  @Column(name = "code")
  private String code;

  @Column(name = "parent")
  private String parent;

  @Column(name = "description")
  private String description;

  @Column(name = "item_includes")
  private String itemIncludes;

  @Column(name = "item_also_includes")
  private String itemAlsoIncludes;

  @Column(name = "rulings")
  private String rulings;

  @Column(name = "item_excludes")
  private String itemExcludes;

  @Column(name = "ref_to_isic_rev_4")
  private String refToIsicRev4;

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRulings() {
    return rulings;
  }

  public void setRulings(String rulings) {
    this.rulings = rulings;
  }

  public String getItemIncludes() {
    return itemIncludes;
  }

  public void setItemIncludes(String itemIncludes) {
    this.itemIncludes = itemIncludes;
  }

  public String getItemExcludes() {
    return itemExcludes;
  }

  public void setItemExcludes(String itemExcludes) {
    this.itemExcludes = itemExcludes;
  }

  public String getRefToIsicRev4() {
    return refToIsicRev4;
  }

  public void setRefToIsicRev4(String refToIsicRev4) {
    this.refToIsicRev4 = refToIsicRev4;
  }

  public String getItemAlsoIncludes() {
    return itemAlsoIncludes;
  }

  public void setItemAlsoIncludes(String itemAlsoIncludes) {
    this.itemAlsoIncludes = itemAlsoIncludes;
  }
}
