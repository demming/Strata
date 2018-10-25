/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.index.type;

import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.index.IborIndex;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.index.IborFutureTrade;

/**
 * A template for creating an Ibor Future trade using an absolute definition of time.
 * <p>
 * The future is selected from a sequence of futures based on a year-month.
 */
@BeanDefinition(builderScope = "private")
final class AbsoluteIborFutureTemplate
    implements IborFutureTemplate, ImmutableBean, Serializable {

  /**
   * The year-month that defines the future.
   * <p>
   * Given an input month, a future is selected from the underlying sequence of futures.
   * In most cases, the date of the future will be in the same month as the input month,
   * but this is not guaranteed.
   */
  @PropertyDefinition(validate = "notNull")
  private final YearMonth yearMonth;
  /**
   * The underlying futures convention.
   * <p>
   * This specifies the market convention of the future to be created.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final IborFutureConvention convention;

  //-------------------------------------------------------------------------
  /**
   * Obtains a template based on the specified convention.
   * <p>
   * The future is selected from a sequence of futures based on a year-month.
   * In most cases, the date of the future will be in the same month as the specified month,
   * but this is not guaranteed.
   * 
   * @param yearMonth  the year-month to use to select the future
   * @param convention  the future convention
   * @return the template
   */
  public static AbsoluteIborFutureTemplate of(YearMonth yearMonth, IborFutureConvention convention) {
    return new AbsoluteIborFutureTemplate(yearMonth, convention);
  }

  //-------------------------------------------------------------------------
  @Override
  public IborIndex getIndex() {
    return convention.getIndex();
  }

  @Override
  public IborFutureTrade createTrade(
      LocalDate tradeDate,
      SecurityId securityId,
      double quantity,
      double notional,
      double price,
      ReferenceData refData) {

    return convention.createTrade(tradeDate, securityId, yearMonth, quantity, notional, price, refData);
  }

  @Override
  public LocalDate calculateReferenceDateFromTradeDate(LocalDate tradeDate, ReferenceData refData) {
    return convention.calculateReferenceDateFromTradeDate(tradeDate, yearMonth, refData);
  }

  @Override
  public double approximateMaturity(LocalDate valuationDate) {
    return (yearMonth.getLong(PROLEPTIC_MONTH) - valuationDate.getLong(PROLEPTIC_MONTH)) / 12d;
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code AbsoluteIborFutureTemplate}.
   * @return the meta-bean, not null
   */
  public static AbsoluteIborFutureTemplate.Meta meta() {
    return AbsoluteIborFutureTemplate.Meta.INSTANCE;
  }

  static {
    MetaBean.register(AbsoluteIborFutureTemplate.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private AbsoluteIborFutureTemplate(
      YearMonth yearMonth,
      IborFutureConvention convention) {
    JodaBeanUtils.notNull(yearMonth, "yearMonth");
    JodaBeanUtils.notNull(convention, "convention");
    this.yearMonth = yearMonth;
    this.convention = convention;
  }

  @Override
  public AbsoluteIborFutureTemplate.Meta metaBean() {
    return AbsoluteIborFutureTemplate.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the year-month that defines the future.
   * <p>
   * Given an input month, a future is selected from the underlying sequence of futures.
   * In most cases, the date of the future will be in the same month as the input month,
   * but this is not guaranteed.
   * @return the value of the property, not null
   */
  public YearMonth getYearMonth() {
    return yearMonth;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying futures convention.
   * <p>
   * This specifies the market convention of the future to be created.
   * @return the value of the property, not null
   */
  @Override
  public IborFutureConvention getConvention() {
    return convention;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      AbsoluteIborFutureTemplate other = (AbsoluteIborFutureTemplate) obj;
      return JodaBeanUtils.equal(yearMonth, other.yearMonth) &&
          JodaBeanUtils.equal(convention, other.convention);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(yearMonth);
    hash = hash * 31 + JodaBeanUtils.hashCode(convention);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("AbsoluteIborFutureTemplate{");
    buf.append("yearMonth").append('=').append(yearMonth).append(',').append(' ');
    buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code AbsoluteIborFutureTemplate}.
   */
  static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code yearMonth} property.
     */
    private final MetaProperty<YearMonth> yearMonth = DirectMetaProperty.ofImmutable(
        this, "yearMonth", AbsoluteIborFutureTemplate.class, YearMonth.class);
    /**
     * The meta-property for the {@code convention} property.
     */
    private final MetaProperty<IborFutureConvention> convention = DirectMetaProperty.ofImmutable(
        this, "convention", AbsoluteIborFutureTemplate.class, IborFutureConvention.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "yearMonth",
        "convention");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -496678845:  // yearMonth
          return yearMonth;
        case 2039569265:  // convention
          return convention;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends AbsoluteIborFutureTemplate> builder() {
      return new AbsoluteIborFutureTemplate.Builder();
    }

    @Override
    public Class<? extends AbsoluteIborFutureTemplate> beanType() {
      return AbsoluteIborFutureTemplate.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code yearMonth} property.
     * @return the meta-property, not null
     */
    public MetaProperty<YearMonth> yearMonth() {
      return yearMonth;
    }

    /**
     * The meta-property for the {@code convention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IborFutureConvention> convention() {
      return convention;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -496678845:  // yearMonth
          return ((AbsoluteIborFutureTemplate) bean).getYearMonth();
        case 2039569265:  // convention
          return ((AbsoluteIborFutureTemplate) bean).getConvention();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code AbsoluteIborFutureTemplate}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<AbsoluteIborFutureTemplate> {

    private YearMonth yearMonth;
    private IborFutureConvention convention;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -496678845:  // yearMonth
          return yearMonth;
        case 2039569265:  // convention
          return convention;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -496678845:  // yearMonth
          this.yearMonth = (YearMonth) newValue;
          break;
        case 2039569265:  // convention
          this.convention = (IborFutureConvention) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public AbsoluteIborFutureTemplate build() {
      return new AbsoluteIborFutureTemplate(
          yearMonth,
          convention);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("AbsoluteIborFutureTemplate.Builder{");
      buf.append("yearMonth").append('=').append(JodaBeanUtils.toString(yearMonth)).append(',').append(' ');
      buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
