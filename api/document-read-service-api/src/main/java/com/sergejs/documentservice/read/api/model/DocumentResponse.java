package com.sergejs.documentservice.read.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DocumentResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T14:25:27.316009+02:00[Europe/Stockholm]", comments = "Generator version: 7.13.0")
public class DocumentResponse {

  @Valid
  private List<@Valid DocumentMetadata> documents = new ArrayList<>();

  public DocumentResponse documents(List<@Valid DocumentMetadata> documents) {
    this.documents = documents;
    return this;
  }

  public DocumentResponse addDocumentsItem(DocumentMetadata documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<>();
    }
    this.documents.add(documentsItem);
    return this;
  }

  /**
   * Get documents
   * @return documents
   */
  @Valid 
  @Schema(name = "documents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("documents")
  public List<@Valid DocumentMetadata> getDocuments() {
    return documents;
  }

  public void setDocuments(List<@Valid DocumentMetadata> documents) {
    this.documents = documents;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentResponse documentResponse = (DocumentResponse) o;
    return Objects.equals(this.documents, documentResponse.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentResponse {\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
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

