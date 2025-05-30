package com.sergejs.documentservice.write.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * InvoiceDocumentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T11:20:52.840464+02:00[Europe/Stockholm]", comments = "Generator version: 7.13.0")
public class InvoiceDocumentRequest {

  private @Nullable String policyNumber;

  private @Nullable PolicyDocumentRequestInsured insured;

  private @Nullable PolicyDocumentRequestInsured owner;

  private @Nullable String phoneNumber;

  private @Nullable BigDecimal totalPrice;

  public InvoiceDocumentRequest policyNumber(String policyNumber) {
    this.policyNumber = policyNumber;
    return this;
  }

  /**
   * Get policyNumber
   * @return policyNumber
   */
  
  @Schema(name = "policyNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("policyNumber")
  public String getPolicyNumber() {
    return policyNumber;
  }

  public void setPolicyNumber(String policyNumber) {
    this.policyNumber = policyNumber;
  }

  public InvoiceDocumentRequest insured(PolicyDocumentRequestInsured insured) {
    this.insured = insured;
    return this;
  }

  /**
   * Get insured
   * @return insured
   */
  @Valid 
  @Schema(name = "insured", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insured")
  public PolicyDocumentRequestInsured getInsured() {
    return insured;
  }

  public void setInsured(PolicyDocumentRequestInsured insured) {
    this.insured = insured;
  }

  public InvoiceDocumentRequest owner(PolicyDocumentRequestInsured owner) {
    this.owner = owner;
    return this;
  }

  /**
   * Get owner
   * @return owner
   */
  @Valid 
  @Schema(name = "owner", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("owner")
  public PolicyDocumentRequestInsured getOwner() {
    return owner;
  }

  public void setOwner(PolicyDocumentRequestInsured owner) {
    this.owner = owner;
  }

  public InvoiceDocumentRequest phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  
  @Schema(name = "phoneNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public InvoiceDocumentRequest totalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  /**
   * Get totalPrice
   * @return totalPrice
   */
  @Valid 
  @Schema(name = "totalPrice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPrice")
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceDocumentRequest invoiceDocumentRequest = (InvoiceDocumentRequest) o;
    return Objects.equals(this.policyNumber, invoiceDocumentRequest.policyNumber) &&
        Objects.equals(this.insured, invoiceDocumentRequest.insured) &&
        Objects.equals(this.owner, invoiceDocumentRequest.owner) &&
        Objects.equals(this.phoneNumber, invoiceDocumentRequest.phoneNumber) &&
        Objects.equals(this.totalPrice, invoiceDocumentRequest.totalPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyNumber, insured, owner, phoneNumber, totalPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvoiceDocumentRequest {\n");
    sb.append("    policyNumber: ").append(toIndentedString(policyNumber)).append("\n");
    sb.append("    insured: ").append(toIndentedString(insured)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    totalPrice: ").append(toIndentedString(totalPrice)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

