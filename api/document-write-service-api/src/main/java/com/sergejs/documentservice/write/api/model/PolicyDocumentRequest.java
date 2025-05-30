package com.sergejs.documentservice.write.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * PolicyDocumentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T11:20:52.840464+02:00[Europe/Stockholm]", comments = "Generator version: 7.13.0")
public class PolicyDocumentRequest {

  private @Nullable PolicyDocumentRequestInsured insured;

  private @Nullable PolicyDocumentRequestInsured owner;

  private @Nullable String policyNumber;

  private @Nullable String phoneNumber;

  private @Nullable String address;

  private @Nullable String city;

  private @Nullable String relationshipToInsured;

  private @Nullable BigDecimal premium;

  private @Nullable String lob;

  public PolicyDocumentRequest insured(PolicyDocumentRequestInsured insured) {
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

  public PolicyDocumentRequest owner(PolicyDocumentRequestInsured owner) {
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

  public PolicyDocumentRequest policyNumber(String policyNumber) {
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

  public PolicyDocumentRequest phoneNumber(String phoneNumber) {
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

  public PolicyDocumentRequest address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public PolicyDocumentRequest city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  
  @Schema(name = "city", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public PolicyDocumentRequest relationshipToInsured(String relationshipToInsured) {
    this.relationshipToInsured = relationshipToInsured;
    return this;
  }

  /**
   * Get relationshipToInsured
   * @return relationshipToInsured
   */
  
  @Schema(name = "relationshipToInsured", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("relationshipToInsured")
  public String getRelationshipToInsured() {
    return relationshipToInsured;
  }

  public void setRelationshipToInsured(String relationshipToInsured) {
    this.relationshipToInsured = relationshipToInsured;
  }

  public PolicyDocumentRequest premium(BigDecimal premium) {
    this.premium = premium;
    return this;
  }

  /**
   * Get premium
   * @return premium
   */
  @Valid 
  @Schema(name = "premium", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("premium")
  public BigDecimal getPremium() {
    return premium;
  }

  public void setPremium(BigDecimal premium) {
    this.premium = premium;
  }

  public PolicyDocumentRequest lob(String lob) {
    this.lob = lob;
    return this;
  }

  /**
   * Get lob
   * @return lob
   */
  
  @Schema(name = "lob", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lob")
  public String getLob() {
    return lob;
  }

  public void setLob(String lob) {
    this.lob = lob;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolicyDocumentRequest policyDocumentRequest = (PolicyDocumentRequest) o;
    return Objects.equals(this.insured, policyDocumentRequest.insured) &&
        Objects.equals(this.owner, policyDocumentRequest.owner) &&
        Objects.equals(this.policyNumber, policyDocumentRequest.policyNumber) &&
        Objects.equals(this.phoneNumber, policyDocumentRequest.phoneNumber) &&
        Objects.equals(this.address, policyDocumentRequest.address) &&
        Objects.equals(this.city, policyDocumentRequest.city) &&
        Objects.equals(this.relationshipToInsured, policyDocumentRequest.relationshipToInsured) &&
        Objects.equals(this.premium, policyDocumentRequest.premium) &&
        Objects.equals(this.lob, policyDocumentRequest.lob);
  }

  @Override
  public int hashCode() {
    return Objects.hash(insured, owner, policyNumber, phoneNumber, address, city, relationshipToInsured, premium, lob);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PolicyDocumentRequest {\n");
    sb.append("    insured: ").append(toIndentedString(insured)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    policyNumber: ").append(toIndentedString(policyNumber)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    relationshipToInsured: ").append(toIndentedString(relationshipToInsured)).append("\n");
    sb.append("    premium: ").append(toIndentedString(premium)).append("\n");
    sb.append("    lob: ").append(toIndentedString(lob)).append("\n");
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

