package com.sergejs.documentservice.write.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * ClaimDocumentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T11:20:52.840464+02:00[Europe/Stockholm]", comments = "Generator version: 7.13.0")
public class ClaimDocumentRequest {

  private @Nullable String claimNumber;

  private @Nullable String policyNumber;

  private @Nullable String city;

  private @Nullable String address;

  private @Nullable String placeOfIncident;

  private @Nullable String shortDescription;

  public ClaimDocumentRequest claimNumber(String claimNumber) {
    this.claimNumber = claimNumber;
    return this;
  }

  /**
   * Get claimNumber
   * @return claimNumber
   */
  
  @Schema(name = "claimNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("claimNumber")
  public String getClaimNumber() {
    return claimNumber;
  }

  public void setClaimNumber(String claimNumber) {
    this.claimNumber = claimNumber;
  }

  public ClaimDocumentRequest policyNumber(String policyNumber) {
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

  public ClaimDocumentRequest city(String city) {
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

  public ClaimDocumentRequest address(String address) {
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

  public ClaimDocumentRequest placeOfIncident(String placeOfIncident) {
    this.placeOfIncident = placeOfIncident;
    return this;
  }

  /**
   * Get placeOfIncident
   * @return placeOfIncident
   */
  
  @Schema(name = "placeOfIncident", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("placeOfIncident")
  public String getPlaceOfIncident() {
    return placeOfIncident;
  }

  public void setPlaceOfIncident(String placeOfIncident) {
    this.placeOfIncident = placeOfIncident;
  }

  public ClaimDocumentRequest shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

  /**
   * Get shortDescription
   * @return shortDescription
   */
  
  @Schema(name = "shortDescription", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("shortDescription")
  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimDocumentRequest claimDocumentRequest = (ClaimDocumentRequest) o;
    return Objects.equals(this.claimNumber, claimDocumentRequest.claimNumber) &&
        Objects.equals(this.policyNumber, claimDocumentRequest.policyNumber) &&
        Objects.equals(this.city, claimDocumentRequest.city) &&
        Objects.equals(this.address, claimDocumentRequest.address) &&
        Objects.equals(this.placeOfIncident, claimDocumentRequest.placeOfIncident) &&
        Objects.equals(this.shortDescription, claimDocumentRequest.shortDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(claimNumber, policyNumber, city, address, placeOfIncident, shortDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimDocumentRequest {\n");
    sb.append("    claimNumber: ").append(toIndentedString(claimNumber)).append("\n");
    sb.append("    policyNumber: ").append(toIndentedString(policyNumber)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    placeOfIncident: ").append(toIndentedString(placeOfIncident)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
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

