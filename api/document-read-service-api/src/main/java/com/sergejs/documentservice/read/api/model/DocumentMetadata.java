package com.sergejs.documentservice.read.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * DocumentMetadata
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T11:27:29.750111+02:00[Europe/Stockholm]", comments = "Generator version: 7.13.0")
public class DocumentMetadata {

  private @Nullable UUID id;

  private @Nullable String documentType;

  public DocumentMetadata id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public DocumentMetadata documentType(String documentType) {
    this.documentType = documentType;
    return this;
  }

  /**
   * Get documentType
   * @return documentType
   */
  
  @Schema(name = "documentType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("documentType")
  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentMetadata documentMetadata = (DocumentMetadata) o;
    return Objects.equals(this.id, documentMetadata.id) &&
        Objects.equals(this.documentType, documentMetadata.documentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentMetadata {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
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

